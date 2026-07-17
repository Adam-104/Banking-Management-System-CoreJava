package notification;

public class SMSNotification implements NotificationService {
    @Override
    public void sendNotification(String message) {
        System.out.println("\n======== SMS Notification ========");
        System.out.println(message);
        System.out.println("====================================");
    }
}
