package org.blanclabas.adjudicationperformance.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.blanclabas.adjudicationperformance.Application;
import org.blanclabas.adjudicationperformance.utils.DBUtils;
import org.blanclabas.adjudicationperformance.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdjudicationDao {

	private static Logger LOG = LoggerFactory.getLogger(Application.class);

	private static final String DELETE_BY_USER = "DELETE FROM hit WHERE client_id = '%s'";
	private static final String DELETE_ADJUDICATIONS = "DELETE FROM adjudication WHERE hit_id IN (%s)";
	private static final String DELETE_ADJUDICATION_STATE = "DELETE FROM adjudication_state WHERE hit_id IN (%s)";
	private static final String DELETE_ATTRIBUTE_STATE = "DELETE FROM attribute WHERE adjudication_id IN (%s)";


	private static final String SELECT_ADJUDICATIONS = "SELECT id FROM adjudication WHERE hit_id IN (%s)";
	private static final String SELECT_HITS = "SELECT id FROM hit WHERE client_id = '%s'";

	private static final String INSERT_HIT = "INSERT INTO hit(id, content_id, client_id, domain_id, case_id, content_type) VALUES(987562, '1', '%s', [DOMAIN], '1', 'RECORD_CLUSTER');";

	public static void deleteByUser(final String user) {
		TimeUtils timeUtils = new TimeUtils("Cleaning DataBase");
		List<Map<String, Object>> hits = DBUtils.jdbcTemplate().queryForList(String.format(SELECT_HITS, user));
		List<Integer> hitsIds = hits.stream().map(item -> MapUtils.getInteger(item, "id", 0)).collect(Collectors.toList());

		if(hitsIds.size() > 0) {
			List<Map<String, Object>> adjudications = DBUtils.jdbcTemplate().queryForList(String.format(SELECT_ADJUDICATIONS, getInClause(hitsIds)));
			List<Integer> adjudicationsIds = adjudications.stream().map(item -> MapUtils.getInteger(item, "id", 0)).collect(Collectors.toList());

			if(adjudicationsIds.size() > 0) {
				DBUtils.jdbcTemplate().execute(String.format(DELETE_ATTRIBUTE_STATE, getInClause(adjudicationsIds)));
			}

			DBUtils.jdbcTemplate().execute(String.format(DELETE_ADJUDICATION_STATE, getInClause(hitsIds)));
			DBUtils.jdbcTemplate().execute(String.format(DELETE_ADJUDICATIONS, getInClause(hitsIds)));
		}

		DBUtils.jdbcTemplate().execute(String.format(DELETE_BY_USER, user));
		LOG.info(timeUtils.stop());
	}

	public static void insertHit(final String user, final String domainId) {
		TimeUtils timeUtils = new TimeUtils("Inserting hit");
		String statement = String.format(INSERT_HIT, user);
		statement = statement.replace("[DOMAIN]", domainId);
		DBUtils.jdbcTemplate().execute(statement);
		LOG.info(timeUtils.stop());
	}

	public static void executeBatch(final String batch, final String action) {
		TimeUtils timeUtils = new TimeUtils("Executing batch: ");
		DBUtils.jdbcTemplate().execute(batch.replace("[ACTION]", action));
		LOG.info(timeUtils.stop());
	}

	private static String getInClause(List<Integer> list) {
		return list.stream()
				.map(String::valueOf)
				.collect(Collectors.joining(","));
	}
}
