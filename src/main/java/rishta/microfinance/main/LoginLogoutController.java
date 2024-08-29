package rishta.microfinance.main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginLogoutController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@GetMapping("/CustomLogin")
	public String checkLogin(@RequestParam("email") String email, Model model) {
		try {
			
		User user = userRepo.findByEmail(email);
		model.addAttribute("user", user);
		if(user==null) {
			model.addAttribute("user", new User());
			model.addAttribute("error", "Invalid username or password please try again.");
			return "login";
		}
		if(user.getRoleId()==1) {
			return "user-profile";
		}
		if (user != null) {
			return "dashboard";
		} 
		}catch(Exception e) {
			model.addAttribute("error", "Somthing went wrong.");
			return "login";
		}
		return "login";
		
	}
	
	

}
