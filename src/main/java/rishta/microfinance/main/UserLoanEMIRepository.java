package rishta.microfinance.main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLoanEMIRepository extends JpaRepository<LoanEMIUser, Long>{
	
	@Query(nativeQuery = true,
		       value ="SELECT * FROM railway.loan_emi WHERE total_emi_paid = (SELECT MAX(total_emi_paid) FROM railway.loan_emi) AND user_id=?1 ")
	public LoanEMIUser getLastPaidEMIUser(String  userId);
	
	@Query("SELECT u FROM LoanEMIUser u WHERE u.customerId=?1")
	public List<LoanEMIUser> getAllUsersPaidEMI(String  userId);
	
	@Query("SELECT SUM(u.emiAmount) FROM LoanEMIUser u")
	public Long getAllPaidEMIAmounts();
	
	@Query(nativeQuery = true,value ="DELETE FROM railway.loan_emi WHERE user_id=?1")
	public Long deleteUserById(String  userId);
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM railway.loan_emi WHERE DATE(emi_payment_date) = CURDATE()")
	public Long getTotalTodayAmount();
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM railway.loan_emi WHERE YEARWEEK(emi_payment_date, 1) = YEARWEEK(CURDATE(), 1)")
	public Long getTotalWeekAmount();
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM railway.loan_emi WHERE YEAR(emi_payment_date) = YEAR(CURDATE()) AND MONTH(emi_payment_date) = MONTH(CURDATE())")
	public Long getTotalMontAmount();
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM railway.loan_emi")
	public Long getTotalSumAmount();
	
	@Query(nativeQuery = true,
		       value ="SELECT * FROM railway.loan_emi WHERE total_emi_paid = (SELECT MAX(total_emi_paid) FROM railway.loan_emi) AND DATE(next_emi_date) <= CURDATE() AND user_id=?1")
	public LoanEMIUser checkEMIDate(String  userId);
	
	
}
