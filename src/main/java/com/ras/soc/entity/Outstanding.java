package com.ras.soc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Outstanding {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer bill_id;
	private Float billamount;
	private Float totalpayment;
	private Float totalamt;

	@OneToOne
	@JoinColumn
	@JsonIgnore
	private Owner owner;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBill_id() {
		return bill_id;
	}

	public void setBill_id(Integer bill_id) {
		this.bill_id = bill_id;
	}

	public Float getBillamount() {
		return billamount;
	}

	public void setBillamount(Float billamount) {
		this.billamount = billamount;
	}

	public Float getTotalpayment() {
		return totalpayment;
	}

	public void setTotalpayment(Float totalpayment) {
		this.totalpayment = totalpayment;
	}

	public Float getTotalamt() {
		return totalamt;
	}

	public void setTotalamt(Float totalamt) {
		this.totalamt = totalamt;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Outstanding [id=" + id + ", bill_id=" + bill_id + ", billamount=" + billamount + ", totalpayment="
				+ totalpayment + ", totalamt=" + totalamt + ", owner=" + owner + "]";
	}

		
}
