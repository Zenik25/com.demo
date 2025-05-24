package com.demo.servicelmpl;

import com.demo.POJO.Bill;
import com.demo.POJO.Email;
import com.demo.constents.ResConstants;
import com.demo.dao.BillDao;
import com.demo.dao.EmailDao;
import com.demo.service.EmailService;
import com.demo.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.demo.POJO.Delivery;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailDao emailDao;
    private final EmailSenderService emailSenderService;
//

    @Autowired
    private BillDao billDao;  // Inject BillDao to save the bill

    @Autowired
    public EmailServiceImpl(EmailDao emailDao, EmailSenderService emailSenderService) {
        this.emailDao = emailDao;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public Email saveEmail(Email email) {
        Email savedEmail = emailDao.save(email);  // Save email to database
        sendEmail(savedEmail);  // Send email after saving it to the database
        return savedEmail;
    }

//    @Override
//    public void sendEmail(Email email) {
//        try {
//            emailSenderService.sendEmail(email);  // Call the EmailSenderService to send email
//        } catch (Exception e) {
//            // Handle exception (you could log this or handle the failure accordingly)
//            System.err.println("Error sending email: " + e.getMessage());
//        }
//    }
@Autowired
private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Email email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(email.getRecipient());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText());

            // Attach PDF if uuid is provided and file exists
            if (email.getId() != null) {
                String filePath = ResConstants.STORE_LOCATION + "\\" + email.getId() + ".pdf";
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    FileSystemResource file = new FileSystemResource(pdfFile);
                    helper.addAttachment("Invoice-" + email.getId() + ".pdf", file);
                }
            }

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Autowired
//    private JavaMailSender mailSender;
@Autowired
private JavaMailSender mailSender;

@Override
public void sendBillEmail(Delivery delivery) {
    try {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(delivery.getEmail());
        helper.setSubject("Your Order Invoice - " + delivery.getId());

        StringBuilder body = new StringBuilder();
        body.append("Dear ").append(delivery.getName()).append(",\n\n")
                .append("Thank you for your order. Below is the Bill Slit.\n\n")
//                .append("Order ID: ").append(delivery.getId()).append("\n")
//                .append("Total Amount: ").append(delivery.getTotalAmount()).append(" MMK\n")
//                .append("Payment Method: ").append(delivery.getPaymentMethod()).append("\n")
//                .append("Products: ")
//                .append(delivery.getOrderedProducts().stream()
//                        .map(op -> op.getProductName() + " x" + op.getQuantity())
//                        .reduce((op1, op2) -> op1 + ", " + op2)
//                        .orElse("No products"))
//                .append("\n\n")
                .append("We appreciate your visiting!\n\n")
                .append("Best regards,\nYour Food Delivery Team");

        helper.setText(body.toString());

        // Attach PDF
        String filePath = ResConstants.STORE_LOCATION + File.separator + delivery.getId() + ".pdf";
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            FileSystemResource pdfAttachment = new FileSystemResource(file);
            helper.addAttachment("Invoice-" + delivery.getId() + ".pdf", pdfAttachment);
        } else {
            System.err.println("PDF file not found: " + filePath);
        }

        mailSender.send(mimeMessage);
    } catch (MessagingException e) {
        System.err.println("Failed to send email: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Unexpected error: " + e.getMessage());
        e.printStackTrace();
    }
}



}
