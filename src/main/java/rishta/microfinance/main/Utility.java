package rishta.microfinance.main;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.ImageLoader;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
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
	 
	 public static Date getDate(String dateString) {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date=null;
	        try {
	            date = formatter.parse(dateString);
	            System.out.println("Converted Date: " + date);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return date;
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
	        String paymentType="";
	        String durationType="";
	        if(user.getLoanPaymentType().equals("daily")) {
	        	paymentType = paymentType+"/ Per Day";
	        	durationType = durationType+"Days";
	        }else if(user.getLoanPaymentType().equals("weekly")) {
	        	paymentType = paymentType+"/ Per Week";
	        	durationType = durationType+"Weeks";
	        }else {
	        	paymentType = paymentType+"/ Per Month";
	        	durationType = durationType+"Months";
	        }
	       
	        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	        document.open();

	        
	        PdfContentByte canvas = writer.getDirectContentUnder();
	        String logoPath = getLogoImagePath();
	        Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(300, 300); // Adjust logo size
            logo.setAlignment(Image.ALIGN_CENTER); // Align logo in center
            document.add(logo);
            
           
            
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(22);
	        font.setColor(Color.RED);
	        Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font1.setSize(8);
	        Font font2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font2.setSize(14);
	        font2.setColor(Color.RED);
	        Paragraph p = new Paragraph("Loan Agreement Report", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(p);
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
	        Paragraph p1 = new Paragraph("Lender Information: ",font2);
	        document.add(p1);
	        Paragraph p2 = new Paragraph("● Name: 			Rishita Micro Finance",font1);
	        document.add(p2);
	        Paragraph p3 = new Paragraph("● Address: 			Nadwasarai,Dist-Mau(U.P) Pin: 275302",font1);
	        document.add(p3);
	        Paragraph p4 = new Paragraph("● Phone Number: 			9695549171",font1);
	        document.add(p4);
	        Paragraph p5 = new Paragraph("● Email: 			Rishitamicrofinance@gmail.com",font1);
	        document.add(p5);
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
	        Paragraph p6 = new Paragraph("Borrower Information: ",font2);
	        document.add(p6);
	        Paragraph p7 = new Paragraph("● Name: 			"+user.getFirstName()+" "+user.getLastName()+" ",font1);
	        document.add(p7);
	        Paragraph p0 = new Paragraph("● Relation: 			"+user.getRelation(),font1);
	        document.add(p0);
	        Paragraph p00 = new Paragraph("● Date Of Birth: 			"+user.getDob(),font1);
	        document.add(p00);
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
	        Paragraph p14 = new Paragraph("● Loan EMI Amount : 			"+user.getEmiAmount()+" "+paymentType,font1);
	        document.add(p14);
	        Paragraph p23 = new Paragraph("● Loan Duration : 			"+user.getLoanDuration()+" "+durationType,font1);
	        document.add(p23);
	        Paragraph p24 = new Paragraph("● Loan Type : 			"+user.getLoanType()+"",font1);
	        document.add(p24);
	        
	       
	        
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
	        
	        Paragraph p16 = new Paragraph("Additional Terms and Condtion: ",font2);
	        document.add(p16);
	        Paragraph p17 = new Paragraph("The Lender agrees to lend to the Borrower and the Borrower agrees to borrow from the Lender for the purposes specified in Article 2 here of and on the basis of terms and conditions legal action will be taken against the candidate .",font1);
	        document.add(p17);
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
	        
	        Paragraph p18 = new Paragraph("Signature:",font2);
	        document.add(p18);
	        Paragraph p19 = new Paragraph("Lender's Signature: ______________________________________ Date: ______________________________",font1);
	        document.add(p19);
	        document.add(Chunk.NEWLINE);
	        Paragraph p20 = new Paragraph("Borrower's Signature: _____________________________________ Date: ______________________________",font1);
	        document.add(p20);
	        document.add(Chunk.NEWLINE);
	        Paragraph granter = new Paragraph("Granter's Signature: _____________________________________ Date: ______________________________",font1);
	        document.add(granter);
	        
	        addWatermark(canvas, writer);

            // Add page border
            addPageBorder(canvas, writer);
	        document.close();
	         
	    }
	    public static void exportSavingCustomer(HttpServletResponse response,SavingCustomerEntity user) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        String paymentType="";
	        String durationType="";
	        if(user.getLoanPaymentType().equals("daily")) {
	        	paymentType = paymentType+"/ Per Day";
	        	durationType = durationType+"Days";
	        }else if(user.getLoanPaymentType().equals("weekly")) {
	        	paymentType = paymentType+"/ Per Week";
	        	durationType = durationType+"Weeks";
	        }else {
	        	paymentType = paymentType+"/ Per Month";
	        	durationType = durationType+"Months";
	        }
	        //PdfWriter.getInstance(document, response.getOutputStream());
	        
	        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	        document.open();
	        
	        PdfContentByte canvas = writer.getDirectContentUnder();
	        String logoPath = getLogoImagePath();
	        Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(300, 300); // Adjust logo size
            logo.setAlignment(Image.ALIGN_CENTER); // Align logo in center
            document.add(logo);
            
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.RED);
	        Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font1.setSize(8);
	        Font font2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font2.setSize(14);
	        font2.setColor(Color.RED);
	        Paragraph p = new Paragraph("Saving Plan Agreement", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(p);
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
	        Paragraph p1 = new Paragraph("Saver Information: ",font2);
	        document.add(p1);
	        Paragraph p2 = new Paragraph("● Name: 			Rishita Microfinance",font1);
	        document.add(p2);
	        Paragraph p3 = new Paragraph("● Address: 			Nadwasarai,Mau(U.P) Pin: 27302",font1);
	        document.add(p3);
	        Paragraph p4 = new Paragraph("● Phone Number: 			8826119350",font1);
	        document.add(p4);
	        Paragraph p5 = new Paragraph("● Email: 			Rishtamicrofinance@gmail.com",font1);
	        document.add(p5);
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
	        Paragraph p6 = new Paragraph("Saving Customer Information: ",font2);
	        document.add(p6);
	        Paragraph p7 = new Paragraph("● Name: 			"+user.getFirstName()+" "+user.getLastName()+" ",font1);
	        document.add(p7);
	        Paragraph p0 = new Paragraph("● Relation: 			"+user.getRelation(),font1);
	        document.add(p0);
	        Paragraph p00 = new Paragraph("● Date Of Birth: 			"+user.getDob(),font1);
	        document.add(p00);
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
	        
	        Paragraph p11 = new Paragraph("Saving Details : ",font2);
	        document.add(p11);
	        Paragraph p12 = new Paragraph("● Saving Amount : 			"+user.getSavingAmount()+"",font1);
	        document.add(p12);
	        Paragraph p25 = new Paragraph("● Total Saving Amount With Intrest : 			"+user.getTotalAmountToPay()+"",font1);
	        document.add(p25);
	        Paragraph p13 = new Paragraph("● Saving Date : 			"+user.getRegistrationDate()+"",font1);
	        document.add(p13);
	        Paragraph p14 = new Paragraph("● Saving EMI Amount : 			"+user.getSavingEMIAmount()+" "+paymentType,font1);
	        document.add(p14);
	        Paragraph p23 = new Paragraph("● Saving Duration : 			"+user.getSavingDuration()+" "+durationType,font1);
	        document.add(p23);
	        Paragraph p24 = new Paragraph("● Saving Type : 			"+user.getSavingType()+"",font1);
	        document.add(p24);
	        
	       
	        
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
	        
	        Paragraph p16 = new Paragraph("Additional Terms and Condtion: ",font2);
	        document.add(p16);
	        Paragraph p17 = new Paragraph("The Lender agrees to lend to the Borrower and the Borrower agrees to borrow from the Lender for the purposes specified in Article 2 here of and on the basis of terms and conditions legal action will be taken against the candidate .",font1);
	        document.add(p17);
	        
	        document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
	        
	        Paragraph p18 = new Paragraph("Signature:",font2);
	        document.add(p18);
	        Paragraph p19 = new Paragraph("Lender's Signature: ______________________________________ Date: ______________________________",font1);
	        document.add(p19);
	        document.add(Chunk.NEWLINE);
	        Paragraph p20 = new Paragraph("Saver's Signature: _____________________________________ Date: ______________________________",font1);
	        document.add(p20);
	        document.add(Chunk.NEWLINE);
	        Paragraph granter = new Paragraph("Granter's Signature: _____________________________________ Date: ______________________________",font1);
	        document.add(granter);
	        
	        addWatermark(canvas, writer);

            // Add page border
            addPageBorder(canvas, writer);
	         
	        document.close();
	         
	    }
	    
	    private static void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.RED);
	        cell.setPadding(5);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.BLACK);
	        font.setSize(8); 
	        cell.setPhrase(new Phrase("User ID", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("First Name", font));
	        table.addCell(cell);
	        
	        //cell.setPhrase(new Phrase("Last Name", font));
	        //table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Loan/Saving Amount", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Loan/Saving Duration", font));
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
	            //table.addCell(user.getLastName());
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
	        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        PdfContentByte canvas = writer.getDirectContentUnder();
	        String logoPath = getLogoImagePath();
	        Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(300, 300); // Adjust logo size
            logo.setAlignment(Image.ALIGN_CENTER); // Align logo in center
            document.add(logo);
	        
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.RED);
	         
	        Paragraph p = new Paragraph("User EMI Details", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(9);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {4.0f, 3.5f, 3.0f, 3.5f,4.0f, 2.0f, 3.0f, 3.0f, 3.5f});
	        table.setSpacingBefore(10);
	        writeTableHeader(table);
	        writeTableData(table,listUsers);
	         
	        document.add(table);
	        
	        addWatermark(canvas, writer);

            // Add page border
            addPageBorder(canvas, writer);
	         
	        document.close();
	         
	    }
	    
	    private static void addWatermark(PdfContentByte canvas, PdfWriter writer) {
	        // Define watermark text
	        Phrase watermarkText = new Phrase("RISHITA MICRO FINANCE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 55, Font.BOLD, new Color(192, 192, 192)));

	        // Get the total number of pages
	        int totalPageCount = writer.getPageNumber();

	        for (int i = 1; i <= totalPageCount; i++) {
	            // Set transparency for the watermark
	            PdfGState gState = new PdfGState();
	            gState.setFillOpacity(0.3f);  // Watermark transparency
	            canvas.setGState(gState);

	            // Add watermark to each page
	            ColumnText.showTextAligned(
	                    canvas, 
	                    Element.ALIGN_CENTER, 
	                    watermarkText, 
	                    298, 421, 45 // Coordinates and rotation (rotate 45 degrees)
	            );
	        }
	    }

	    private static void addPageBorder(PdfContentByte canvas, PdfWriter writer) {
	        // Set border properties
	        canvas.setLineWidth(2f); // Border thickness
	        canvas.setColorStroke(new Color(255, 0, 0)); // Border color (black)

	        // Draw a rectangle border around the entire page
	        Rectangle pageSize = writer.getPageSize();
	        canvas.rectangle(20, 20, pageSize.getWidth() - 40, pageSize.getHeight() - 40); // Adjust margins as needed
	        canvas.stroke();
	    }
	    
	    private static String getLogoImagePath() throws IOException {
	    	try {
	            Path path = Paths.get("src/main/resources/static/images/micro.jpg");
                return path.toAbsolutePath().toString(); 
	        } catch (Exception e) {
	            throw new RuntimeException("Error retrieving image path: ", e);
	        }
	    }

}
