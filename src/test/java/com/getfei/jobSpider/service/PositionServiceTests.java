package com.getfei.jobSpider.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PositionServiceTests {

	@Autowired
	IPositionService service;
	@Test
	public void getPosition() {
		service.getPosition();
	}

}
