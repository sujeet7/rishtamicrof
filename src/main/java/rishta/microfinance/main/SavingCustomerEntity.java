package rishta.microfinance.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Saving_Customer_Details")
public class SavingCustomerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private String userId;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 64)
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Column(name = "address")
	private String address;
	@Column(name = "dob")
	private String dob;
	@Column(name = "gender")
	private String gender;
	@Column(name = "loan_payment_type")
	private String loanPaymentType;
	@Column(name = "saving_duration")
	private Long savingDuration;
	@Column(name = "saving_amount")
	private Long savingAmount;
	@Column(name = "total_amount_to_pay")
	private Double totalAmountToPay;
	@Column(name = "saving_type")
	private String savingType;
	@Column(name = "registration_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registrationDate;
	@Column(name = "role_id")
	private int roleId;
	@Column(name = "adhar_number")
	private String adharNumber;
	@Column(name = "interest_rate")
	private Long interestRate;
	@Column(name = "emi_Amount", nullable = false, length = 20)
	private Double savingEMIAmount;
	@Transient
	private Date lastTransaction;
	@Column(name = "relation")
	private String relation;
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
	public Long getSavingDuration() {
		return savingDuration;
	}
	public void setSavingDuration(Long savingDuration) {
		this.savingDuration = savingDuration;
	}
	public Long getSavingAmount() {
		return savingAmount;
	}
	public void setSavingAmount(Long savingAmount) {
		this.savingAmount = savingAmount;
	}
	public Double getTotalAmountToPay() {
		return totalAmountToPay;
	}
	public void setTotalAmountToPay(Double totalAmountToPay) {
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
	public Double getSavingEMIAmount() {
		return savingEMIAmount;
	}
	public void setSavingEMIAmount(Double savingEMIAmount) {
		this.savingEMIAmount = savingEMIAmount;
	}
	public String getLoanPaymentType() {
		return loanPaymentType;
	}
	public void setLoanPaymentType(String loanPaymentType) {
		this.loanPaymentType = loanPaymentType;
	}
	public Date getLastTransaction() {
		return lastTransaction;
	}
	public void setLastTransaction(Date lastTransaction) {
		this.lastTransaction = lastTransaction;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	
}
