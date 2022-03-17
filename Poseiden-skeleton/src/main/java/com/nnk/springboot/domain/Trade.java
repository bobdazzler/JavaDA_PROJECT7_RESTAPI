package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
@DynamicUpdate
@Entity
@Table(name = "trade")
public class Trade {
	// TODO: Map columns in data table TRADE with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TradeId")
	Integer tradeId;
	@Column(name = "account")
	String account;
	@Column(name = "type")
	String type;
	@Column(name = "buyQuantity")
	Double buyQuantity;
	@Column(name = "sellQuantity")
	Double sellQuantity;
	@Column(name = "buyPrice")
	Double buyPrice;
	@Column(name = "sellPrice")
	Double sellPrice;
	@Column(name = "benchmark")
	String benchmark;
	@Column(name = "tradeDate")
	Timestamp tradeDate;
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

	public Trade() {
	}

	public Trade(String account, String type, Double buyQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
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

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public Trade(Integer tradeId, String account, String type) {
		this.tradeId = tradeId;
		this.account = account;
		this.type = type;
	}

}
