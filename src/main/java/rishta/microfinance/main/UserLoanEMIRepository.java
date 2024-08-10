package rishta.microfinance.main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLoanEMIRepository extends JpaRepository<LoanEMIUser, Long>{
	
	@Query(nativeQuery = true,
		       value ="SELECT * FROM Loan_EMI WHERE user_id=?1 ORDER BY emi_payment_date DESC LIMIT 1")
	public LoanEMIUser getLastPaidEMIUser(String  userId);
	
	@Query("SELECT u FROM LoanEMIUser u WHERE u.customerId=?1")
	public List<LoanEMIUser> getAllUsersPaidEMI(String  userId);
	
	@Query("SELECT SUM(u.emiAmount) FROM LoanEMIUser u")
	public Long getAllPaidEMIAmounts();
	
	
	@Query(nativeQuery = true,
		       value ="SELECT * FROM Loan_EMI WHERE user_id=?1 AND DATE(next_emi_date) <= CURDATE() ORDER BY next_emi_date DESC LIMIT 1")
	public LoanEMIUser checkEMIDate(String  userId);
	
}
