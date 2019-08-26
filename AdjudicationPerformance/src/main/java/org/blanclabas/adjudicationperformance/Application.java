package org.blanclabas.adjudicationperformance;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.persistence.NamedNativeQuery;
import org.blanclabas.adjudicationperformance.model.Domain;
import org.blanclabas.adjudicationperformance.model.Hit;
import org.blanclabas.adjudicationperformance.service.PerformanceService;
import org.blanclabas.adjudicationperformance.utils.DBUtils;
import org.blanclabas.adjudicationperformance.utils.HibernateConnection;
import org.blanclabas.adjudicationperformance.utils.HibernateQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private static Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		LOG.info("Starting new process: " + new Date());
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream("application.properties")) {
			// load a properties file
			prop.load(input);
			if(true) {
				HibernateQuery.insertAdjudications(prop);
			} else {
				DBUtils.init(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password"));

				PerformanceService performanceService = new PerformanceService();
				performanceService.executePerformanceTest(prop.getProperty("batch.file"));
			}
			LOG.info("End process: " + new Date());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

