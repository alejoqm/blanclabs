package org.blanclabas.adjudicationperformance.model;

import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "hit", uniqueConstraints = {@UniqueConstraint(columnNames = {"content_id", "domain_id"})})
public class Hit {

	@Id
	@GeneratedValue(generator = "uniqueRowId")
	@GenericGenerator(name = "uniqueRowId", strategy = "org.hibernate.id.IdentityGenerator")
	@Column(name = "id")
	private Long id;

	@Column(name = "client_id")
	private String clientId;

	@Column(name = "content_id")
	private String contentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "domain_id", referencedColumnName = "id", nullable = false)
	private Domain domain;

	@Column(name = "case_id")
	private String caseId;

	@Column(name = "content_type")
	private String contentType;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "hit_id")
	private List<Adjudication> adjudications;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "hit_id")
	private List<AdjudicationState> adjudicationStates;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public List<Adjudication> getAdjudications() {
		if(adjudications == null) {
			adjudications = new ArrayList<>();
		}
		return adjudications;
	}

	public void setAdjudications(List<Adjudication> adjudications) {
		this.adjudications = adjudications;
	}

	public List<AdjudicationState> getAdjudicationStates() {
		if(adjudicationStates == null) {
			adjudicationStates = new ArrayList<>();
		}
		return adjudicationStates;
	}

	public void setAdjudicationStates(List<AdjudicationState> adjudicationStates) {
		this.adjudicationStates = adjudicationStates;
	}
}
