package com.dz.app.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author dz Mar 17, 2023
 *
 */
@Entity
@Table(name="student")
public class Student {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long sid;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="GENDER")
    private String gender;

    @OneToMany(mappedBy="student" ,cascade=CascadeType.ALL)
    private List<Address> addresses;
    
	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Student() {
	}

	public Student(String name, String gender) {
		this.name = name;
		this.gender = gender;
	}
    
	
}	
