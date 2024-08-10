package rishta.microfinance.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SavingCustomerRepository extends JpaRepository<SavingCustomerEntity, Long>{
	
	@Query("SELECT u FROM SavingCustomerEntity u WHERE u.email = ?1")
	public SavingCustomerEntity findByEmail(String email);
	
	@Query("SELECT MAX(u.id) FROM SavingCustomerEntity u")
	public Long getMaxId();
	
	@Query("SELECT u FROM SavingCustomerEntity u WHERE u.userId=?1")
	public SavingCustomerEntity getUserById(String  userId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update SavingCustomerEntity set email =:email, "
			+ "firstName =:firstName,"
			+ "lastName =:lastName,"
			+ "mobileNumber =:mobileNumber,"
			+ "address =:address,"
			+ "dob =:dob,"
			+ "gender =:gender,"
			+ "savingAmount =:savingAmount,"
			+ "savingType =:savingType,"
			+ "savingDuration =:savingDuration"
			+ " where id =:id")
public void updateSavingCustomer(@Param("email") String email, 
		@Param("firstName") String firstName,
		@Param("lastName") String lastName,
		@Param("mobileNumber") String mobileNumber,
		@Param("address") String address,
		@Param("dob") String dob,
		@Param("gender") String gender,
		@Param("savingAmount") Long savingAmount,
		@Param("savingType") String savingType,
		@Param("savingDuration") String savingDuration,
		@Param("id") Long id
		);

}
