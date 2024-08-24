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
@Table(name = "users")
public class User {

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
	@Column(name = "loan_amount", nullable = false, length = 20)
	private Long loanAmount;
	@Column(name = "loan_type", nullable = false, length = 20)
	private String loanType;
	@Column(name = "loan_payment_type", nullable = false, length = 20)
	private String loanPaymentType;
	@Column(name = "emi_Amount", nullable = false, length = 20)
	private Double emiAmount;
	@Column(name = "total_amount_to_pay", nullable = false, length = 20)
	private Double totalAmountToPay;
	@Column(name = "registration_date", nullable = false, length = 20)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registrationDate;
	@Column(name = "role_id", nullable = false, length = 20)
	private int roleId;
	@Column(name = "adhar_number", nullable = false, length = 20)
	private String adharNumber;
	@Column(name = "interest_rate", nullable = false, length = 20)
	private Long interestRate;
	@Column(name = "loan_duration", nullable = false, length = 20)
	private Long loanDuration;
	@Transient
	private Date lastTransaction;
	
	@Transient
	private String otp;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}

	

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public Double getTotalAmountToPay() {
		return totalAmountToPay;
	}

	public void setTotalAmountToPay(Double totalAmountToPay) {
		this.totalAmountToPay = totalAmountToPay;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
	

	public String getLoanPaymentType() {
		return loanPaymentType;
	}

	public void setLoanPaymentType(String loanPaymentType) {
		this.loanPaymentType = loanPaymentType;
	}

	public Long getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Long interestRate) {
		this.interestRate = interestRate;
	}

	public Long getLoanDuration() {
		return loanDuration;
	}

	public void setLoanDuration(Long loanDuration) {
		this.loanDuration = loanDuration;
	}

	public Date getLastTransaction() {
		return lastTransaction;
	}

	public void setLastTransaction(Date lastTransaction) {
		this.lastTransaction = lastTransaction;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", email=" + email + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", mobileNumber=" + mobileNumber + ", address=" + address
				+ ", dob=" + dob + ", gender=" + gender + ", loanAmount=" + loanAmount + ", loanType=" + loanType
				+ ", loanDuration=" + loanDuration + ", emiAmount=" + emiAmount + ", totalAmountToPay="
				+ totalAmountToPay + "]";
	}


}
