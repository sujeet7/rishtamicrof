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
		User user = userRepo.findByEmail(email);
		model.addAttribute("user", user);
		if(user.getRoleId()==1) {
			return "user-profile";
		}
		if (user != null) {
			return "dashboard";
		} else {
			return "login";
		}
	}
	

}
