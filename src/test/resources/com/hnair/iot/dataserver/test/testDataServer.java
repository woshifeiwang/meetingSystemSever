package com.hnair.iot.dataserver.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hnair.iot.dataserver.Application;
import com.hnair.iot.dataserver.repository.Device;

@SpringBootTest(classes=Application.class)
public class testDataServer {
	
	@Autowired
	Device device;
	
	@Test
	public void test(){
		
		
	
		
	}
	
	

}
