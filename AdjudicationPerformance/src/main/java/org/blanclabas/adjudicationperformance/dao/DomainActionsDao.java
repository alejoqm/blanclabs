package org.blanclabas.adjudicationperformance.dao;

import java.util.List;
import java.util.Map;
import org.blanclabas.adjudicationperformance.utils.DBUtils;

public class DomainActionsDao {

	private static String GET_ID = "select id from domain_actions where action = '%s' and domain_id = %s;";
	private static String GET_DOMAIN_ID = "select id from \"domain\" where name = '%s';";

	public static String getDomainActionId(final String action, final String domainId) {
		List<Map<String, Object>> response = DBUtils.jdbcTemplate().queryForList(String.format(GET_ID, action, domainId));
		return response.get(0).get("id").toString();
	}

	public static String getDomainId(final String domain) {
		List<Map<String, Object>> response = DBUtils.jdbcTemplate().queryForList(String.format(GET_DOMAIN_ID, domain));
		return response.get(0).get("id").toString();
	}
}
