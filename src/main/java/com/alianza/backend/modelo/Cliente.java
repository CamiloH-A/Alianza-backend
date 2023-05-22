package com.alianza.backend.modelo;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "shared_key", length = 50, nullable = false)
	private String sharedKey;

	@Column(name = "bussines_id", length = 50, nullable = false)
	private String bussinesId;

	@Column(name = "email", length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(name = "phone", length = 11, nullable = false)
	private String phone;

	@Column(name = "date_added", nullable = false)
	private Date dateAdded;

	public Cliente() {

	}

	public Cliente(Long id, String sharedKey, String bussinesId, String email, String phone, Date dateAdded) {
		super();
		this.id = id;
		this.sharedKey = sharedKey;
		this.bussinesId = bussinesId;
		this.email = email;
		this.phone = phone;
		this.dateAdded = dateAdded;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSharedKey() {
		return sharedKey;
	}

	public void setSharedKey(String sharedKey) {
		this.sharedKey = sharedKey;
	}

	public String getBussinesId() {
		return bussinesId;
	}

	public void setBussinesId(String bussinesId) {
		this.bussinesId = bussinesId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	

}
