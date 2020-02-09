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
import com.getfei.jobSpider.service.IPositionService;
import com.getfei.jobSpider.service.IFetcherService;
import com.getfei.jobSpider.service.ex.EmptyFetchedResultException;
import com.getfei.jobSpider.service.ex.NoSuchPositionException;

@Service
public class FetcherServiceImpl implements IFetcherService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPositionService positionService;

	@Override
	public FetchedResult fetchJobs(String keyword,String positionCode) throws Exception {
		//根据位置名查询位置编码（福州 110200）
		FetchedResult result = new FetchedResult();
		Position position=positionService.getByCode(positionCode);
		if(position==null) {
			throw new NoSuchPositionException("爬取工作时出错：找不到编码为\""+positionCode+"\"的位置。");
		}
		logger.info("获取前端传入的位置编码名："+positionCode);
		//总页数
		int totalPage=0;
		//当前页码
		int currentPage=0;
		List<Job> jobs = new ArrayList<>();
		String jobListUrl=null;
		Document document;
		int i = 1;//第1页开始
		for (; ; i++) {
			try {
				jobListUrl = "https://search.51job.com/list/"+position.getCode()+",000000,0000,00,9,99," + keyword + ",2," + i
						+ ".html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
				document = Jsoup.connect(jobListUrl)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
				//获取总页数
				if(totalPage==0) {
					Element totalPageElement=document.selectFirst("#hidTotalPage");
					totalPage=Integer.parseInt(totalPageElement.val());
					//数据总览
					logger.info("总页数："+totalPage);
				}
				//获取当前页码
				Element currentPageElement=document.selectFirst("#jump_page");
				currentPage=Integer.parseInt(currentPageElement.val());
				//判断本页是否还有结果
				Elements es = document.select(".t1 a");
				if (es.isEmpty()) {
					logger.info("第" + i + "页结果为空！" + (i - 1) + "已是最后一页，爬取结束。");
					break;
				}
				logger.info("开始爬第"+i+"页");
				//爬取本页所有链接
				for (Element e : es) {
					String jobTitle=e.attr("title");
					if(jobTitle!=null&&!jobTitle.contains(keyword)) {
						continue;
					}
					String url = e.attr("href");
					Job job = fetchSingleUrl(url);
					jobs.add(job);
				}
				//处于后半部门的数据匹配度较低且考虑效率，只爬取前半部分的数据
				if(totalPage>1&&i>totalPage) {
					logger.info("爬取停止，原因：截止第"+i+"页的数据已爬取完毕，舍弃后半部分数据。");
					break;
				}
			} catch (Exception e) {
				result.failureCountIncrease1();//失败数+1
				logger.info("爬取停止，原因：爬取第" + i + "页时出错！信息：" + e.getMessage());
				continue;
			}
		}
		if (jobs.isEmpty()) {
			throw new EmptyFetchedResultException("爬取结果为空，请更换关键字后再试。");
		}
		//将爬取的工作和爬取成功失败数注入fetchedResult对象
		result.setJobs(jobs);
		result.setKeyword(keyword);
		result.setPosition(positionCode);
		result.setSuccessCount(jobs.size());
		result.setTotal(result.getFailureCount() + result.getSuccessCount());
		return result;
	}

	/**爬取单页url*/
	private Job fetchSingleUrl(String url) throws Exception {
		Job job = new Job();
		job.setUrl(url);
		Document document;
		try {
			document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0")
					.get();
			// 职位
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
			//2019.1.19：51job改了DOM！
//			String jobMessage = "";
//			Elements es = document.select(".job_msg div");
//			for (int i = 0; i < es.size(); i++) {
//				String line = es.get(i).text();
//				logger.info("line="+line);
//				if(!"".equals(line)) {
//					jobMessage += line + "\n";
//				}
//			}
			e = document.selectFirst(".job_msg");
			String jobMessage = e.html();
			job.setJobMessage(jobMessage);
		} catch (IOException e1) {
			throw new Exception(url + "爬取失败，原因：" + e1.getMessage());
		}
//		logger.info(url + "爬取成功。");
		return job;
	}

}
