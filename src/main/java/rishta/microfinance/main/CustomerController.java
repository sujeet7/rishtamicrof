package rishta.microfinance.main;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

	 private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	   
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserLoanEMIRepository userEmiRepo;
	
	@Autowired
	private SavingCustomerRepository savingCustomerRepository;
	
	 @Value("${spring.microfinance.interestrate}")
	    private String interestRate;

	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		Long totalRecieveAmount = userEmiRepo.getAllPaidEMIAmounts();
		Long totalDesburseAmount = userRepo.getAllDesbursAmount();
		Long totalInterestAmount = userRepo.getAllInterstAmount();
		
		model.addAttribute("totalRecieveAmount", totalRecieveAmount);
		model.addAttribute("totalDesburseAmount", totalDesburseAmount);
		model.addAttribute("totalInterestAmount", totalInterestAmount);
		if(totalDesburseAmount!=null && totalRecieveAmount!=null) {
			model.addAttribute("totalOutstandingAmount", totalDesburseAmount-totalRecieveAmount);
			}
		model.addAttribute("user", new User());

		return "dashboard";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "user-registration";
	}
	@GetMapping("/addSavingCustomer")
	public String showSavingCustomer(Model model) {
		model.addAttribute("savingCustomer", new SavingCustomerEntity());
		return "saving-customer-registration";
	}
	
	@PostMapping("/process_saving_customer")
	public String addSavingCustomer(SavingCustomerEntity user, Model model) {
		String msg = null;
		SavingCustomerEntity userExit = null;
		Long totalAmoutToPay = null;
		Long interestAmount= null;
		String userId="RMFSA0";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getFirstName() + "@1234");
		try {
			userExit = savingCustomerRepository.findByEmail(user.getEmail());
			System.out.println("aaaaaaaaaaaaaaaa"+userExit);
			logger.info("inside addSavingCustomer");
			if (userExit!= null) {
				System.out.println(user.getFirstName() + "*******" + user.getLastName() + "" + userExit.getId());
				savingCustomerRepository.updateSavingCustomer(user.getEmail(), user.getFirstName(), user.getLastName(), user.getMobileNumber(),
						user.getAddress(), user.getDob(), user.getGender(), user.getSavingAmount(), user.getSavingType(),
						user.getSavingDuration(), userExit.getId());
				msg = "Data updated sucessfully for user id : " + userExit.getId();
				model.addAttribute("msg", msg);
				model.addAttribute("savingCustomer", user);
				return "saving-customer-registration";
			} else {
				user.setPassword(encodedPassword);
				Long id =savingCustomerRepository.getMaxId();
				if(id!=null) {
				id = id+1;
				}
				System.out.println("Id +++++"+id);
				userId = userId+id;
				user.setUserId(userId);
				if(user.getSavingAmount()!=null) {
				interestAmount = (user.getSavingAmount()*Long.valueOf(interestRate))/100;
				totalAmoutToPay = user.getSavingAmount()+interestAmount;
				user.setTotalAmountToPay(user.getSavingAmount()+interestAmount);
				user.setRoleId(1);
				user.setRegistrationDate(new Date());
				}
				savingCustomerRepository.save(user);
				msg = "Data saved sucessfully for user id : " + user.getUserId();
			}

		} catch (Exception e) {
			System.out.println(e.getCause());
			msg = "Error while saving data may be email or mobile number is duplicate.....";
			logger.info("inside addSavingCustomer"+e);
		}
		model.addAttribute("msg", msg);
		model.addAttribute("savingCustomer", new SavingCustomerEntity());
		return "saving-customer-registration";
	}

	@PostMapping("/process_register")
	public String processRegister(User user, Model model) {
		String msg = null;
		User userExit = null;
		Long totalAmoutToPay = null;
		Long interestAmount= null;
		String userId="RMFC0";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getFirstName() + "@1234");
		user.setPassword(encodedPassword);
		user.setRoleId(1);
		Long id =userRepo.getMaxId()+1;
		userId = userId+id;
		user.setUserId(userId);
		if(user.getLoanAmount()!=null) {
		interestAmount = (user.getLoanAmount()*Long.valueOf(interestRate))/100;
		totalAmoutToPay = user.getLoanAmount()+interestAmount;
		user.setTotalAmountToPay(user.getLoanAmount()+interestAmount);
		user.setRegistrationDate(new Date());
		}
		if (user.getLoanDuration().equals("daily")) {
			user.setEmiAmount(totalAmoutToPay/240);
		} else if (user.getLoanDuration().equals("weekly")) {
			user.setEmiAmount(totalAmoutToPay/(long)34.79);
		} else {
			user.setEmiAmount(totalAmoutToPay/8);
		}
		try {
			userExit = userRepo.findByEmail(user.getEmail());
			System.out.println("aaaaaaaaaaaaaaaa"+userExit);
			if (userExit!= null) {
				System.out.println(user.getFirstName() + "*******" + user.getLastName() + "" + userExit.getId());
				userRepo.updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getMobileNumber(),
						user.getAddress(), user.getDob(), user.getGender(), user.getLoanAmount(), user.getLoanType(),
						user.getLoanDuration(), userExit.getId());
				msg = "Data updated sucessfully for user id : " + userExit.getUserId();
				model.addAttribute("msg", msg);
				model.addAttribute("user", user);
				return "user-registration";
			} else {
				userRepo.save(user);
				//MessageUtility.sendMessage(user.getMobileNumber(), user);
				msg = "Data saved sucessfully for user id : " + user.getUserId();
			}

		} catch (Exception e) {
			System.out.println(e.getCause());
			msg = "Error while saving data may be email or mobile number is duplicate.....";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("user", new User());
		return "user-registration";
	}

	@PostMapping("/payEmiForUser")
	public String payEmi(Model model, HttpSession httpSession) {
		
		String firstName = (String) httpSession.getAttribute("firstName");
		String emiAmount = (String) httpSession.getAttribute("emiAmount");
		String userId = (String) httpSession.getAttribute("id");
		String loanAmount = (String) httpSession.getAttribute("loanAmount");
		String loanDuration = (String) httpSession.getAttribute("loanDuration");
		LoanEMIUser emiUser = new LoanEMIUser();
		LoanEMIUser emiUserObj = userEmiRepo.getLastPaidEMIUser(userId);
		LoanEMIUser emiDateObj = userEmiRepo.checkEMIDate(userId);
		List<LoanEMIUser> userFirstEMI = userEmiRepo.getAllUsersPaidEMI(userId);
		if(emiUserObj!=null) {
			model.addAttribute("nextEmiDate", String.valueOf(emiUserObj.getNextEmiDate()).replace("00:00:00.0", ""));
			}
		if(emiDateObj==null && userFirstEMI.size()>0) {
			return "emi-message";
		}
		
		emiUser.setCustomerId(userId);
		emiUser.setFirstName(firstName);
		emiUser.setLastName("Kumar");
		emiUser.setLoanAmount(Long.valueOf(loanAmount));
		emiUser.setEmiAmount(Long.valueOf(emiAmount));
		emiUser.setEmiPaymentDate(new Date());
		emiUser.setLoanDuration(loanDuration);
		if(emiUserObj==null) {
		emiUser.setNextEmiDate(Utility.getNextMonth(new Date()));
		emiUser.setTotalPaidEmi("1");
		if(loanDuration.equals("daily")) {
		emiUser.setNextEmiDate(Utility.getNextDay(new Date()));
		}else if(loanDuration.equals("weekly")) {
			emiUser.setNextEmiDate(Utility.getNextWeek(new Date()));
		}else {
			emiUser.setNextEmiDate(Utility.getNextMonth(new Date()));
		}
		if(loanDuration.equals("daily")) {
			emiUser.setLeftEmiDuration("239");
			}else if(loanDuration.equals("weekly")) {
				emiUser.setLeftEmiDuration("33");
			}else {
				emiUser.setLeftEmiDuration("7");
			}
		emiUser.setTotalDueEmi(String.valueOf(Long.valueOf(loanAmount)-Long.valueOf(emiAmount)));
		model.addAttribute("user", new User());
		}else {
			emiUser.setEmiPaymentDate(emiUserObj.getNextEmiDate());
			emiUser.setTotalPaidEmi(String.valueOf((Integer.valueOf(emiUserObj.getTotalPaidEmi())+1)));
			emiUser.setTotalDueEmi(String.valueOf(Long.valueOf(loanAmount)-(Long.valueOf(emiAmount)*(Long.valueOf(emiUserObj.getTotalPaidEmi())+1))));
			if (loanDuration.equals("daily")) {
				emiUser.setNextEmiDate(Utility.getNextDay(emiUserObj.getNextEmiDate()));
			} else if (loanDuration.equals("weekly")) {
				emiUser.setNextEmiDate(Utility.getNextWeek(emiUserObj.getNextEmiDate()));
			} else {
				emiUser.setNextEmiDate(Utility.getNextMonth(emiUserObj.getNextEmiDate()));
			}
			if(loanDuration.equals("daily")) {
				emiUser.setLeftEmiDuration(String.valueOf(Integer.valueOf(emiUserObj.getLeftEmiDuration())-1));
				}else if(loanDuration.equals("weekly")) {
					emiUser.setLeftEmiDuration(String.valueOf(Integer.valueOf(emiUserObj.getLeftEmiDuration())-1));
				}else {
					emiUser.setLeftEmiDuration(String.valueOf(Integer.valueOf(emiUserObj.getLeftEmiDuration())-1));
				}
			
		}
		System.out.println("HHHHHHHHH" + firstName + ":" + emiAmount + userId);
		LoanEMIUser result = null;
		try {
			result = userEmiRepo.save(emiUser);
			if (result.getId() != null) {
				model.addAttribute("msg", "Successfully paid emi");
			} else {
				model.addAttribute("msg", "Error while paying emi");
			}
		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("msg", "Error while paying emi");
		}
		return "loan_emi_paymnet";
	}

	@GetMapping("/payEmi")
	public String payEmiforUser(@RequestParam("firstName") String firstName, @RequestParam("id") String id,
			@RequestParam("loanAmount") String loanAmount, @RequestParam("loanDuration") String loanDuration,
			@RequestParam("emiAmount") String emiAmount, Model model, HttpSession httpSession) {
		httpSession.setAttribute("firstName", firstName);
		httpSession.setAttribute("emiAmount", emiAmount);
		httpSession.setAttribute("id", id);
		httpSession.setAttribute("loanAmount", loanAmount);
		httpSession.setAttribute("loanDuration", loanDuration);
		model.addAttribute("user", new User());
		System.out.println("@@@@@@@@@@@@@@@@@@@@@" + firstName + ":" + emiAmount);

		return "loan_emi_paymnet";
	}
	
	@GetMapping("/getEMIByUserId")
	public String getEMIByUserId( @RequestParam("id") String userId,
			 Model model, HttpSession httpSession) {
		List<LoanEMIUser> user = userEmiRepo.getAllUsersPaidEMI(userId);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@" + userId);
		model.addAttribute("listOfEmiUsers", user);
		return "user-emi-history";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		logger.info("inside listUsers");
		List<User> listUsers = userRepo.findAll();
		Long totalRecieveAmount = userEmiRepo.getAllPaidEMIAmounts();
		Long totalDesburseAmount = userRepo.getAllDesbursAmount();
		Long totalInterestAmount = userRepo.getAllInterstAmount();
		if(listUsers!=null && listUsers.size()>0) {
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("totalRecieveAmount", totalRecieveAmount);
		model.addAttribute("totalDesburseAmount", totalDesburseAmount);
		model.addAttribute("totalInterestAmount", totalInterestAmount);
		if(totalDesburseAmount!=null && totalRecieveAmount!=null) {
		model.addAttribute("totalOutstandingAmount", totalDesburseAmount-totalRecieveAmount);
		}
		}else {
		model.addAttribute("msg", "No user found with this id : ");
		}

		return "users";
	}
	
	@GetMapping("/savingCustomers")
	public String savingCustomers(Model model) {
		List<SavingCustomerEntity> listUsers = savingCustomerRepository.findAll();
		model.addAttribute("savingCustomers", listUsers);

		return "saving-customers";
	}
	
	@GetMapping("/editSavingCustomer")
	public String editSavingCustomer(Model model, @RequestParam("id") String id) {
		Optional<SavingCustomerEntity> user = savingCustomerRepository.findById(Long.valueOf(id));
		System.out.println("idddddddddddddd"+id);
		model.addAttribute("savingCustomer", user);

		return "saving-customer-registration";
	}

	@GetMapping("/editUser")
	public String editUser(Model model, @RequestParam("id") String id) {
		Optional<User> user = userRepo.findById(Long.valueOf(id));
		model.addAttribute("user", user);

		return "user-registration";
	}
	
	@GetMapping("/searchUserById")
	public String searchUserById(User user, Model model) {
		User userOb = null;
		SavingCustomerEntity customerOb = null;
		if (user.getUserId().startsWith("RMFC")) {
			userOb = userRepo.getUserById(user.getUserId());
		} else {
			customerOb = savingCustomerRepository.getUserById(user.getUserId());

		}
		if (userOb != null) {
			model.addAttribute("listUsers", userOb);
		} else {
			model.addAttribute("savingCustomers", customerOb);
		}
		if (user.getUserId().startsWith("RMFC")) {
			if (userOb == null) {
				model.addAttribute("msg", "No user found with this id : " + user.getUserId());
			}
			return "users";
		} else {
			if (customerOb == null) {
				model.addAttribute("msg", "No user found with this id : " + user.getUserId());
			}
			return "saving-customers";
		}
	}
	
	@GetMapping("/searchUser")
	public String search(Model model) {
		model.addAttribute("user", new User());

		return "user-popup";
	}

	@GetMapping("/deleteUser")
	public String deleteUser(Model model, @RequestParam("id") String id) {
		System.out.println("id : " + id);
		try {
			userRepo.deleteById(Long.valueOf(id));
			userEmiRepo.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "users";
	}
	
	@GetMapping("/deleteSavingCustomer")
	public String deleteSavingCustomer(Model model, @RequestParam("id") String id) {
		System.out.println("id : " + id);
		try {
			savingCustomerRepository.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "saving-customers";
	}

}
