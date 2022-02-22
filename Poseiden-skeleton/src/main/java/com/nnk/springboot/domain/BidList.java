package com.nnk.springboot.domain;

import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "BidListId")
	Integer bidListId;
	@NotBlank(message = "Account is mandatory")
	@Column(name = "account")
	String account;
	@NotBlank(message = "Type is mandatory")
	@Column(name = "type")
	String type;
	@Digits(fraction = 0,integer=13)
	@Column(name = "bidQuantity")
	Double bidQuantity;
	@Column(name = "askQuantity")
	Double askQuantity;
	@Column(name = "bid")
	Double bid;
	@Column(name = "ask")
	Double ask;
	@Column(name = "benchmark")
	String benchmark;
	@Column(name = "bidListDate")
	Timestamp bidListDate;
	@Column(name = "commentary")
	String commentary;
	@Column(name = "security")
	String security;
	@Column(name = "status")
	String status;
	@Column(name = "trader")
	String trader;
	@Column(name = "book")
	String book;
	@Column(name = "creationName")
	String creationName;
	@Column(name = "creationDate")
	Timestamp creationDate;
	@Column(name = "revisionName")
	String revisionName;
	@Column(name = "revisionDate")
	Timestamp revisionDate;
	@Column(name = "dealName")
	String dealName;
	@Column(name = "dealType")
	String dealType;
	@Column(name = "sourceListId")
	String sourceListId;
	@Column(name = "side")
	String side;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BidList() {
	}
}
