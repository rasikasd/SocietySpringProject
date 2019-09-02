package com.ras.soc.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private BigDecimal billamount;
	private BigDecimal prevbillamt;
	private BigDecimal payment;
	private BigDecimal adjustment;
	private BigDecimal currcharges;
	private BigDecimal totalamt;
	private Instant billdate;
	private Instant duedate;
	
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Owner owner;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public BigDecimal getBillamount() {
		return billamount;
	}


	public void setBillamount(BigDecimal billamount) {
		this.billamount = billamount;
	}


	public BigDecimal getPrevbillamt() {
		return prevbillamt;
	}


	public void setPrevbillamt(BigDecimal prevbillamt) {
		this.prevbillamt = prevbillamt;
	}


	public BigDecimal getPayment() {
		return payment;
	}


	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}


	public BigDecimal getAdjustment() {
		return adjustment;
	}


	public void setAdjustment(BigDecimal adjustment) {
		this.adjustment = adjustment;
	}


	public BigDecimal getCurrcharges() {
		return currcharges;
	}


	public void setCurrcharges(BigDecimal currcharges) {
		this.currcharges = currcharges;
	}


	public BigDecimal getTotalamt() {
		return totalamt;
	}


	public void setTotalamt(BigDecimal totalamt) {
		this.totalamt = totalamt;
	}


	public Instant getBilldate() {
		return billdate;
	}


	public void setBilldate(Instant billdate) {
		this.billdate = billdate;
	}


	public Instant getDuedate() {
		return duedate;
	}


	public void setDuedate(Instant duedate) {
		this.duedate = duedate;
	}


	public Owner getOwner() {
		return owner;
	}


	public void setOwner(Owner owner) {
		this.owner = owner;
	}


	@Override
	public String toString() {
		return "Bill [id=" + id + ", billamount=" + billamount + ", prevbillamt=" + prevbillamt + ", payment=" + payment
				+ ", adjustment=" + adjustment + ", currcharges=" + currcharges + ", totalamt=" + totalamt
				+ ", billdate=" + billdate + ", duedate=" + duedate + ", owner=" + owner + ", getId()=" + getId()
				+ ", getBillamount()=" + getBillamount() + ", getPrevbillamt()=" + getPrevbillamt() + ", getPayment()="
				+ getPayment() + ", getAdjustment()=" + getAdjustment() + ", getCurrcharges()=" + getCurrcharges()
				+ ", getTotalamt()=" + getTotalamt() + ", getBilldate()=" + getBilldate() + ", getDuedate()="
				+ getDuedate() + ", getOwner()=" + getOwner() + "]";
	}

	


}





