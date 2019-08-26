package org.blanclabas.adjudicationperformance.store;

import java.io.Serializable;
import org.blanclabas.adjudicationperformance.utils.HibernateConnection;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UniqueRowIdGenerator implements IdentifierGenerator {

	private static final String GET_UNIQUE_ROWID = "SELECT unique_rowid() FROM Domain";

	@Override
	public Serializable generate(SharedSessionContractImplementor session,
			Object object) throws HibernateException {
		return Long.valueOf(HibernateConnection.getSession().createQuery(GET_UNIQUE_ROWID).uniqueResult().toString());
	}

}
