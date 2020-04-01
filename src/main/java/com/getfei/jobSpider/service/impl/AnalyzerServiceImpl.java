package com.getfei.jobSpider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.getfei.jobSpider.common.DataCenter;
import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.Job;
import com.getfei.jobSpider.entity.Technology;
import com.getfei.jobSpider.service.IAnalyzerService;
import com.getfei.jobSpider.util.data.EchartsData;

@Service
public class AnalyzerServiceImpl extends CommonServiceImpl implements IAnalyzerService {

	private Map<Technology, Integer> technologyCounter;

	public void initTechnologyCounter() {
		technologyCounter = new HashMap<>();
//		for (Technology technology : Technologies.technologyMapping.values()) {
		for (Technology technology : DataCenter.getTechnologyMapping().values()) {
			technologyCounter.put(technology, 0);
		}
	}

	@Override
	public AnalysisResult analyse(FetchedResult fetchedResult) {
		initTechnologyCounter();
		// 遍历job，统计次数
		for (int i = 0, size = fetchedResult.getJobs().size(); i < size; i++) {
			Job job = fetchedResult.getJobs().get(i);
			String url = job.getUrl();
			String jobTitle = job.getJobTitle();
			String salary = job.getSalary();
			String companyName = job.getCompanyName();
			String jobMessage = job.getJobMessage();
//			logger.info("开始分析：url="+url+",职位="+jobTitle+",薪水="+salary+",公司名称="+companyName);
//			logger.info("信息："+jobMessage);
			// 遍历technologyMapping，找出匹配的technology
//			Technologies.technologyMapping.forEach((key, technology) -> {
			DataCenter.getTechnologyMapping().forEach((key, technology) -> {
				boolean finded = false;
				if (jobMessage.toLowerCase().contains(technology.getName().toLowerCase())) {
					finded = true;
				} else {
					for (String aliase : technology.getAliases()) {// 遍历别名集
						if (jobMessage.toLowerCase().contains(aliase.toLowerCase())) {
							finded = true;
						}
					}
				}
				if (finded) {
					Integer newCount = technologyCounter.get(technology) + 1;
//					technologyCounter.put(Technologies.technologyMapping.get(key), newCount);
					technologyCounter.put(DataCenter.getTechnologyMapping().get(key), newCount);
				}
			});
		}
		// 移除次数为0的技术
		technologyCounter = technologyCounter.entrySet().stream().filter(a -> a.getValue() != 0)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));// stream的filter实现
		// 按技术类型统计构造technologyTypeCounter
		Map<String, Integer> technologyTypeCounter = new HashMap<>();
		technologyCounter.forEach((technology, count) -> {
			String technologyType = technology.getType();
			if (technologyTypeCounter.containsKey(technologyType)) {// 类型已有
				int newCount = technologyTypeCounter.get(technologyType) + count;
				technologyTypeCounter.put(technologyType, newCount);
			} else {// 类型未有
				technologyTypeCounter.put(technologyType, count);
			}
		});
		// 排序
		Map<Technology, Integer> sortedTechnologyCounter = sortTechnologyCounter(technologyCounter);
		Map<String, Integer> sortedTechnologyTypeCounter = sortTechnologyTypeCounter(technologyTypeCounter);
		// 转化为Echarts需要的数据类型echartsData
		List<EchartsData> technologyCounterEchartsData = new ArrayList<>();
		sortedTechnologyCounter.forEach((key, value) -> {
			technologyCounterEchartsData.add(new EchartsData(key.getName(), String.valueOf(value)));
		});
		List<EchartsData> technologyTypeCounterEchartsData = new ArrayList<>();
		sortedTechnologyTypeCounter.forEach((key, value) -> {
			technologyTypeCounterEchartsData.add(new EchartsData(key, String.valueOf(value)));
		});
		// 构造AnalysisResult对象
		AnalysisResult analysisResult = new AnalysisResult();
//		analysisResult.setFetchResult(fetchedResult);
		analysisResult.setDate(new Date());
		analysisResult.setKeyword(fetchedResult.getKeyword());
		analysisResult.setPosition(fetchedResult.getPosition());
		analysisResult.setTechnologyCounter(technologyCounterEchartsData);
		analysisResult.setTechnologyTypeCounter(technologyTypeCounterEchartsData);
		// 日志
		logger.info("爬取Job总数：" + fetchedResult.getTotal());
		logger.info("爬取成功数：" + fetchedResult.getSuccessCount());
		logger.info("爬取失败数：" + fetchedResult.getFailureCount());
		sortedTechnologyCounter.forEach((key, value) -> {
			logger.info(key + " --- " + value);
		});
		sortedTechnologyTypeCounter.forEach((key, value) -> {
			logger.info(key + " --- " + value);
		});
		return analysisResult;
	}

	private static Map<Technology, Integer> sortTechnologyCounter(Map<Technology, Integer> map) {
		Map<Technology, Integer> sortMap = new LinkedHashMap<>();
		map.entrySet().stream().sorted((o1, o2) -> {
			int r = o1.getKey().getType().compareTo(o2.getKey().getType());
			if (r == 0) {
				r = o2.getValue().compareTo(o1.getValue());
			}
			return r;
		}).forEach(entry -> sortMap.put(entry.getKey(), entry.getValue()));
		return sortMap;
	}

	private static Map<String, Integer> sortTechnologyTypeCounter(Map<String, Integer> map) {
		Map<String, Integer> sortMap = new LinkedHashMap<>();
		map.entrySet().stream().sorted((o1, o2) -> {
			int r = o1.getKey().compareTo(o2.getKey());
			if (r == 0) {
				r = o2.getValue().compareTo(o1.getValue());
			}
			return r;
		}).forEach(entry -> sortMap.put(entry.getKey(), entry.getValue()));
		return sortMap;
	}

}
