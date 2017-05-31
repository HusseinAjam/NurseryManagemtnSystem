import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

public class Email {
	
/*	public void saveAsPng() {
       WritableImage image = ChildListView.snapshot(new SnapshotParameters(), null);

       // TODO: probably use a file chooser here
       File file = new File("chart.png");

       try {
           ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
       } catch (IOException e) {
           // TODO: handle exception here
       }
   }  */
   
 public  void CreatePDF (String[] reportForEmail) throws MalformedURLException, IOException, DocumentException
   {	  
	   // Create the PDF file and leave it ready to be send soon
	    final BufferedImage image = ImageIO.read(new File("logo.jpg"));
        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString(reportForEmail [0], 50, 50);
        g.drawString(reportForEmail [1], 50, 75);
        g.drawString(reportForEmail [2], 50, 100);
        g.drawString(reportForEmail [3], 50, 125);
        g.drawString(reportForEmail [4], 50, 150);
        g.drawString(reportForEmail [5], 50, 175);
        g.drawString(reportForEmail [6], 50, 200);

        g.dispose();
        ImageIO.write(image, "png", new File("temp.png"));
        Document document = new Document();
          
  		PdfWriter.getInstance(document, new FileOutputStream("ForSend.pdf"));
        document.open();
       	Image img = Image.getInstance("temp.png"); 
       	document.add(img);
        document.close();
   }
   
  public void SendPDF (String CarerEmail) throws MessagingException
   {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("northantsday@gmail.com","123456789nd");
				}
			});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("northantsday@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(CarerEmail));
		message.setSubject("Northantsday Nursery Notifications");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Hello Sir/Madam, Please See the attached file");
        Multipart MessageContainer = new MimeMultipart();
        MessageContainer.addBodyPart(messageBodyPart);
        
         // Attach the file
        messageBodyPart = new MimeBodyPart();
        String filename = System.getProperty("user.dir");
        filename = filename + "/ForSend.pdf";
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        MessageContainer.addBodyPart(messageBodyPart);        
        message.setContent(MessageContainer);

        // Send message
        JOptionPane.showMessageDialog(null, "Sending....", "Emailing", JOptionPane.INFORMATION_MESSAGE);
 		Transport.send(message);
        JOptionPane.showMessageDialog(null, "Done", "Emailing", JOptionPane.INFORMATION_MESSAGE);
   }
}
