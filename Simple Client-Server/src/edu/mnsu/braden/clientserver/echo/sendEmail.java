import java.util.Properties;

public class sendEmail{
    public static void main(String[] args){
        String to = "";
        String from = "";
        String host = "localhost";
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);

        //Session session = Session.getDefaultInstance(properties);
    }
}