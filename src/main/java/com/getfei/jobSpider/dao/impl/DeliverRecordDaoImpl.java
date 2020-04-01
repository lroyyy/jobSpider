package com.getfei.jobSpider.dao.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.IDeliverRecordDao;
import com.getfei.jobSpider.entity.DeliverRecord;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class DeliverRecordDaoImpl extends MongoTemplateDaoImpl<DeliverRecord> implements IDeliverRecordDao {

	@Override
	public boolean update(DeliverRecord deliverRecord) {
		Update update = new Update();
		try {
			Field[] fields = DeliverRecord.class.getDeclaredFields();
			for (Field field : fields) {
				String fieldName=field.getName();
				if(fieldName.equals("_id")||fieldName.equals("serialVersionUID")) {//跳过_id和serialVersionUID
					continue;
				}
				field.setAccessible(true);
				update.set(fieldName, field.get(deliverRecord));
			}
		} catch (Exception e) {
			return false;
		}
		UpdateResult result=mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(deliverRecord.get_id())), update,
				DeliverRecord.class, getCollectionName());
		return result.wasAcknowledged();
	}

	@Override
	public List<DeliverRecord> findByMultipleCriteria(Map<String, Object> criteriaMap) {
		Criteria criteria=new Criteria();
		List<Criteria> criterias=new ArrayList<>();
		criteriaMap.forEach((k,v)->{
			Criteria tmpCriteria=null;
			if(k.equals("companyName")||k.equals("companyShorthand")||k.equals("address")) {//模糊查询
				tmpCriteria=Criteria.where(k).regex("^.*"+v+".*$","i");
				logger.info("模糊k="+k+",v="+v);
			}else {//默认精确查询
				tmpCriteria=Criteria.where(k).is(v);
				logger.info("精确k="+k+",v="+v);
			}
			criterias.add(tmpCriteria);
		});
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		List<DeliverRecord> deliverRecords=mongoTemplate.find(Query.query(criteria),DeliverRecord.class,getCollectionName());
		return deliverRecords;
	}

}
