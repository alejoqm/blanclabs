package org.blanclabas.adjudicationperformance.utils;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBUtils {

	private static JdbcTemplate jdbcTemplate = null;

	public static void init(final String url, final String username, final String password) {
		System.out.println(String.format("Initialize JDBC Template %s", url));
		final HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setAutoCommit(true);
		ds.setDriverClassName("org.postgresql.Driver");
		jdbcTemplate = new JdbcTemplate(ds);

		/*try {
			initLiquidBase();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}*/
	}

	public static void initLiquidBase() throws SQLException, DatabaseException {
		java.sql.Connection c = jdbcTemplate.getDataSource().getConnection();
		Liquibase liquibase = null;
		try {
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
			liquibase = new Liquibase("migrations/liquibase-changeLog.xml", new FileSystemResourceAccessor(), database);
			liquibase.update("");
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (LiquibaseException e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.rollback();
					c.close();
				} catch (SQLException e) {
					//nothing to do
				}
			}
		}
	}

	public static JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}

}
