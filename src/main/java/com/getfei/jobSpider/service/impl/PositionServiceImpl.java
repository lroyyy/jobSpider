package com.getfei.jobSpider.service.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import com.getfei.jobSpider.dao.IPositionDao;
import com.getfei.jobSpider.entity.Position;
import com.getfei.jobSpider.service.IPositionService;

@Service
public class PositionServiceImpl implements IPositionService {

	public static Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

	@Autowired
	private IPositionDao positionDao;
	
	@Override
	public List<Position> list() {
		List<Position> positions=positionDao.findAll();
		return positions;
	}
	
	@Override
	public Position getByName(String name) {
		return positionDao.findByName(name);
	}

	@Override
	public Position getByCode(String code) {
		return positionDao.findByCode(code);
	}
	/**
	 * 爬取地区信息
	 */
	@Override
	public void fetchAndInsert() {
		logger.info("开始爬虫。");
		int min = 10000;// 北京
		// int max = 100000;
		int max = 350000;// 台湾
		for (int code = min; code <= max;) {
			try {
				String name = getNameByCode(code);
				logger.info("code=" + code + ",name=" + name);
				Position position=new Position(name,formatCode(code));
				positionDao.save(position);
				code = getNextAvailablePositionCode(code);
			} catch (Exception e) {
				logger.error("爬取地区信息时出错，信息："+e.getMessage());
			}
		}
		logger.info("爬虫结束。");
	}

	/** 输入编码，爬取地区的名称 */
	private String getNameByCode(int code) throws IOException {
		String url = "https://search.51job.com/list/" + formatCode(code)
				+ ",000000,0000,00,9,99,%2520,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
		Document document;
		document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0")
				.get();
		Element e = document.selectFirst("#work_position_input");
		String positionName = e.val();
		return positionName;
	}

	/** 格式化编码 */
	public static String formatCode(int code) {
		return String.format("%06d", code);
	}

	/** 获取下个有效的地区编码 */
	private int getNextAvailablePositionCode(int code) throws IOException {
		PositionCode nextCode = new PositionCode(code);
		String name;
		do {
			nextCode.increase("city");
			name = getNameByCode(nextCode.toInt());
		} while ("全国".equals(name));
		return nextCode.toInt();
	}

	@Override
	public List<Position> listStructured() {
		List<Position> positions=positionDao.findAll();
		List<Position> provinces=new ArrayList<>();
		positions.forEach(position->{
			if(position.getCode().endsWith("0000")) {//筛选出省
				provinces.add(position);
				String head=position.getCode().substring(0,2);
//				logger.info("name="+position.getName()+",head="+head);
				positions.forEach(p->{
					if(!p.getCode().endsWith("0000")&&p.getCode().startsWith(head)) {
						position.addPosition(p);
//						logger.info("add"+p.getName()+"to"+position.getName());
					}
				});
			}
		});
		return provinces;
	}

}

/** 地区编码 */
class PositionCode {

	int province;
	int city;
	int county;

	public PositionCode(int code) {
		String codeStr = PositionServiceImpl.formatCode(code);
		// PositionServiceImpl.logger.info(code+"构造成"+codeStr);
		province = Integer.parseInt(codeStr.substring(0, 2));
		city = Integer.parseInt(codeStr.substring(2, 4));
		county = Integer.parseInt(codeStr.substring(4, 6));
	}

	public void reset(String key) {
		switch (key) {
		case "county":
			county = 0;
			break;
		case "city":
			city = 0;
			break;
		case "province":
			province = 0;
			break;
		}
	}

	public void increase(String key) {
//		String old = toString();
		switch (key) {
		case "county":
			increaseCounty();
			break;
		case "city":
			increaseCity();
			break;
		case "province":
			increaseProvince();
			break;
		}
		// PositionServiceImpl.logger.info("增长前："+old+","+key+"增长后："+toString());
	}

	private void increaseProvince() {
		province++;
	}

	private void increaseCity() {
		if (city == 99) {
			reset("city");
			increaseProvince();
		} else {
			city++;
		}
	}

	private void increaseCounty() {
		if (county == 99) {
			reset("county");
			increaseCity();
		} else {
			county++;
		}
	}

	private static String formatDigit(int digit) {
		return digit <= 9 ? "0" + digit : String.valueOf(digit);
	}

	public int toInt() {
		String str = toString();
		// if(str.startsWith("0")) {
		// str=str.substring(1);
		// }
		return Integer.parseInt(str);
	}

	@Override
	public String toString() {
		return formatDigit(province) + formatDigit(city) + formatDigit(county);
	}

}
