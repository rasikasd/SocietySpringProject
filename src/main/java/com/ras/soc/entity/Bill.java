package com.ras.soc.entity;

import java.time.Instant;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Float billamount;
	private Float prevbillamt;
	private Float payment;
	private Float adjustment;
	private Float currcharges;
	private Float totalamt;
	
	private Date billdate;
	private Date duedate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	@JsonIgnore
	private Owner owner;
	
	@OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Receipt> receipts = new HashSet<>();


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Float getBillamount() {
		return billamount;
	}


	public void setBillamount(Float billamount) {
		this.billamount = billamount;
	}


	public Float getPrevbillamt() {
		return prevbillamt;
	}


	public void setPrevbillamt(Float prevbillamt) {
		this.prevbillamt = prevbillamt;
	}


	public Float getPayment() {
		return payment;
	}


	public void setPayment(Float payment) {
		this.payment = payment;
	}


	public Float getAdjustment() {
		return adjustment;
	}


	public void setAdjustment(Float adjustment) {
		this.adjustment = adjustment;
	}


	public Float getCurrcharges() {
		return currcharges;
	}


	public void setCurrcharges(Float currcharges) {
		this.currcharges = currcharges;
	}


	public Float getTotalamt() {
		return totalamt;
	}


	public void setTotalamt(Float totalamt) {
		this.totalamt = totalamt;
	}

	
	public Date getBilldate() {
		return billdate;
	}


	public void setBilldate(Date billdate) {
		this.billdate = billdate;
	}


	public Date getDuedate() {
		return duedate;
	}


	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}


	public Owner getOwner() {
		return owner;
	}


	public void setOwner(Owner owner) {
		this.owner = owner;
	}


	public Set<Receipt> getReceipts() {
		return receipts;
	}


	public void setReceipts(Set<Receipt> receipts) {
		this.receipts = receipts;
	}


	@Override
	public String toString() {
		return "Bill [id=" + id + ", billamount=" + billamount + ", prevbillamt=" + prevbillamt + ", payment=" + payment
				+ ", adjustment=" + adjustment + ", currcharges=" + currcharges + ", totalamt=" + totalamt
				+ ", billdate=" + billdate + ", duedate=" + duedate + ", owner=" + owner + ", receipts=" + receipts
				+ "]";
	}




}





