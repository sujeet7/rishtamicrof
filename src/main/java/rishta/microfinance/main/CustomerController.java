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
import org.springframework.transaction.annotation.Transactional;
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
		Long todayCollection = userEmiRepo.getTotalTodayAmount();
		Long weeklyCollection = userEmiRepo.getTotalWeekAmount();
		Long monthlyCollection = userEmiRepo.getTotalMontAmount();
		Long totalSum = userEmiRepo.getTotalSumAmount();
		model.addAttribute("totalRecieveAmount", totalRecieveAmount);
		model.addAttribute("totalDesburseAmount", totalDesburseAmount);
		model.addAttribute("totalInterestAmount", totalInterestAmount);
		model.addAttribute("todayCollection", todayCollection);
		model.addAttribute("weeklyCollection", weeklyCollection);
		model.addAttribute("monthlyCollection", monthlyCollection);
		model.addAttribute("totalSum", totalSum);
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
				interestAmount = (user.getSavingAmount()*Long.valueOf(user.getInterestRate()))/100;
				System.out.println("RRRRRRRRRRRRRRRRRR"+interestAmount);
				totalAmoutToPay = user.getSavingAmount();
				if(user.getSavingDuration().equals("sixmonths")) {
					interestAmount = interestAmount/2;
					totalAmoutToPay = totalAmoutToPay+interestAmount;
				}
				else if(user.getSavingDuration().equals("year")) {
					totalAmoutToPay = totalAmoutToPay+interestAmount;
				}
				else {
					interestAmount = interestAmount*5;
					totalAmoutToPay = totalAmoutToPay+interestAmount;
				}
				user.setTotalAmountToPay(totalAmoutToPay);
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
		String userId="RMFC0";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getFirstName() + "@1234");
		user.setPassword(encodedPassword);
		user.setRoleId(1);
		Long id =userRepo.getMaxId()+1;
		userId = userId+id;
		user.setUserId(userId);
		
		try {
			userExit = userRepo.findByEmail(user.getEmail());
			if (userExit!= null) {
				System.out.println(user.getFirstName() + "*******" + user.getLastName() + "" + userExit.getId());
				userRepo.updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getMobileNumber(),
						user.getAddress(), user.getDob(), user.getGender(), user.getLoanAmount(), user.getLoanType(),
						user.getLoanDuration(),user.getLoanPaymentType(),user.getEmiAmount(),user.getTotalAmountToPay(),
						user.getInterestRate(),user.getAdharNumber(),user.getRegistrationDate(), userExit.getId());
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
			System.out.println(e);
			msg = "Error while saving data may be email or mobile number is duplicate.....";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("user", new User());
		return "user-registration";
	}

	@PostMapping("/payEmiForUser")
	public String payEmi(LoanEMIUser lonaEmiObj,Model model, HttpSession httpSession) {
		
		String firstName = (String) httpSession.getAttribute("firstName");
		String emiAmount = (String) httpSession.getAttribute("emiAmount");
		String userId = (String) httpSession.getAttribute("id");
		String loanAmount = (String) httpSession.getAttribute("loanAmount");
		String loanPaymentType = (String) httpSession.getAttribute("loanPaymentType");
		String loanDuration = (String) httpSession.getAttribute("loanDuration");
		
		LoanEMIUser emiUser = new LoanEMIUser();
		LoanEMIUser emiUserObj = userEmiRepo.getLastPaidEMIUser(userId);
		
		emiUser.setCustomerId(userId);
		emiUser.setFirstName(firstName);
		emiUser.setLastName(" ");
		emiUser.setLoanAmount(Double.parseDouble(loanAmount));
		if(lonaEmiObj.getEmiAmount().equals(Double.parseDouble(emiAmount))) {
		emiUser.setEmiAmount(Double.parseDouble(emiAmount));
		}else {
			emiUser.setEmiAmount(lonaEmiObj.getEmiAmount());
		}
		emiUser.setEmiPaymentDate(lonaEmiObj.getEmiPaymentDate());
		emiUser.setLoanDuration(loanDuration);
		emiUser.setNextEmiDate(lonaEmiObj.getNextEmiDate());
		
		if(emiUserObj==null) {
		emiUser.setTotalPaidEmi("1");
		emiUser.setLeftEmiDuration(String.valueOf(Integer.valueOf(loanDuration)-1));
		emiUser.setTotalDueEmi(String.valueOf(Double.parseDouble(loanAmount)-Double.parseDouble(emiAmount)));
		}else {
			emiUser.setLeftEmiDuration(String.valueOf(Integer.valueOf(emiUserObj.getLeftEmiDuration())-1));
			emiUser.setTotalPaidEmi(String.valueOf((Integer.valueOf(emiUserObj.getTotalPaidEmi())+1)));
			emiUser.setTotalDueEmi(String.valueOf(Double.parseDouble(emiUserObj.getTotalDueEmi())-lonaEmiObj.getEmiAmount()));
			emiUser.setTotalPaidEmi(String.valueOf((Integer.valueOf(emiUserObj.getTotalPaidEmi())+1)));
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
		model.addAttribute("LoanEMIUser", new LoanEMIUser());
		return "loan_emi_paymnet";
	}

	@GetMapping("/payEmi")
	public String payEmiforUser(@RequestParam("firstName") String firstName, @RequestParam("id") String id,
			@RequestParam("loanAmount") String loanAmount, @RequestParam("loanPaymentType") String loanPaymentType,
			@RequestParam("loanDuration") String loanDuration,
			@RequestParam("emiAmount") String emiAmount, Model model, HttpSession httpSession) {
		httpSession.setAttribute("firstName", firstName);
		httpSession.setAttribute("emiAmount", emiAmount);
		httpSession.setAttribute("id", id);
		httpSession.setAttribute("loanAmount", loanAmount);
		httpSession.setAttribute("loanPaymentType", loanPaymentType);
		httpSession.setAttribute("loanPaymentType", loanPaymentType);
		httpSession.setAttribute("loanDuration", loanDuration);
		model.addAttribute("LoanEMIUser", new LoanEMIUser());
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
			for (User user : listUsers) {
				LoanEMIUser emiUserObj = userEmiRepo.getLastPaidEMIUser(user.getUserId());
				if(emiUserObj!=null) {
				user.setLastTransaction(emiUserObj.getEmiPaymentDate());
				}
			}
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
	public String deleteUser(Model model, @RequestParam("id") String id, @RequestParam("userId") String userId) {
		System.out.println("id : " + userId);
		try {
			userRepo.deleteById(Long.valueOf(id));
			userEmiRepo.deleteUserById(userId);
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
