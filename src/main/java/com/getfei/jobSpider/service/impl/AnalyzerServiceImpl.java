package com.getfei.jobSpider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.Job;
import com.getfei.jobSpider.entity.Technologies;
import com.getfei.jobSpider.entity.Technology;
import com.getfei.jobSpider.service.IAnalyzerService;
import com.getfei.jobSpider.util.data.EchartsData;

@Service
public class AnalyzerServiceImpl extends BaseServiceImpl implements IAnalyzerService {

	private Map<Technology, Integer> technologyCounter;
	
	public void initTechnologyCounter() {
		technologyCounter = new HashMap<>();
		for (Technology technology : Technologies.technologyMapping.values()) {
			technologyCounter.put(technology, 0);
		}
	}
	
	@Override
	public AnalysisResult analyse(FetchedResult fetchedResult) {
		initTechnologyCounter();
		// 遍历job，统计次数
		for (int i = 0, size = fetchedResult.getJobs().size(); i < size; i++) {
			Job job =fetchedResult.getJobs().get(i);
			String url=job.getUrl();
			String jobTitle=job.getJobTitle();
			String salary=job.getSalary();
			String companyName=job.getCompanyName();
			String jobMessage = job.getJobMessage();
//			logger.info("开始分析：url="+url+",职位="+jobTitle+",薪水="+salary+",公司名称="+companyName);
//			logger.info("信息："+jobMessage);
			// 遍历technologyMapping，找出匹配的technology
			Technologies.technologyMapping.forEach((key, technology) -> {
				boolean finded=false;
//				logger.info("包含"+technology.getName()+"?");
				if(jobMessage.toLowerCase().contains(technology.getName().toLowerCase())) {
//					logger.info("包含"+technology.getName());
					finded=true;
				}else {
					for (String aliase : technology.getAliases()) {// 遍历别名集
						if(jobMessage.toLowerCase().contains(aliase.toLowerCase())) {
//							logger.info("包含"+aliase);
							finded=true;
						}
					}
				}
				if(finded) {
					Integer newCount = technologyCounter.get(technology) + 1;
					technologyCounter.put(Technologies.technologyMapping.get(key), newCount);
//					logger.info("找到" + technology.getName() + "，新次数=" + newCount);
				}
//				for (String aliase : technology.getAliases()) {// 遍历别名集
//					if (jobMessage.toLowerCase().contains(aliase.toLowerCase())) {// 匹配，更新次数
//						Integer newCount = technologyCounter.get(technology) + 1;
//						technologyCounter.put(Technologies.technologyMapping.get(key), newCount);
//						logger.info("找到" + aliase + "，新次数=" + newCount);
//						break;
//					}
//				}
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
//				logger.info(technologyType + "已存在，且count=" + technologyTypeCounter.get(technologyType)
//						+ "，准备加" + count);
				int newCount = technologyTypeCounter.get(technologyType) + count;
				technologyTypeCounter.put(technologyType, newCount);
			} else {// 类型未有
//				logger.info(technologyType + "不存在，count初始化为" + count);
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
		logger.info("爬取Job总数：" + fetchedResult.getTotal());
		logger.info("爬取成功数：" + fetchedResult.getSuccessCount());
		logger.info("爬取失败数：" + fetchedResult.getFailureCount());
		// for (Entry<Technology, Integer> entry : technologyCounterList) {
		// logger.info(entry.getKey() + " --- " + entry.getValue());
		// }
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
