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
	
	@Query("SELECT COUNT(*) FROM User u")
	public Long getTotalNumberOfUser();
	
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
	
}
