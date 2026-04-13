package util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

/**
 * SMTP Email utility using Gmail.
 * Sends borrow receipts and staff credential notifications.
 */
public class EmailService {

    private static final String FROM_EMAIL = "bookeeprtm@gmail.com";
    private static final String APP_PASSWORD = "xjjn ninp nhkp satv";

    private static jakarta.mail.Session getMailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return jakarta.mail.Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });
    }

    /**
     * Sends a borrow receipt email to a member.
     */
    public static void sendBorrowReceipt(String memberEmail, String memberName,
                                          String barcode, String dueDate) {
        new Thread(() -> {
            try {
                jakarta.mail.Session session = getMailSession();
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL, "BOOKEEPR Library"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(memberEmail));
                message.setSubject("Borrow Receipt - BOOKEEPR");

                String html = "<html>"
                    + "<body style='font-family: Georgia, serif; background-color: #f5f5f5; padding: 20px;'>"
                    + "<div style='max-width: 500px; margin: 0 auto; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>"
                    + "<div style='background: #000; color: #fff; padding: 24px; text-align: center;'>"
                    + "<h1 style='margin: 0; font-size: 22px;'>BOOKEEPR</h1>"
                    + "<p style='margin: 5px 0 0; font-size: 13px; color: #ccc;'>Library Management System</p>"
                    + "</div>"
                    + "<div style='padding: 30px;'>"
                    + "<h2 style='color: #333; margin-top: 0;'>Borrow Receipt</h2>"
                    + "<p>Hello <strong>" + memberName + "</strong>,</p>"
                    + "<p>Your borrow has been recorded successfully:</p>"
                    + "<table style='width: 100%; border-collapse: collapse; margin: 15px 0;'>"
                    + "<tr style='border-bottom: 1px solid #eee;'>"
                    + "<td style='padding: 8px 0; color: #888;'>Barcode</td>"
                    + "<td style='padding: 8px 0; text-align: right; font-weight: bold;'>" + barcode + "</td>"
                    + "</tr>"
                    + "<tr style='border-bottom: 1px solid #eee;'>"
                    + "<td style='padding: 8px 0; color: #888;'>Due Date</td>"
                    + "<td style='padding: 8px 0; text-align: right; font-weight: bold;'>" + dueDate + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "<p style='color: #888; font-size: 12px; margin-top: 20px;'>"
                    + "Please return the item by the due date to avoid fines.<br>"
                    + "Thank you for using BOOKEEPR!</p>"
                    + "</div></div></body></html>";

                message.setContent(html, "text/html; charset=utf-8");
                Transport.send(message);
                System.out.println("Borrow receipt sent to " + memberEmail);

            } catch (Exception e) {
                System.out.println("Failed to send borrow receipt: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Sends a return notification email to a member.
     */
    public static void sendReturnNotification(String memberEmail, String memberName,
                                               String barcode) {
        new Thread(() -> {
            try {
                jakarta.mail.Session session = getMailSession();
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL, "BOOKEEPR Library"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(memberEmail));
                message.setSubject("Return Confirmed - BOOKEEPR");

                String html = "<html>"
                    + "<body style='font-family: Georgia, serif; background-color: #f5f5f5; padding: 20px;'>"
                    + "<div style='max-width: 500px; margin: 0 auto; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>"
                    + "<div style='background: #000; color: #fff; padding: 24px; text-align: center;'>"
                    + "<h1 style='margin: 0; font-size: 22px;'>BOOKEEPR</h1>"
                    + "<p style='margin: 5px 0 0; font-size: 13px; color: #ccc;'>Library Management System</p>"
                    + "</div>"
                    + "<div style='padding: 30px;'>"
                    + "<h2 style='color: #333; margin-top: 0;'>Return Confirmed</h2>"
                    + "<p>Hello <strong>" + memberName + "</strong>,</p>"
                    + "<p>Your return has been processed:</p>"
                    + "<table style='width: 100%; border-collapse: collapse; margin: 15px 0;'>"
                    + "<tr style='border-bottom: 1px solid #eee;'>"
                    + "<td style='padding: 8px 0; color: #888;'>Barcode</td>"
                    + "<td style='padding: 8px 0; text-align: right; font-weight: bold;'>" + barcode + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "<p style='color: #888; font-size: 12px; margin-top: 20px;'>"
                    + "Thank you for returning the item on time!</p>"
                    + "</div></div></body></html>";

                message.setContent(html, "text/html; charset=utf-8");
                Transport.send(message);
                System.out.println("Return notification sent to " + memberEmail);

            } catch (Exception e) {
                System.out.println("Failed to send return notification: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Sends staff credentials after signup.
     */
    public static void sendStaffCredentials(String staffEmail, String fullName,
                                             String username, String plainPassword) {
        new Thread(() -> {
            try {
                jakarta.mail.Session session = getMailSession();
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL, "BOOKEEPR Library"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(staffEmail));
                message.setSubject("Your Staff Account - BOOKEEPR");

                String html = "<html>"
                    + "<body style='font-family: Georgia, serif; background-color: #f5f5f5; padding: 20px;'>"
                    + "<div style='max-width: 500px; margin: 0 auto; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>"
                    + "<div style='background: #000; color: #fff; padding: 24px; text-align: center;'>"
                    + "<h1 style='margin: 0; font-size: 22px;'>BOOKEEPR</h1>"
                    + "<p style='margin: 5px 0 0; font-size: 13px; color: #ccc;'>Library Management System</p>"
                    + "</div>"
                    + "<div style='padding: 30px;'>"
                    + "<h2 style='color: #333; margin-top: 0;'>Welcome, " + fullName + "!</h2>"
                    + "<p>Your staff account has been created. Here are your credentials:</p>"
                    + "<table style='width: 100%; border-collapse: collapse; margin: 15px 0;'>"
                    + "<tr style='border-bottom: 1px solid #eee;'>"
                    + "<td style='padding: 8px 0; color: #888;'>Username</td>"
                    + "<td style='padding: 8px 0; text-align: right; font-weight: bold;'>" + username + "</td>"
                    + "</tr>"
                    + "<tr style='border-bottom: 1px solid #eee;'>"
                    + "<td style='padding: 8px 0; color: #888;'>Password</td>"
                    + "<td style='padding: 8px 0; text-align: right; font-weight: bold;'>" + plainPassword + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "<p style='color: #d00; font-size: 12px; margin-top: 20px;'>"
                    + "Please change your password after your first login.</p>"
                    + "</div></div></body></html>";

                message.setContent(html, "text/html; charset=utf-8");
                Transport.send(message);
                System.out.println("Staff credentials sent to " + staffEmail);

            } catch (Exception e) {
                System.out.println("Failed to send staff credentials: " + e.getMessage());
            }
        }).start();
    }
}
