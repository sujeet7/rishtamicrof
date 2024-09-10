package rishta.microfinance.main;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserLoanEMIRepository extends JpaRepository<LoanEMIUser, Long>{
	
	@Query(nativeQuery = true,
		       value ="SELECT * FROM rishtadb.loan_emi WHERE total_emi_paid = (SELECT MAX(total_emi_paid) FROM rishtadb.loan_emi where user_id=?1 ) AND user_id=?1 ")
	public LoanEMIUser getLastPaidEMIUser(String  userId);
	
	@Query("SELECT u FROM LoanEMIUser u WHERE u.customerId=?1")
	public List<LoanEMIUser> getAllUsersPaidEMI(String  userId);
	
	@Query("SELECT MAX(u.emiPaymentDate) FROM LoanEMIUser u WHERE u.customerId=?1")
	public Date getMaxDate(String  userId);
	
	@Query("SELECT u FROM LoanEMIUser u WHERE u.id=?1")
	public LoanEMIUser getLoanEMIUser(Long  userId);
	
	@Query("SELECT SUM(u.emiAmount) FROM LoanEMIUser u")
	public Long getAllPaidEMIAmounts();
	
	@Query("SELECT SUM(u.emiAmount) FROM LoanEMIUser u WHERE u.customerId LIKE 'RMFSA0%'")
	public Long getAllPaidEMIAmountsForSaving();
	
	@Query("SELECT MAX(u.totalPaidEmi) FROM LoanEMIUser u WHERE u.customerId=?1")
	public Long getTotalPaidEMI(String  userId);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM LoanEMIUser u WHERE u.customerId=?1")
	public void deleteById(String id);
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM rishtadb.loan_emi WHERE DATE(emi_payment_date) = CURDATE()")
	public Long getTotalTodayAmount();
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM rishtadb.loan_emi WHERE YEARWEEK(emi_payment_date, 1) = YEARWEEK(CURDATE(), 1)")
	public Long getTotalWeekAmount();
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM rishtadb.loan_emi WHERE YEAR(emi_payment_date) = YEAR(CURDATE()) AND MONTH(emi_payment_date) = MONTH(CURDATE())")
	public Long getTotalMontAmount();
	
	@Query(nativeQuery = true,value ="SELECT SUM(emi_amount) FROM rishtadb.loan_emi")
	public Long getTotalSumAmount();
	
	@Query(nativeQuery = true,
		       value ="SELECT * FROM rishtadb.loan_emi WHERE total_emi_paid = (SELECT MAX(total_emi_paid) FROM rishtadb.loan_emi) AND DATE(next_emi_date) <= CURDATE() AND user_id=?1")
	public LoanEMIUser checkEMIDate(String  userId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update LoanEMIUser set leftEmiDuration =:leftEmiDuration, "
			+ "firstName =:firstName,"
			+ "loanAmount =:loanAmount,"
			+ "loanDuration =:loanDuration,"
			+ "emiAmount =:emiAmount,"
			+ "emiPaymentDate =:emiPaymentDate,"
			+ "nextEmiDate =:nextEmiDate,"
			+ "totalPaidEmi =:totalPaidEmi,"
			+ "totalDueEmi =:totalDueEmi"
			+ " where id =:id")
public void updateEMIHistory(
		@Param("firstName") String firstName,
		@Param("loanAmount") Double loanAmount,
		@Param("loanDuration") String loanDuration,
		@Param("emiAmount") Double emiAmount,
		@Param("emiPaymentDate") Date emiPaymentDate,
		@Param("nextEmiDate") Date nextEmiDate,
		@Param("totalPaidEmi") String totalPaidEmi,
		@Param("totalDueEmi") String totalDueEmi,
		@Param("leftEmiDuration") String leftEmiDuration,
		@Param("id") Long id
		);
	
	
}
