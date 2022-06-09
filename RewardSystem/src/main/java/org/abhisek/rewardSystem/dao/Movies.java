package org.abhisek.rewardSystem.dao;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="Movies")
public class Movies {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="movieid")
	int movieid;
	@Column(name="ott")
	String ott;
	@Column(name="moviename")
	String movieName;
	@Column(name="watched")
	boolean watched;
	@Column(name="watcheddate")
	Date watchedDate;
	@Column(name="watchlist")
	boolean watchlist;
	@Column(name="watchlistpriority")
	int watchlistpriority;
	@Expose(serialize = false)
	@ManyToOne
	private Login login;
}
