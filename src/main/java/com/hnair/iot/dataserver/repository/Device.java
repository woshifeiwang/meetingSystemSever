package com.hnair.iot.dataserver.repository;

import java.util.List;

import com.hnair.iot.dataserver.model.DataRecordEvent;
import com.hnair.iot.dataserver.model.DataRecordModel;
import com.hnair.iot.dataserver.util.Criterias;
import com.hnair.iot.dataserver.util.Page;

public interface Device {

	void save(List<DataRecordModel> dateEvents, String productName);

	Page<DataRecordEvent> findByCriterias(Criterias criterias, String productName);

	void deleteByCriterias(Criterias criterias, String productName);
}
