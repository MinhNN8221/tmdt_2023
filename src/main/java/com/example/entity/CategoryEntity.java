package com.example.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;
    
    @OneToMany(
    	mappedBy = "category",
    	cascade = CascadeType.ALL
    )
    @JsonIgnore
    List<ProductEntity> proList;
    
    public CategoryEntity() {}
    
    public CategoryEntity(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductEntity> getProList() {
		return proList;
	}

	public void setProList(List<ProductEntity> proList) {
		this.proList = proList;
	}
    
}
