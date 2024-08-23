package rishta.microfinance.main;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.ImageLoader;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class Utility {
	
	 public static Date getNextMonth(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);

	        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
	            calendar.set(Calendar.MONTH, Calendar.JANUARY);
	            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
	        } else {
	            calendar.roll(Calendar.MONTH, true);
	        }

	        return calendar.getTime();
	    }
	 
	 public static Date getNextDay(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, 1);
	        date = calendar.getTime();
	        
	        return date;
	        
	 }  
	 
	 public static String formateDate() {
		 LocalDateTime currentDateTime = LocalDateTime.now();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 return formatter.format(currentDateTime);
	 }
	 
	 
	 public static Date getNextWeek(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, 7);
	        date = calendar.getTime();
	        
	        return date;
	        
	 }   
	 
	     
	     
	    public static void export(HttpServletResponse response,User user) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.RED);
	        Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font1.setSize(8);
	        Font font2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font2.setSize(14);
	        font2.setColor(Color.PINK);
	        Paragraph p = new Paragraph("Personal Loan Agreement", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(p);
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
	        Paragraph p1 = new Paragraph("Lender Information: ",font2);
	        document.add(p1);
	        Paragraph p2 = new Paragraph("● Name: 			Rishta Microfinance",font1);
	        document.add(p2);
	        Paragraph p3 = new Paragraph("● Address: 			Nadwasarai,Mau(U.P) Pin: 27302",font1);
	        document.add(p3);
	        Paragraph p4 = new Paragraph("● Phone Number: 			8826119350",font1);
	        document.add(p4);
	        Paragraph p5 = new Paragraph("● Email: 			Rishtamicrofinance@gmail.com",font1);
	        document.add(p5);
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
	        Paragraph p6 = new Paragraph("Borrower Information: ",font2);
	        document.add(p6);
	        Paragraph p7 = new Paragraph("● Name: 			"+user.getFirstName()+" "+user.getLastName()+" ",font1);
	        document.add(p7);
	        Paragraph p8 = new Paragraph("● Address: 			"+user.getAddress()+"",font1);
	        document.add(p8);
	        Paragraph p9 = new Paragraph("● Phone Number: 			"+user.getMobileNumber()+"",font1);
	        document.add(p9);
	        Paragraph p10 = new Paragraph("● Adhar Number: 			"+user.getAdharNumber()+"",font1);
	        document.add(p10);
	        Paragraph p15 = new Paragraph("● Email: 			"+user.getEmail()+"",font1);
	        document.add(p15);
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
	        
	        Paragraph p11 = new Paragraph("Loan Details : ",font2);
	        document.add(p11);
	        Paragraph p12 = new Paragraph("● Loan Amount : 			"+user.getLoanAmount()+"",font1);
	        document.add(p12);
	        Paragraph p25 = new Paragraph("● Total Loan Amount With Intrest : 			"+user.getTotalAmountToPay()+"",font1);
	        document.add(p25);
	        Paragraph p13 = new Paragraph("● Loan Date : 			"+user.getRegistrationDate()+"",font1);
	        document.add(p13);
	        Paragraph p14 = new Paragraph("● Loan EMI Amount : 			"+user.getEmiAmount()+"",font1);
	        document.add(p14);
	        Paragraph p23 = new Paragraph("● Loan Duration : 			"+user.getLoanDuration()+"",font1);
	        document.add(p23);
	        Paragraph p24 = new Paragraph("● Loan Type : 			"+user.getLoanType()+"",font1);
	        document.add(p24);
	        
	       
	        
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
	        
	        Paragraph p16 = new Paragraph("Additional Terms and Condtion: ",font2);
	        document.add(p16);
	        Paragraph p17 = new Paragraph("The Lender agrees to lend to the Borrower and the Borrower agrees to borrow from the Lender for the purposes specified in Article 2 hereof and  on the terms and conditions contained ",font1);
	        document.add(p17);
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
	        
	        Paragraph p18 = new Paragraph("Signature:",font2);
	        document.add(p18);
	        Paragraph p19 = new Paragraph("Lender's Signature: ______________________________________ Date: ______________________________",font1);
	        document.add(p19);
	        Paragraph p20 = new Paragraph("Borrower's Signature: _____________________________________ Date: ______________________________",font1);
	        document.add(p20);
	        Paragraph granter = new Paragraph("Granter's Signature: _____________________________________ Date: ______________________________",font1);
	        document.add(granter);
	         
	        document.close();
	         
	    }
	    
	    
	    private static void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.PINK);
	        cell.setPadding(5);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.BLACK);
	        font.setSize(8); 
	        cell.setPhrase(new Phrase("User ID", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("First Name", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Last Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Loan Amount", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Loan Duration", font));
	        table.addCell(cell); 
	        
	        cell.setPhrase(new Phrase("Next EMI Date", font));
	        table.addCell(cell); 
	        
	        cell.setPhrase(new Phrase("Total EMI Paid", font));
	        table.addCell(cell); 
	        
	        cell.setPhrase(new Phrase("Total EMI Left", font));
	        table.addCell(cell); 
	        
	        cell.setPhrase(new Phrase("Total Left EMI Duration", font));
	        table.addCell(cell); 
	        
	        cell.setPhrase(new Phrase("EMI Amount", font));
	        table.addCell(cell); 
	    }
	     
	    private static void writeTableData(PdfPTable table,List<LoanEMIUser> listUsers) {
	        for (LoanEMIUser user : listUsers) {
	            table.addCell(user.getCustomerId());
	            table.addCell(user.getFirstName());
	            table.addCell(user.getLastName());
	            table.addCell(String.valueOf(user.getLoanAmount()));
	            table.addCell(user.getLoanDuration());
	            table.addCell(String.valueOf(user.getNextEmiDate()).replace("00:00:00.0",""));
	            table.addCell(user.getTotalPaidEmi());
	            table.addCell(user.getTotalDueEmi());
	            table.addCell(user.getLeftEmiDuration());
	            table.addCell(String.valueOf(user.getEmiAmount()));
	        }
	    }
	     
	    public static void generateEMIReport(HttpServletResponse response,List<LoanEMIUser> listUsers) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.RED);
	         
	        Paragraph p = new Paragraph("User EMI Details", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(10);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {4.0f, 3.5f, 3.0f, 3.0f, 3.5f,4.0f, 2.0f, 3.0f, 3.0f, 3.5f});
	        table.setSpacingBefore(10);
	        writeTableHeader(table);
	        writeTableData(table,listUsers);
	         
	        document.add(table);
	         
	        document.close();
	         
	    }

}
