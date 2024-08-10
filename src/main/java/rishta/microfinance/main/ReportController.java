package rishta.microfinance.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;

@Controller
public class ReportController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserLoanEMIRepository userEmiRepo;
	
	@Autowired
	private SavingCustomerRepository savingCustomerRepository;
	
	 @Value("${spring.microfinance.interestrate}")
	    private String interestRate;
	 
		@GetMapping("/exportPdfForUser")
		public void exportToPDF(@RequestParam("id") String userId, HttpServletResponse response)
				throws DocumentException, IOException {
			response.setContentType("application/pdf");

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=Customer_" + userId + ".pdf";
			response.setHeader(headerKey, headerValue);

			User user = userRepo.getUserById(userId);

			Utility.export(response, user);

		}
		
		@GetMapping("/generateEMIReport")
		public void generateEMIReport(@RequestParam("id") String userId, HttpServletResponse response)
				throws DocumentException, IOException {
			response.setContentType("application/pdf");

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=USER-EMI_" + userId + ".pdf";
			response.setHeader(headerKey, headerValue);

			List<LoanEMIUser> user = userEmiRepo.getAllUsersPaidEMI(userId);

			Utility.generateEMIReport(response, user);

		}

}
