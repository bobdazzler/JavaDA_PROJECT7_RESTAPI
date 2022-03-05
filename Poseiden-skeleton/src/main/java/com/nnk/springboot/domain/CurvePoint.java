package com.nnk.springboot.domain;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@DynamicUpdate
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
	// TODO: Map columns in data table CURVEPOINT with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	Integer id;
	@NotNull(message = "must not be null")
	@Column(name = "CurveId")
	Integer curveId;
	@Column(name = "asOfDate")
	Timestamp asOfDate;
	@Column(name = "term")
	Double term;
	@Column(name = "value")
	Double value;
	@Column(name = "creationDate")
	Timestamp creationDate;
	@Column(name = "user_id")
	Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public CurvePoint() {
	}

	public CurvePoint(
			@NotNull @NotBlank @Length(min = 6, message = "The field must be at least 6 characters") Integer curveId,
			Double term, Double value) {
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

}
