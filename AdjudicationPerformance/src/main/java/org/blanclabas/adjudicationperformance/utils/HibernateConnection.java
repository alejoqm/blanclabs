package org.blanclabas.adjudicationperformance.utils;

import java.util.Properties;
import org.blanclabas.adjudicationperformance.model.Adjudication;
import org.blanclabas.adjudicationperformance.model.AdjudicationState;
import org.blanclabas.adjudicationperformance.model.Attribute;
import org.blanclabas.adjudicationperformance.model.Domain;
import org.blanclabas.adjudicationperformance.model.DomainActions;
import org.blanclabas.adjudicationperformance.model.Hit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateConnection {

	private static SessionFactory sessionFactory;

	private static Session session;

	public static Session getSession() {
		return session;
	}

	public static void setSession(Session session) {
		HibernateConnection.session = session;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			throw new UnsupportedOperationException();
		}
		return sessionFactory;
	}

	public static  SessionFactory getSessionFactory(final String url, final String username, final String password) {
		if(sessionFactory == null) {
			Configuration configuration = new Configuration();

			Properties settings = new Properties();
			settings.put(Environment.DRIVER, "org.postgresql.Driver");
			settings.put(Environment.URL, url);
			settings.put(Environment.USER, username);
			settings.put(Environment.PASS, password);
			settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
			settings.put(Environment.SHOW_SQL, "true");
			settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
			settings.put(Environment.HBM2DDL_AUTO, "update");

			configuration.setProperties(settings);

			configuration.addAnnotatedClass(Adjudication.class);
			configuration.addAnnotatedClass(AdjudicationState.class);
			configuration.addAnnotatedClass(Attribute.class);
			configuration.addAnnotatedClass(Domain.class);
			configuration.addAnnotatedClass(DomainActions.class);
			configuration.addAnnotatedClass(Hit.class);

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}
}
