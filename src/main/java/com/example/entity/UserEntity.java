package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_customer")
public class UserEntity {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	@NotBlank(message = "Tên không đc trống :))")
	private String fullname;
	
	@Column(columnDefinition = "VARCHAR(255)")
	@NotBlank(message = "email không được trống")
	@Email(regexp = "^(.+)@(.+)$", message = "email không hợp lệ")
	private String email;
	
	@Column(columnDefinition = "VARCHAR(255)")
	@Size(min = 6, message = "password phải có ít nhất 6 kí tự")
	private String password;
	
	@Column(columnDefinition = "VARCHAR(255)")
	@Pattern(regexp = "^\\d{10}$", message = "số điện thoại không hợp lệ")
	@Size(min = 10, max = 11, message = "số điện thoại từ 3 đến 11 kí tự")
	private String phone;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String fax;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	@NotBlank(message = "địa chỉ không được trống")
	private String address;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	private String province;
	
	@ManyToOne
	@JoinColumn(name = "countryid")
	private CountryEntity country;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	private String city;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String token;
	@Enumerated(EnumType.STRING)
	@Column(name = "authprovider")
	private AuthProvider authProvider;
	
	private Integer status;
	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL
	)
	List<BillDetailEntity> billDetailEntityList;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
         name = "role_user",
         joinColumns =@JoinColumn(name = "user_cus_id"),
         inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<RoleEntity> roles = new ArrayList<RoleEntity>();
	
	@OneToMany(
	   mappedBy = "user",
	   cascade = CascadeType.ALL
    )
	private List<CartItemEntity> carts = new ArrayList<CartItemEntity>();

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public List<CartItemEntity> getCarts() {
		return carts;
	}

	public void setCarts(List<CartItemEntity> carts) {
		this.carts = carts;
	}

	public AuthProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthProvider authProvider) {
		this.authProvider = authProvider;
	}
	
}
