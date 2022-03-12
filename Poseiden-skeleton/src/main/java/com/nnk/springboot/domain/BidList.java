package com.nnk.springboot.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
@DynamicUpdate
@Entity
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "bidListId")
	Integer bidListId;
	@NotBlank(message = "Account is mandatory")
	@Column(name = "account")
	String account;
	@NotBlank(message = "Type is mandatory")
	@Column(name = "type")
	String type;
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
	@Column(name = "user_id")
	Integer userId;
	
	public Integer getBidListId() {
		return bidListId;
	}
	public void setBidListId(Integer bidListId) {
		this.bidListId = bidListId;
	}
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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Double getBidQuantity() {
		return bidQuantity;
	}
	public void setBidQuantity(Double bidQuantity) {
		this.bidQuantity = bidQuantity;
	}
	public BidList(@NotBlank(message = "Account is mandatory") String account,
			@NotBlank(message = "Type is mandatory") String type, Double bidQuantity) {
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}
	public BidList(Integer bidListId, @NotBlank(message = "Account is mandatory") String account,
			@NotBlank(message = "Type is mandatory") String type, Double bidQuantity) {
		this.bidListId = bidListId;
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}
	
}
