package com.hnair.iot.dataserver.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hnair.iot.dataserver.model.DataRecordEvent;
import com.hnair.iot.dataserver.model.DataRecordModel;
import com.hnair.iot.dataserver.model.MongoDataEventModel;
import com.hnair.iot.dataserver.model.TimeUnit;
import com.hnair.iot.dataserver.util.CriteriaUtil;
import com.hnair.iot.dataserver.util.Criterias;
import com.hnair.iot.dataserver.util.GenericAvroConverter;
import com.hnair.iot.dataserver.util.Page;
import com.hnair.iot.dataserver.util.Pages;
import com.mongodb.util.JSON;

@Service
public class DeviceImpl implements Device {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceImpl.class);

	@Autowired
	TimeUnit timeUnit;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void save(List<DataRecordModel> dateEvents, String productName) {

		createCollection(productName);
		dateEvents.forEach(date -> {
			String sysJson = GenericAvroConverter.toJson(date.getSystemData(), date.getSystemSchema());
			String userJson = GenericAvroConverter.toJson(date.getUserData(), date.getUserSchema());

			DataRecordEvent dto = new DataRecordEvent();
			dto.setHeader(sysJson);
			dto.setMeta(userJson);
			MongoDataEventModel dataEventModel = new MongoDataEventModel(dto);

			Schema.Parser parser = new Schema.Parser();
			Schema toSchema = parser.parse(date.getUserSchema());
			List<Field> fields = toSchema.getFields();
			IndexOperations indexOps = mongoTemplate.indexOps(toSchema.getName().toLowerCase());
			List<IndexInfo> indexInfo = indexOps.getIndexInfo();
			if (indexInfo.isEmpty()) {
				for (Field s : fields) {
					String name = s.name();
					Map<String, Object> objectProps = s.getObjectProps();
					for (Entry<String, Object> entry : objectProps.entrySet()) {
						if (entry.getKey().equals("index") && (boolean) entry.getValue()) {
							System.out.println("index join");
							Index index = new Index();
							Direction direction = Direction.ASC;
							index.on(name, direction);
							indexOps.ensureIndex(index);
						}
					}
				}
			}
			mongoTemplate.save(dataEventModel, productName);
		});
	}

	// Find By Criterias
	@Override
	public Page<DataRecordEvent> findByCriterias(Criterias criterias, String productName) {
		LOG.debug("Retrieving the list of all record");
		int pageNo = criterias.getPageNo();
		int pageSize = criterias.getPageSize();
		Criteria createCriteria = CriteriaUtil.createCriterias(criterias.getListFilter(), criterias.isAndOr(),
				timeUnit);
		Query query = new Query(createCriteria).with(Pages.createPageRequest(pageNo, pageSize));
		long totalCount = mongoTemplate.count(query, MongoDataEventModel.class, productName);
		List<DataRecordEvent> content = returnList(query, productName);
		return Pages.createPage(pageNo, pageSize, totalCount, content);
	}

	// Delete By Criterias
	@Override
	public void deleteByCriterias(Criterias criterias, String productName) {
		Criteria createCriterias = CriteriaUtil.createCriterias(criterias.getListFilter(), criterias.isAndOr(),
				timeUnit);
		Query query = new Query(createCriterias);
		mongoTemplate.remove(query, MongoDataEventModel.class, productName);
	}

	// GraplhQL
	// Id

	// Ceate by Collection Name
	public void createCollection(String collectionName) {
		try {
			if (!mongoTemplate.collectionExists(collectionName)) {
				mongoTemplate.createCollection(collectionName);
			}
		}
		catch (Exception e) {
			LOG.warn("Failed to create collection {} due to", collectionName, e);
		}
	}

	// return List<MongoDataEventModel>
	public List<DataRecordEvent> returnList(Query query, String productName) {
		List<MongoDataEventModel> records = mongoTemplate.find(query, MongoDataEventModel.class, productName);
		List<DataRecordEvent> content = records.stream().map(model -> {
			String header = JSON.serialize(model.getHeader());
			String meta = JSON.serialize(model.getEvent());
			return new DataRecordEvent(meta, header);
		}).collect(Collectors.toList());
		return content;
	}

}
