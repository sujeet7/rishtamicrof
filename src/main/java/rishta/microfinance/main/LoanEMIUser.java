package rishta.microfinance.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "loan_emi")
public class LoanEMIUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private String customerId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "loan_amount")
	private Long loanAmount;
	
	@Column(name = "loan_duration")
	private String loanDuration;
	
	@Column(name = "next_emi_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date nextEmiDate;
	
	@Column(name = "total_emi_paid")
	private String totalPaidEmi;
	
	@Column(name = "total_emi_due")
	private String totalDueEmi;
	
	@Column(name = "left_emi_duration")
	private String leftEmiDuration;
	
	@Column(name = "emi_payment_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date emiPaymentDate;
	
	@Column(name = "emi_amount")
	private Long emiAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}


	public String getLoanDuration() {
		return loanDuration;
	}

	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}

	public Long getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(Long emiAmount) {
		this.emiAmount = emiAmount;
	}

	public Date getNextEmiDate() {
		return nextEmiDate;
	}

	public void setNextEmiDate(Date nextEmiDate) {
		this.nextEmiDate = nextEmiDate;
	}

	public String getTotalPaidEmi() {
		return totalPaidEmi;
	}

	public void setTotalPaidEmi(String totalPaidEmi) {
		this.totalPaidEmi = totalPaidEmi;
	}

	public String getTotalDueEmi() {
		return totalDueEmi;
	}

	public void setTotalDueEmi(String totalDueEmi) {
		this.totalDueEmi = totalDueEmi;
	}

	public String getLeftEmiDuration() {
		return leftEmiDuration;
	}

	public void setLeftEmiDuration(String leftEmiDuration) {
		this.leftEmiDuration = leftEmiDuration;
	}

	public Date getEmiPaymentDate() {
		return emiPaymentDate;
	}

	public void setEmiPaymentDate(Date emiPaymentDate) {
		this.emiPaymentDate = emiPaymentDate;
	}


}
