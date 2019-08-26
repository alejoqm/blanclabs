package org.blanclabas.adjudicationperformance.model;

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
@Table(name = "adjudication_state")
public class AdjudicationState {

	@Id
	@GeneratedValue(generator = "uniqueRowId")
	@GenericGenerator(name = "uniqueRowId", strategy = "org.hibernate.id.IdentityGenerator")
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hit_id", referencedColumnName = "id", nullable = false)
	private Hit hit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id", referencedColumnName = "id", nullable = false)
	private DomainActions action;

	@Column(name = "state")
	private Boolean state;

	@Column(name = "version")
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hit getHit() {
		return hit;
	}

	public void setHit(Hit hit) {
		this.hit = hit;
	}

	public DomainActions getAction() {
		return action;
	}

	public void setAction(DomainActions action) {
		this.action = action;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}


}
