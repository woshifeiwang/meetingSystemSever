package com.hnair.iot.dataserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hnair.iot.dataserver.model.DataRecordEvent;
import com.hnair.iot.dataserver.model.Filter;
import com.hnair.iot.dataserver.model.TimeUnit;
import com.hnair.iot.dataserver.repository.Device;
import com.hnair.iot.dataserver.util.Criterias;
import com.hnair.iot.dataserver.util.Page;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	TimeUnit  timeUnit;

	@Autowired
	Device testa;

	@RequestMapping("a")
	public void test() {
		
		List<Filter> list = new ArrayList<Filter>();
		list.add(Filter.rangeTime("header.startTime", null, null));
		Criterias criterias = new Criterias(list,0,2);
		
//		criterias.setListFilter(list);
//      criterias.setAndOr(false);
//		criterias.setPageNo(0);
//		criterias.setPageSize(2);
		
		
		Page<DataRecordEvent> findByCriterias = testa.findByCriterias(criterias, "ir");
		System.out.println(findByCriterias);

	}
}
