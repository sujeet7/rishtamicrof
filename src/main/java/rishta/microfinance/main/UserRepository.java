package rishta.microfinance.main;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);
	
	@Query("SELECT MAX(u.id) FROM User u")
	public Long getMaxId();

	@Query("SELECT u FROM User u WHERE u.userId=?1")
	public User getUserById(String  userId);
	@Query("SELECT u FROM User u WHERE u.firstName LIKE %?1%")
	public List<User> getUserByName(String  firstName);
	
	 @Query(value = "SELECT * FROM railway.users ORDER BY registration_date DESC LIMIT 5;", nativeQuery = true)
	public List<User> getToFiveUsers();
	
	@Query("SELECT COUNT(*) FROM User u")
	public Long getTotalNumberOfUser();
	
	 @Query("SELECT u FROM User u WHERE MONTH(u.registrationDate) = :month")
	    List<User> findByMonth(int month);
	 
	 @Query("SELECT SUM(u.totalAmountToPay-u.loanAmount) FROM User u WHERE MONTH(u.registrationDate) = :month")
		public Long getAllInterstAmountMonthly(int month);
	
	public void deleteById(String id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User set email =:email, "
			+ "firstName =:firstName,"
			+ "lastName =:lastName,"
			+ "mobileNumber =:mobileNumber,"
			+ "address =:address,"
			+ "dob =:dob,"
			+ "gender =:gender,"
			+ "loanAmount =:loanAmount,"
			+ "loanType =:loanType,"
			+ "loanDuration =:loanDuration,"
			+ "loanPaymentType =:loanPaymentType,"
			+ "totalAmountToPay =:totalAmountToPay,"
			+ "interestRate =:interestRate,"
			+ "adharNumber =:adharNumber,"
			+ "registrationDate =:registrationDate,"
			+ "emiAmount =:emiAmount"
			+ " where id =:id")
public void updateUser(@Param("email") String email, 
		@Param("firstName") String firstName,
		@Param("lastName") String lastName,
		@Param("mobileNumber") String mobileNumber,
		@Param("address") String address,
		@Param("dob") String dob,
		@Param("gender") String gender,
		@Param("loanAmount") Long loanAmount,
		@Param("loanType") String loanType,
		@Param("loanDuration") Long loanDuration,
		@Param("loanPaymentType") String loanPaymentType,
		@Param("emiAmount") Double emiAmount,
		@Param("totalAmountToPay") Double totalAmountToPay,
		@Param("interestRate") Long interestRate,
		@Param("adharNumber") String adharNumber,
		@Param("registrationDate") Date registrationDate,
		@Param("id") Long id
		);
	
	@Query("SELECT SUM(u.loanAmount) FROM User u")
	public Long getAllDesbursAmount();
	
	@Query("SELECT SUM(u.totalAmountToPay-u.loanAmount) FROM User u")
	public Long getAllInterstAmount();
	
	 @Query(nativeQuery = true,value = "SELECT sum(lm.emi_amount) FROM railway.loan_emi lm INNER JOIN railway.users u ON lm.user_id = u.user_id WHERE MONTH(u.registration_date) = ?1 and u.loan_payment_type='daily'")
	 public Long getDailyByMonth(int month);
	 
	 @Query(nativeQuery = true,value = "SELECT sum(lm.emi_amount) FROM railway.loan_emi lm INNER JOIN railway.users u ON lm.user_id = u.user_id WHERE MONTH(u.registration_date) = ?1 and u.loan_payment_type='weekly'")
	 public Long getWeeklyByMonth(int month);
	 
	 @Query(nativeQuery = true,value = "SELECT sum(lm.emi_amount) FROM railway.loan_emi lm INNER JOIN railway.users u ON lm.user_id = u.user_id WHERE MONTH(u.registration_date) = ?1 and u.loan_payment_type='monthly'")
	 public Long getMonthly(int month);
}
