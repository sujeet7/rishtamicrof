package rishta.microfinance.main;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginLogoutController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserLoanEMIRepository userEmiRepo;
	
	@Autowired
	private SavingCustomerRepository savingCustomerRepository;
	
	@GetMapping("")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@GetMapping("/updatePassword")
	public String openPasswordPage(Model model) {
		 model.addAttribute("passwordUpdateDTO", new PasswordUpdateDTO());
		return "update-password";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@ModelAttribute("passwordUpdateDTO") PasswordUpdateDTO passwordUpdateDTO, Model model) {

		User user = userRepo.findByEmail(passwordUpdateDTO.getUserId());
		System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN"+user);
		if(user!=null) {
			if (!passwordEncoder.matches(passwordUpdateDTO.getCurrentPassword(), user.getPassword())) {
				model.addAttribute("errorMessage", "Current password is incorrect.");
				return "update-password";
			}
	
			if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
				model.addAttribute("errorMessage", "New password do not match.");
				return "update-password";
			}
	
			user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
			try {
			userRepo.save(user);
			model.addAttribute("successMessage", "Password updated successfully.");
			}catch (Exception e) {
				model.addAttribute("successMessage", "Somthing Went Wrong.");
			}
		
		}else {
			model.addAttribute("errorMessage", "No user exist with this userid.");
		}

		
		return "update-password";
	}


	@GetMapping("/CustomLogin")
	public String checkLogin(@RequestParam("email") String email, Model model) {
		try {
			
		User user = userRepo.findByEmail(email);
		if(user==null) {
			model.addAttribute("user", new User());
			model.addAttribute("error", "Invalid username or password please try again.");
			return "login";
		}
		model.addAttribute("user", user);
		if(user.getRoleId()==1) {
			return "user-profile";
		}
		if (user != null) {
			Long totalRecieveAmount = userEmiRepo.getAllPaidEMIAmounts();
			Long totalDesburseAmount = userRepo.getAllDesbursAmount();
			Long totalInterestAmount = userRepo.getAllInterstAmount();
			Long todayCollection = userEmiRepo.getTotalTodayAmount();
			Long weeklyCollection = userEmiRepo.getTotalWeekAmount();
			Long monthlyCollection = userEmiRepo.getTotalMontAmount();
			
			List<User> topFiveUsers = userRepo.getToFiveUsers();
			List<SavingCustomerEntity> topFiveSavingUsers = savingCustomerRepository.getToFiveUsers();
			System.out.println("HHHHHHHHHHHHHHH"+topFiveUsers);
			Long totalSum = userEmiRepo.getTotalSumAmount();
			model.addAttribute("totalRecieveAmount", totalRecieveAmount);
			model.addAttribute("totalDesburseAmount", totalDesburseAmount);
			model.addAttribute("totalInterestAmount", totalInterestAmount);
			model.addAttribute("todayCollection", todayCollection);
			model.addAttribute("weeklyCollection", weeklyCollection);
			model.addAttribute("monthlyCollection", monthlyCollection);
			model.addAttribute("totalSum", totalSum);
			model.addAttribute("topFiveUsers", topFiveUsers);
			model.addAttribute("topFiveSavingUsers", topFiveSavingUsers);
			if(totalDesburseAmount!=null && totalRecieveAmount!=null) {
				model.addAttribute("totalOutstandingAmount", totalDesburseAmount-totalRecieveAmount);
				}
			return "dashboard";
		} 
		}catch(Exception e) {
			model.addAttribute("error", "Somthing went wrong.");
			return "login";
		}
		return "login";
		
	}
	
	

}
