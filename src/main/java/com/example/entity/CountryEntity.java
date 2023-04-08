package com.example.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class CountryEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
    @Column(columnDefinition = "NVARCHAR(255)")
	private String name;
    
    @OneToMany(
    	fetch = FetchType.LAZY,
        mappedBy = "country",
        cascade = CascadeType.ALL
    )
    List<CityEntity> city;

    @OneToMany(
        mappedBy = "country",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    List<UserEntity> user;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityEntity> getCity() {
		return city;
	}

	public void setCity(List<CityEntity> city) {
		this.city = city;
	}

	public Integer getId() {
		return id;
	}

	public List<UserEntity> getUser() {
		return user;
	}

	public void setUser(List<UserEntity> user) {
		this.user = user;
	}
    
} 
