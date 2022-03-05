package com.nnk.springboot.domain;

import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
@DynamicUpdate
@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
	 @Id
	    @GeneratedValue(strategy= GenerationType.AUTO)
	 @Column(name = "Id")
	Integer id;
	 @Column(name = "moodysRating")
	String moodysRating;
	 @Column(name = "sandPRating")
	String sandPRating;
	 @Column(name = "fitchRating")
	String fitchRating;
	 @Column(name = "orderNumber")
	Integer orderNumber;
	 @Column(name = "user_id")
		Integer userId;
	public Rating() {
	}
	public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMoodysRating() {
		return moodysRating;
	}
	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}
	public String getSandPRating() {
		return sandPRating;
	}
	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}
	public String getFitchRating() {
		return fitchRating;
	}
	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
}
