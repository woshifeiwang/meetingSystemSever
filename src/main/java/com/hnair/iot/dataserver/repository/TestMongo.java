package com.hnair.iot.dataserver.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hnair.iot.dataserver.model.DataRecordEvent;
import com.hnair.iot.dataserver.model.DataRecordModel;
import com.hnair.iot.dataserver.model.Filter;
import com.hnair.iot.dataserver.model.TimeUnit;
import com.hnair.iot.dataserver.util.Criterias;
import com.hnair.iot.dataserver.util.Files;
import com.hnair.iot.dataserver.util.GenericAvroConverter;
import com.hnair.iot.dataserver.util.Page;
import com.hnair.iot.dataserver.util.ZonedUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestMongo {

	@Autowired
	Device device;

	@Autowired
	TimeUnit timeUnit;

	@Test
	public void findAll() {
		Criterias criterias = new Criterias();

		List<Filter> list = new ArrayList<Filter>();
		list.add(Filter.eq("event.name", "xiaoli"));
		list.add(Filter.lt("event.age", 25));

		criterias.setListFilter(list);
		criterias.setAndOr(false);

		// Page<DataRecordEvent> listAll = device.listAll(0, 2, criterias, "ir");
		// System.out.println(listAll.toString());
	}

	@Test
	public void findRangTimes() {

		// of("UTC+08:00")
		// ZonedDateTime todayLocalTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC+00:00"));
		// ZonedDateTime lastWeek = ZonedDateTime.of(LocalDateTime.now().plus(-1, ChronoUnit.WEEKS),
		// ZoneId.of("UTC+00:00"));
		// ZonedDateTime todayLocalTime = null;
		// ZonedDateTime lastWeek = null;
		// Page<DataRecordEvent> findByRangeTime = device.findByRangeTime(0, 2, lastWeek, todayLocalTime, "ir");
		// System.out.println(findByRangeTime.toString());
	}

	static String ROOT = System.getProperty("user.dir") + "/src/main/resources/schemas/";

	@Test
	public void saveTesst() {

		DataRecordModel dataRecord = new DataRecordModel();

		String f = ROOT + "client_schema.avsc";
		String systemSchema = Files.getFileContent(f);

		String u = ROOT + "user_schema.avsc";
		String userSchema = Files.getFileContent(u);

		String userData = "{\"name\":\"lz1111111q\",\"age\":1112,\"createdTime\":\"2016-12-2 15:23:00\",\"values\":22}";
		// String sysData="{\"serialNumber\":\"num1ber\",\"name\":\"TV\",\"os\":\"windows\",\"description\":\"Good
		// TV\",\"networkType\":\"WiFi\"}";

		// String sysData = "{\"serialNumber\":\"num1ber\",\"name\":\"TV\",\"os\":\"windows\",\"description\": \"Good
		// TV\",\"startTime\"ISODate(\"'"+localDateTime+"'\"), \"networkType\": \"WiFi\"}";

		long instant = Instant.now().toEpochMilli();

		String sysData = "{\"serialNumber\": \"num1ber\",\"name\": \"TV\",\"os\": \"windows\",\"description\":\"Good TV\",\"startTime\":"
				+ instant + ",\"networkType\": \"WiFi\"}";

		System.out.println(sysData);
		byte[] userByteData = GenericAvroConverter.toRawData(userData, userSchema);
		byte[] sysByteData = GenericAvroConverter.toRawData(sysData, systemSchema);

		dataRecord.setSystemSchema(systemSchema);
		dataRecord.setUserSchema(userSchema);
		dataRecord.setUserData(userByteData);
		dataRecord.setSystemData(sysByteData);

		List<DataRecordModel> listData = new ArrayList<DataRecordModel>();
		listData.add(dataRecord);
		device.save(listData, "testDevice");

	}

	@Test
	public void deleteByCriterias() {
		// Criterias criterias = new Criterias();
		// List<Filter> list = new ArrayList<Filter>();
		// list.add(Filter.eq("event.name", "wuhu"));
		// list.add(Filter.lt("event.age", 25));
		// criterias.setListFilter(list);
		// criterias.setAndOr(false);
		// device.deleteByCriterias(criterias,"ir");
	}

	@Test
	public void findByCriterias() {

		ZonedDateTime todayLocalTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC+00:00"));
		ZonedDateTime lastWeek = ZonedDateTime.of(LocalDateTime.now().plus(-1, ChronoUnit.WEEKS),
				ZoneId.of("UTC+00:00"));
		System.out.println(todayLocalTime);
		System.out.println(lastWeek);
		Criterias criterias = new Criterias();
		List<Filter> list = new ArrayList<Filter>();
		list.add(Filter.rangeTime("header.startTime", null, null));
		criterias.setListFilter(list);
		criterias.setAndOr(false);
		criterias.setPageNo(0);
		criterias.setPageSize(2);
		Page<DataRecordEvent> findByCriterias = device.findByCriterias(criterias, "ir");
		System.out.println(findByCriterias);
	}

	@Test
	public void testAddTime() {
		// System.out.println(timeSpan);
		// System.out.println(timeUnit.getAmountToAdd());

		// Duration parseDuration = TimeUtil.parseDuration("-1wees");
		// System.out.println(parseDuration);

		// int findIndexOfNonDigit = TimeUtil.findIndexOfNonDigit("2231");
		// System.out.println(findIndexOfNonDigit);

		// ChronoUnit parseDuration = TimeUtil.parseDuration("month");
		// System.out.println(parseDuration.toString());
	}

	@Test
	public void testTest() {

		ZonedDateTime todayLocalTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
		System.out.println(todayLocalTime);
		System.out.println(ZonedUtil.zonedTime(todayLocalTime, timeUnit.getZoneId()));

	}

	@Test
	public void testShijian() {
		ZonedDateTime todayLocalTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC+00:00"));
		ZonedDateTime lastWeek = ZonedDateTime.of(LocalDateTime.now().plus(-1, ChronoUnit.WEEKS),
				ZoneId.of("UTC+00:00"));
		long today = todayLocalTime.toInstant().toEpochMilli();
		long lastweek = lastWeek.toInstant().toEpochMilli();

		System.out.println(today);
		System.out.println(lastweek);
		System.out.println(today > lastweek);
	}
}
