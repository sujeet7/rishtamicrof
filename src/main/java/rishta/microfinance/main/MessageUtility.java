package rishta.microfinance.main;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class MessageUtility {
	
    private static final Map<String, String> otpStorage = new ConcurrentHashMap<>();
   
	
	public static void sendMessage(String mobileNumber,User user,String ACCOUNT_SID,String AUTH_TOKEN,String FROM_NUMBER) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		mobileNumber = "+91"+mobileNumber;
		 String body = "Dear "+user.getFirstName()+" "+user.getLastName()+",\r\n"
		    		+ "\r\n"
		    		+ "Welcome to Rishita Microfinanace.\r\n"
		    		+ "We are pleased to inform you that your loan amount of "+user.getLoanAmount()+" Rs. has been successfully credited to you, ending for Aadhar card "+user.getAdharNumber()+". \r\n"
		    		+ "\r\n"
		    		+ "Thank you for choosing Rishita Microfinace.\r\n"
		    		+ "\r\n"
		    		+ "Best regards,\r\n"
		    		+ "[Rishita Microfinace]";
		  Message message = Message.creator(
	                new PhoneNumber(mobileNumber),  // To number
	                new PhoneNumber(FROM_NUMBER),  // From number
	                body
	        ).create();
		
		  System.out.println(message.getSid());
	}
	public static void sendMessageForSaving(String mobileNumber,SavingCustomerEntity user,String ACCOUNT_SID,String AUTH_TOKEN,String FROM_NUMBER) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		mobileNumber = "+91"+mobileNumber;
		 String body = "Dear "+user.getFirstName()+" "+user.getLastName()+",\r\n"
		    		+ "\r\n"
		    		+ "Welcome to Rishita Microfinanace.\r\n"
		    		+ "We are pleased to inform you that You have opend "+ user.getSavingType() +" account for amount "+user.getSavingAmount()+" Rs. has been successfully processed to my company, ending for Aadhar card "+user.getAdharNumber()+". \r\n"
		    		+ "\r\n"
		    		+ "Thank you for choosing Rishita Microfinace.\r\n"
		    		+ "\r\n"
		    		+ "Best regards,\r\n"
		    		+ "[Rishita Microfinace]";
		  Message message = Message.creator(
	                new PhoneNumber(mobileNumber),  // To number
	                new PhoneNumber(FROM_NUMBER),  // From number
	                body
	        ).create();
		
		  System.out.println(message.getSid());
	}
	
	
	public static void sendOTPMessage(String mobileNumber,String otp,String ACCOUNT_SID,String AUTH_TOKEN,String FROM_NUMBER) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		mobileNumber = "+91"+mobileNumber;
		 String body = "Dear Customer\r\n"
		    		+ "This otp is only for loan verifiaction, your otp is: "+otp+" \r\n"
		    		+ "Do not share your otp with unknown.\r\n"
		    		+ "\r\n"
		    		+ "Best regards,\r\n"
		    		+ "[Rishita Microfinace]";
		  Message message = Message.creator(
	                new PhoneNumber(mobileNumber),  // To number
	                new PhoneNumber(FROM_NUMBER),  // From number
	                body
	        ).create();
		
		  System.out.println(message.getSid());
	}
	
	public static void sendOTP(String to, String from, String otp,String ACCOUNT_SID,String AUTH_TOKEN) {
		
		 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                "Your OTP code is: " + otp
        ).create();

        System.out.println("SMS sent successfully. SID: " + message.getSid());
		
	}
	
	 public static String generateOTP() {
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);
	        return String.valueOf(otp);
	    }
	 
	 public static boolean verifyOtp(String customerOtp,String generatedOtp ) {

	        if (customerOtp.equals(generatedOtp)) {
	           return true;
	        } else {
	        	 return false;
	        }
	    }

	public static void storeOtp(String mobileNumber, String otp) {
		otpStorage.put(mobileNumber, otp);
		
	}
	
	public static String getOtp(String mobileNumber) {
		return otpStorage.get(mobileNumber);
		
	}

}
