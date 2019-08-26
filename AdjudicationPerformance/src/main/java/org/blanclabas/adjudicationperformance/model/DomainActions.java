package org.blanclabas.adjudicationperformance.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "domain_actions")
public class DomainActions {

	@Id
	@GeneratedValue(generator = "uniqueRowId")
	@GenericGenerator(name = "uniqueRowId", strategy = "org.blanclabas.adjudicationperformance.store.UniqueRowIdGenerator")
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "domain_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Domain domain;

	@Column(name = "action")
	private String action;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DomainActions that = (DomainActions) o;
		return Objects.equals(id, that.id) && Objects.equals(domain.getId(), that.domain.getId()) && Objects.equals(action, that.action);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, domain, action);
	}

}
