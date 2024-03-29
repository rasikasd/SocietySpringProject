package com.ras.soc.entity;

import java.time.Instant;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ras.soc.util.CustomerDateAndTimeDeserialize;


@Entity
public class BillGen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date billgenstart;
	private Date billgenend;
	private Integer billfreq;
	private Integer totalgenbill;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getBillgenstart() {
		return billgenstart;
	}
	
	public void setBillgenstart(Date billgenstart) {
		this.billgenstart = billgenstart;
	}
	
	public Date getBillgenend() {
		return billgenend;
	}
	
	public void setBillgenend(Date billgenend) {
		this.billgenend = billgenend;
	}
	
	public Integer getTotalgenbill() {
		return totalgenbill;
	}
	
	public void setTotalgenbill(Integer totalgenbill) {
		this.totalgenbill = totalgenbill;
	}
	
	

	public Integer getBillfreq() {
		return billfreq;
	}
	public void setBillfreq(Integer billfreq) {
		this.billfreq = billfreq;
	}
	@Override
	public String toString() {
		return "BillGen [id=" + id + ", billgenstart=" + billgenstart + ", billgenend=" + billgenend + ", billfreq="
				+ billfreq + ", totalgenbill=" + totalgenbill + "]";
	}

	
	
	
}
