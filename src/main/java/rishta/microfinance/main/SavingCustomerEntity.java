package rishta.microfinance.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Saving_Customer_Details")
public class SavingCustomerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", nullable = false, length = 20)
	private String userId;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 64)
	private String password;

	@Column(name = "first_name", nullable = false, length = 20)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 20)
	private String lastName;
	@Column(name = "mobile_number", nullable = false, length = 20)
	private String mobileNumber;
	@Column(name = "address", nullable = false, length = 200)
	private String address;
	@Column(name = "dob", nullable = false, length = 20)
	private String dob;
	@Column(name = "gender", nullable = false, length = 20)
	private String gender;
	@Column(name = "saving_duration", nullable = false, length = 20)
	private String savingDuration;
	@Column(name = "saving_amount", nullable = false, length = 20)
	private Long savingAmount;
	@Column(name = "total_amount_to_pay", nullable = false, length = 20)
	private Long totalAmountToPay;
	@Column(name = "saving_type", length = 20)
	private String savingType;
	@Column(name = "registration_date", nullable = false, length = 20)
	private Date registrationDate;
	@Column(name = "role_id", nullable = false, length = 20)
	private int roleId;
	@Column(name = "adhar_number", nullable = false, length = 20)
	private String adharNumber;
	@Column(name = "interest_rate", nullable = false, length = 20)
	private Long interestRate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSavingDuration() {
		return savingDuration;
	}
	public void setSavingDuration(String savingDuration) {
		this.savingDuration = savingDuration;
	}
	public Long getSavingAmount() {
		return savingAmount;
	}
	public void setSavingAmount(Long savingAmount) {
		this.savingAmount = savingAmount;
	}
	public Long getTotalAmountToPay() {
		return totalAmountToPay;
	}
	public void setTotalAmountToPay(Long totalAmountToPay) {
		this.totalAmountToPay = totalAmountToPay;
	}
	public String getSavingType() {
		return savingType;
	}
	public void setSavingType(String savingType) {
		this.savingType = savingType;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getAdharNumber() {
		return adharNumber;
	}
	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}
	public Long getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Long interestRate) {
		this.interestRate = interestRate;
	}
	
	
}
