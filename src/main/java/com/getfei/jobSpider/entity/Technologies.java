package com.getfei.jobSpider.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ITechnologyDao;

@Component
public class Technologies {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public static Technologies technologies;
	public static Map<String, Technology> technologyMapping;

	@Autowired
	private ITechnologyDao technologyDao;

	@PostConstruct
	public void init() {
		technologyMapping = new HashMap<>();
		// buildFromCode();
		buildFromMongoDB();
	}

	public void buildFromMongoDB() {
		List<Technology> list = technologyDao.findAll();
		logger.info("list="+list);
		technologyMapping = list.stream().collect(Collectors.toMap(Technology::getName, technology -> technology));
	}

	private static void buildFromCode() {
		Technologies.batchAdd(technologyMapping, new TechnologyType("后端技术"), new String[] { "SSH" },
				new String[] { "SSM" }, new String[] { "Spring" }, new String[] { "SpringCloud" },
				new String[] { "Dubbo" }, new String[] { "SpringMVC", "Spring MVC" }, new String[] { "SpringBoot" },
				new String[] { "Struts" }, new String[] { "WebService" }, new String[] { "JFinal" },
				new String[] { "socket" }, new String[] { "Shiro" }, new String[] { "JSP" }, new String[] { "Servlet" },
				new String[] { "Netty" }, new String[] { "ElasticSearch" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("数据库框架"), new String[] { "MyBatis" },
				new String[] { "iBatis" }, new String[] { "Hibernate" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("数据库"), new String[] { "MySql" },
				new String[] { "PostgreSQL" }, new String[] { "Oracle" }, new String[] { "Mongodb" },
				new String[] { "Redis" }, new String[] { "Memcached" }, new String[] { "SQLServer", "MSSQL" },
				new String[] { "JDBC" }, new String[] { "存储过程" }, new String[] { "SQL语句优化", "SQL优化", "SQL调优" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("Web中间件"), new String[] { "Tomcat" },
				new String[] { "WebSphere" }, new String[] { "WebLogic" }, new String[] { "JBoss" },
				new String[] { "Apache" }, new String[] { "Nginx" }, new String[] { "Jetty" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("前端技术"), new String[] { "Html" },
				new String[] { "CSS" }, new String[] { "JavaScript", "JS" }, new String[] { "JQuery" },
				new String[] { "elementUI" }, new String[] { "BootStrap" }, new String[] { "Vue" },
				new String[] { "React" }, new String[] { "Angular" }, new String[] { "Regular" },
				new String[] { "Node.js", "Nodejs" }, new String[] { "Ajax" }, new String[] { "微信小程序", "小程序" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("工具"), new String[] { "Eclipse" },
				new String[] { "MyEclipse" }, new String[] { "IntelliJ IDEA", "idea" }, new String[] { "HBuilder" },
				new String[] { "Git" }, new String[] { "SVN" }, new String[] { "Maven" }, new String[] { "Docker" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("概念"), new String[] { "集群" },
				new String[] { "分布式" }, new String[] { "微服务" }, new String[] { "消息队列", "消息机制" },
				new String[] { "网络编程" }, new String[] { "反射" }, new String[] { "设计模式" }, new String[] { "JVM", "虚拟机" },
				new String[] { "nio" }, new String[] { "多线程" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("消息中间件"), new String[] { "kafka" },
				new String[] { "ActiveMQ" }, new String[] { "RocketMQ" }, new String[] { "RabbitMQ" });
		Technologies.batchAdd(technologyMapping, new TechnologyType("其他"), new String[] { "XML" },
				new String[] { "YAML", "YML" }, new String[] { "JSON" }, new String[] { "RESTful" },
				new String[] { "Linux" }, new String[] { "UML" }, new String[] { "Shell" }, new String[] { "http" });
	}

	public static void batchAdd(Map<String, Technology> map, TechnologyType type, String[]... elements) {
		for (int i = 0; i < elements.length; i++) {
			String key = elements[i][0];
//			Technology technology = new Technology(key, type);
//			Technology technology=new Technology(key,type.getName());
//			technology.setAliases(elements[i]);
//			map.put(key, technology);
		}
	}

}
