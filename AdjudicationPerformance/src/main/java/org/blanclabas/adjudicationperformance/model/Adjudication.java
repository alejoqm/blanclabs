package org.blanclabas.adjudicationperformance.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "adjudication")
public class Adjudication {

	@Id
	@GeneratedValue(generator = "uniqueRowId")
	@GenericGenerator(name = "uniqueRowId", strategy = "org.hibernate.id.IdentityGenerator")
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hit_id",  referencedColumnName = "id", nullable = false)
	private Hit hit;

	@Column(name = "actor_id")
	private String actorId;

	@Column(name = "actor_name")
	private String actorName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id", referencedColumnName = "id", nullable = false)
	private DomainActions domainAction;

	@Column(name = "state")
	private String state;

	@Column(name = "create_at")
	private Date createAt;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "adjudication_id")
	private List<Attribute> attributes;

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

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public DomainActions getDomainAction() {
		return domainAction;
	}

	public void setDomainAction(DomainActions domainAction) {
		this.domainAction = domainAction;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Adjudication that = (Adjudication) o;
		return Objects.equals(id, that.id) && Objects.equals(hit, that.hit) && Objects.equals(actorId, that.actorId) && Objects
				.equals(actorName, that.actorName) && Objects.equals(domainAction, that.domainAction) && Objects.equals(state, that.state) && Objects
				.equals(createAt, that.createAt) && Objects.equals(attributes, that.attributes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, hit, actorId, actorName, domainAction, state, createAt, attributes);
	}
}
