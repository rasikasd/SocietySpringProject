package com.ras.soc.entity;

import java.time.Instant;
import java.util.Date;
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
	private Integer	id;
	
	private Float paidamt;
	
	private Date rcptdate;
	
	private String paymenttype;
	
	private String paymentdetail;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	@JsonIgnore
	private Owner owner;

	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Bill bill;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getPaidamt() {
		return paidamt;
	}

	public void setPaidamt(Float paidamt) {
		this.paidamt = paidamt;
	}

	public Date getRcptdate() {
		return rcptdate;
	}

	public void setRcptdate(Date rcptdate) {
		this.rcptdate = rcptdate;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getPaymentdetail() {
		return paymentdetail;
	}

	public void setPaymentdetail(String paymentdetail) {
		this.paymentdetail = paymentdetail;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", paidamt=" + paidamt + ", rcptdate=" + rcptdate + ", paymenttype=" + paymenttype
				+ ", paymentdetail=" + paymentdetail + ", owner=" + owner + ", bill=" + bill + "]";
	}

	
	
	
}
