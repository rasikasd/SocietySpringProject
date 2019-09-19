package com.ras.soc.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Owner implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String firstname;
	private String lastname;
	private String flatno;
	
	@OneToOne(cascade =  CascadeType.ALL,
            mappedBy = "owner")
	private Outstanding outstanding;
	 
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Set<Bill> bills = new HashSet<>();
	
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Set<Receipt> receipts = new HashSet<>();

	public Owner() 
	{
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFlatno() {
		return flatno;
	}

	public void setFlatno(String flatno) {
		this.flatno = flatno;
	}

	public Set<Bill> getBills() {
		return bills;
	}

	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}
	
	
/*
	public Outstanding getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(Outstanding outstanding) {
		this.outstanding = outstanding;
	}
*/
	public Set<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Receipt> receipts) {
		this.receipts = receipts;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", flatno=" + flatno
				 + ", bills=" + bills + ", receipts=" + receipts + "]";
	}

	


	
}
