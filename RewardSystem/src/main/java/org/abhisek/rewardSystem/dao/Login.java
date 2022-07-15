package org.abhisek.rewardSystem.dao;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.abhisek.rewardSystem.bean.LoginBean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter @Setter  @NoArgsConstructor
@Table(name="login")
public class Login implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid")
	private int userid;
	@Column(name="username",unique=true)
	private String username;
	@Column(name="email")
	private String email;
	@Column(name="userpassword",length = 10000)
	private String userpassword;
	@Column(name="role")
	private String role;
	@JsonIgnore
	@Transient
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "login")
	Set<DailyTimesheetBean> timesheets=new HashSet<DailyTimesheetBean>();
	@JsonIgnore
	@Transient
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "login")
	Set<PointsBean>  pointBeans=new HashSet<PointsBean>();
	@JsonIgnore
	@Transient
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "login")
	Set<RewardsBean> rewardsBeans=new HashSet<RewardsBean>();
	@JsonIgnore
	@Transient
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "login")
	Set<Movies> movies =new HashSet<Movies>();
	@JsonIgnore
	@Transient
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "login")
	Set<Webseries> webseries=new HashSet<Webseries>();
	@JsonIgnore
	@Transient
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "login")
	Task task;
	public Login(LoginBean loginbean) {
		this.username=loginbean.getName();
		this.email=loginbean.getEmail();
	}
}
