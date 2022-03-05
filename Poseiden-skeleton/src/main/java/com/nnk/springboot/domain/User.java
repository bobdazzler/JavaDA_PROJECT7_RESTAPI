package com.nnk.springboot.domain;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;
@DynamicUpdate
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
	private String role;
    @OneToMany
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    List<BidList> bidList;
    @OneToMany
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    List<CurvePoint> curvePoint;
    @OneToMany
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    List<Rating> rating;
    @OneToMany
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    List<RuleName> ruleName;
    @OneToMany
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    List<Trade> trade;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
