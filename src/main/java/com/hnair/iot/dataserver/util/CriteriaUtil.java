package com.hnair.iot.dataserver.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.hnair.iot.dataserver.model.Filter;
import com.hnair.iot.dataserver.model.TimeUnit;

/**
 * Create criteria
 */
public class CriteriaUtil {

	public static Criteria createCriterias(List<Filter> listFilter, boolean isAndOr,TimeUnit timeUnit) {
		Criteria c = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		listFilter.forEach(filter -> {
			switch (filter.getOperator()) {
			case EQ:
				listC.add(Criteria.where(filter.getProperty()).is(filter.getValue()));
				break;
			case GT:
				listC.add(Criteria.where(filter.getProperty()).gt(filter.getValue()));
				break;
			case LT:
				listC.add(Criteria.where(filter.getProperty()).lt(filter.getValue()));
				break;
			case IS:
				listC.add(Criteria.where(filter.getProperty()).is(filter.getValue()));
				break;
			case GTE:
				listC.add(Criteria.where(filter.getProperty()).gte(filter.getValue()));
				break;
			case LTE:
				listC.add(Criteria.where(filter.getProperty()).lte(filter.getValue()));
				break;
			case NE:
				listC.add(Criteria.where(filter.getProperty()).ne(filter.getValue()));
				break;
			case IN:
				listC.add(Criteria.where(filter.getProperty()).in(filter.getValue()));
				break;
			case RANGETIME:
				String zoneId = timeUnit.getZoneId();
				if(filter.getStartTime() == null && filter.getEndTime() ==  null){
					ChronoUnit datePattern = TimeUtil.parseDuration(timeUnit.getTimeSpan());
					Long timeLength = timeUnit.getAmountToAdd();
					ZonedDateTime todayLocalTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(zoneId));
					ZonedDateTime lastWeek = ZonedDateTime.of(LocalDateTime.now().plus(timeLength,datePattern),ZoneId.of(zoneId));
					listC.add(Criteria.where(filter.getProperty())
							.gte(lastWeek.toLocalDateTime())
							.lt(todayLocalTime.toLocalDateTime()));
				}else{
					listC.add(Criteria.where(filter.getProperty())
							.gte(ZonedUtil.zonedTime(filter.getStartTime(),zoneId))
							.lt(ZonedUtil.zonedTime(filter.getEndTime(),zoneId)));
				}
				break;
			default:
				break;
			}

		});

		if (listC.size() > 0) {
			Criteria[] cs = new Criteria[listC.size()];
			if (isAndOr) {
				c.andOperator(listC.toArray(cs));
			}
			else {
				c.orOperator(listC.toArray(cs));
			}
		}
		return c;
	}
}