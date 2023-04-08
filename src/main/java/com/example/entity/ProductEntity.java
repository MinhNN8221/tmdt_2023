package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "product")
public class ProductEntity {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	private String name;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String img;
	
	private Long price;
	
	private Integer quantity;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String extra_img1;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String extra_img2;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	
	@OneToMany(
	     mappedBy = "product",
	     cascade = CascadeType.ALL
	)
	List<CartItemEntity> carts = new ArrayList<CartItemEntity>();
	
	
	public String getExtra_img1() {
		return extra_img1;
	}


	public void setExtra_img1(String extra_img1) {
		this.extra_img1 = extra_img1;
	}


	public String getExtra_img2() {
		return extra_img2;
	}


	public void setExtra_img2(String extra_img2) {
		this.extra_img2 = extra_img2;
	}

	public ProductEntity() {}
	
	
	public ProductEntity(String name, String img, Long price, Integer quantity, Date create_time,
			CategoryEntity category) {
		this.name = name;
		this.img = img;
		this.price = price;
		this.quantity = quantity;
		this.createTime = create_time;
		this.category = category;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	@Transient
	public String getProductImagePath() {
		return (id == null || img == null) ? null : "/image/san-pham/"+id+"/"+img;
//		return (id == null || img == null) ? null : "/image/san-pham/"+img;

	}
	
	@Transient
	public String getProductExtraImagePath1() {
		return (id == null || img == null) ? null : "/image/san-pham/"+id+"/"+extra_img1;
//		return (id == null || img == null) ? null : "/image/san-pham/"+extra_img1;
	}
	
	@Transient
	public String getProductExtraImagePath2() {
		return (id == null || img == null) ? null : "/image/san-pham/"+id+"/"+extra_img2;
//		return (id == null || img == null) ? null : "/image/san-pham/"+extra_img2;
	}
	
	@Transient
	public String getPriceCurrency() {
		StringBuilder s = new StringBuilder(price.toString());
		for(int i = price.toString().length()-3; i>=0; i-=3) {
			if(i == 0) continue;
			s.insert(i, ',');
		}
		s.append("đ");
		return s.toString();
	}
}
