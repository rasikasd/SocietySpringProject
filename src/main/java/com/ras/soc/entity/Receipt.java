package com.ras.soc.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Receipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer	rcpt_id;
	
	private BigDecimal paid_amt;
	
	private Instant rcptdate;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Owner owner;

	public Integer getRcpt_id() {
		return rcpt_id;
	}

	public void setRcpt_id(Integer rcpt_id) {
		this.rcpt_id = rcpt_id;
	}

	public BigDecimal getPaid_amt() {
		return paid_amt;
	}

	public void setPaid_amt(BigDecimal paid_amt) {
		this.paid_amt = paid_amt;
	}

	public Instant getRcptdate() {
		return rcptdate;
	}

	public void setRcptdate(Instant rcptdate) {
		this.rcptdate = rcptdate;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Receipt [rcpt_id=" + rcpt_id + ", paid_amt=" + paid_amt + ", rcptdate=" + rcptdate + ", owner=" + owner
				+ ", getRcpt_id()=" + getRcpt_id() + ", getPaid_amt()=" + getPaid_amt() + ", getRcptdate()="
				+ getRcptdate() + ", getOwner()=" + getOwner() + "]";
	}
	
	
	
}
