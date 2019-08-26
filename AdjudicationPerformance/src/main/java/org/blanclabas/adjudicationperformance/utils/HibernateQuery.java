package org.blanclabas.adjudicationperformance.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.blanclabas.adjudicationperformance.model.Adjudication;
import org.blanclabas.adjudicationperformance.model.AdjudicationState;
import org.blanclabas.adjudicationperformance.model.Attribute;
import org.blanclabas.adjudicationperformance.model.Domain;
import org.blanclabas.adjudicationperformance.model.DomainActions;
import org.blanclabas.adjudicationperformance.model.Hit;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateQuery {

	private static final  String hql = "SELECT D.id, D.name FROM Domain D WHERE D.name = 'DDIQ'";
	private static final String DOMAIN_ACTION = "SELECT D.id, D.domain, D.action from DomainActions D Where D.action = 'CONFIRM'  AND D.domain = :domain";


	public static void insertAdjudications(Properties prop) {
		HibernateConnection.getSessionFactory(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password"));
		SessionFactory sessionFactory = HibernateConnection.getSessionFactory(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password"));
		HibernateConnection.setSession(sessionFactory.openSession());
		Transaction trans = HibernateConnection.getSession().beginTransaction();

		TimeUtils timeUtils = new TimeUtils("Starting hibernate process");

		Domain domain = getDomain();

		DomainActions domainActions = getDomainActions(domain);

		Hit hit = getHit(domain);


		List<Adjudication> adjudicationList = new ArrayList<>();
		for(int i = 0; i < 1; i++) {
			Adjudication adjudication = new Adjudication();
			adjudication.setActorId("actorId");
			adjudication.setActorName("actorName");
			adjudication.setCreateAt(new Date(System.currentTimeMillis()));
			adjudication.setDomainAction(domainActions);
			adjudication.setHit(hit);
			adjudication.setState("true");
			adjudicationList.add(adjudication);
		}

		AdjudicationState adjudicationState = new AdjudicationState();
		adjudicationState.setAction(domainActions);
		adjudicationState.setHit(hit);
		adjudicationState.setState(true);
		adjudicationState.setVersion(1l);

		Attribute attribute = new Attribute();
		attribute.setAdjudication(adjudicationList.get(0));
		attribute.setKey("Comment");
		attribute.setValue("This is a comment");
		adjudicationList.get(0).setAttributes(Arrays.asList(attribute));

		hit.setAdjudications(adjudicationList);
		hit.setAdjudicationStates(Arrays.asList(adjudicationState));

		HibernateConnection.getSession().save(hit);
		trans.commit();

		System.out.println(timeUtils.stop());
	}

	private static DomainActions getDomainActions(Domain domain) {
		DomainActions domainActions = new DomainActions();
		Object[] object = (Object[]) HibernateConnection.getSession().createQuery(DOMAIN_ACTION).setParameter("domain", domain).getSingleResult();
		domainActions.setId((Long) object[0]);
		domainActions.setAction((String) object[2]);
		domainActions.setDomain((Domain) object[1]);
		return domainActions;
	}

	private static Domain getDomain() {
		Domain domain = new Domain();
		Object[] object = (Object[]) HibernateConnection.getSession().createQuery(hql).getSingleResult();
		domain.setId((Long) object[0]);
		domain.setName((String) object[1]);
		return domain;
	}

	private static Hit getHit(Domain domain) {
		Hit hit = new Hit();
		hit.setContentId("1");
		hit.setClientId("lddiq");
		hit.setDomain(domain);
		hit.setCaseId("1");
		hit.setContentType("INCIDENT");
		return hit;
	}

}
