package notification;

public class EmailNotification implements NotificationService {
    @Override
    public void sendNotification(String message) {
        System.out.println("\n===========Email Notification ========");
        System.out.println(message);
        System.out.println("========================================");
    }
}
