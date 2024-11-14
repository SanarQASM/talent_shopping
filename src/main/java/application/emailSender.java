package application;

import java.util.Properties;
import java.util.Random;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class emailSender {
    public static String randomNumbers;

    public int startSendMail(String to) throws Exception {
        int result =-1;
        try {
            String from = "sanar.software18@gmail.com";
            String password = "yxvz opxx ahdm arka";
            String host = "smtp.gmail.com";
            String port = "587";
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new jakarta.mail.PasswordAuthentication(from, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Login to System Book For Reader");
            generateRandomNumber();
            message.setText("Your code is: { "+randomNumbers+" }Don't share it with anyone");

            Transport.send(message);
        }catch (AddressException e) {
            result =2;
        }
        catch(MessagingException ec) {
            result =1;
        }
        catch(Exception e) {
//            nC.showNotificationSomethingWrong("failed to Send Email");
            throw new Exception("Please Try again later");
        }
        return result;
    }
    private void generateRandomNumber() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        randomNumbers = Integer.toString(randomNumber);
    }
//	public int sendEmailToForget(String byUser,String message,String imageUrl) {
//		int result =-1;
//		try {
//			String from = "sanar.software18@gmail.com";
//	        String password = "yxvz opxx ahdm arka";
//	        String host = "smtp.gmail.com";
//	        String port = "587";
//	        String sendEmailTo = "sanar.qasm18@gmail.com";
//	            Properties properties = new Properties();
//	            properties.put("mail.smtp.host", host);
//	            properties.put("mail.smtp.port", port);
//	            properties.put("mail.smtp.auth", "true");
//	            properties.put("mail.smtp.starttls.enable", "true");
//
//	            Session session = Session.getInstance(properties, new Authenticator() {
//	                @Override
//	                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication()   {
//	                    return new jakarta.mail.PasswordAuthentication(from, password);
//	                }
//	            });
//
//	            Message message1 = new MimeMessage(session);
//	            message1.setFrom(new InternetAddress(from));
//	            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendEmailTo));
//	            message1.setSubject("By User: " + "\"" + byUser + "\"");
//	            MimeMultipart multipart = new MimeMultipart();
//	            MimeBodyPart textPart = new MimeBodyPart();
//	            textPart.setText(message);
//	            multipart.addBodyPart(textPart);
//	            MimeBodyPart imagePart = new MimeBodyPart();
//	            URI uri = new URI(imageUrl);
//	            imageUrl= uri.getPath();
//	            DataSource source = new FileDataSource(new File(imageUrl));
//	            imagePart.setDataHandler(new DataHandler(source));
//	            int lastSeparatorIndex = imageUrl.lastIndexOf("/");
//	            imageUrl= imageUrl.substring(lastSeparatorIndex + 1);
//	            imagePart.setFileName(imageUrl);
//	            multipart.addBodyPart(imagePart);
//	            message1.setContent(multipart);
//	            Transport.send(message1);
//	}	catch (AddressException e) {
//		result =2;
//	}	catch(MessagingException  ec) {
//		result =1;
//	}
//	catch(Exception e) {
//			notificationsClass nC=new notificationsClass();
//			nC.showNotificaitonSomethingWrong("failed to send Email");
//	}
//	return result;
//}
}
