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
@Table(name = "attribute")
public class Attribute {

	@Id
	@GeneratedValue(generator = "uniqueRowId")
	@GenericGenerator(name = "uniqueRowId", strategy = "org.hibernate.id.IdentityGenerator")
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adjudication_id", referencedColumnName = "id", nullable = false)
	private Adjudication adjudication;

	@Column(name = "key")
	private String key;

	@Column(name = "value")
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Adjudication getAdjudication() {
		return adjudication;
	}

	public void setAdjudication(Adjudication adjudication) {
		this.adjudication = adjudication;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
