package com.getfei.jobSpider.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.IPositionDao;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.Job;
import com.getfei.jobSpider.entity.Position;
import com.getfei.jobSpider.service.IWebFetcherService;

@Service
public class WebFetcherImpl implements IWebFetcherService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IPositionDao positionDao;

	@Override
	public FetchedResult fetchJobs(String positionName) throws Exception {
		//福州 110200
		FetchedResult result = new FetchedResult();
		Position position=positionDao.findOne(positionName);
		logger.info("获取前端传入的地区名："+positionName+",mongoDB查询获取地区编码："+position.getCode());
		List<Job> jobs = new ArrayList<>();
		int i = 1;
		for (; ; i++) {
			try {
				// 福州java
				String keyWord = "java";
				String url = "https://search.51job.com/list/"+position.getCode()+",000000,0000,00,9,99," + keyWord + ",2," + i
						+ ".html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
				Document document;
				document = Jsoup.connect(url)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
				Elements es = document.select(".t1 a");
				if (es.isEmpty()) {
					logger.info("第" + i + "页结果为空！" + (i - 1) + "已是最后一页，爬取结束。");
					break;
				}
				for (Element e : es) {
					String u = e.attr("href");
					Job job = fetchSingleUrl(u);
					jobs.add(job);
				}
			} catch (Exception e) {
				result.failureCountIncrease1();
				logger.info("爬取第" + i + "页时出错！信息：" + e.getMessage());
				continue;
			}
		}
		if (jobs.isEmpty()) {
			throw new Exception("爬取结果为空！");
		}
		result.setJobs(jobs);
		result.setSuccessCount(jobs.size());
		result.setTotal(result.getFailureCount() + result.getSuccessCount());
		return result;
	}

	private Job fetchSingleUrl(String url) throws Exception {
		Job job = new Job();
		Document document;
		try {
			document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0")
					.get();
			// 岗位
			Element e = document.selectFirst("h1");
			String jobTitle = e.text();
			job.setJobTitle(jobTitle);
			// 薪资
			e = document.selectFirst(".tHeader strong");
			String salary = e.text();
			job.setSalary(salary);
			// 公司名称
			e = document.selectFirst(".catn");
			String companyName = e.text();
			job.setCompanyName(companyName);
			// 职位信息
			Elements es = document.select(".job_msg div");
			String jobMessage = "";
			for (int i = 0; i < es.size(); i++) {
				String line = es.get(i).text();
				jobMessage += line + "\n";
			}
			job.setJobMessage(jobMessage);
		} catch (IOException e1) {
			throw new Exception(url + "爬取失败，原因：" + e1.getMessage());
		}
		logger.info(url + "爬取成功。");
		return job;
	}

}
