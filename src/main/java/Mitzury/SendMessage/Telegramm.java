package Mitzury.SendMessage;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;

public class Telegramm {

    private static final String BOT_TOKEN = "_BOT_TOKEN";

    public static void sendMessage(String chatid, String text, String systext, String time) {
        if (time.equalsIgnoreCase(getTime())) {
            try {
                String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String postData = "chat_id=" + chatid + "&text=" + systext + "\n" + text;
                byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(postDataBytes);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Сообщение успешно отправлено!");
                } else {
                    System.out.println("Ошибка при отправке сообщения: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTime() {
        LocalTime now = LocalTime.now();
        LocalTime startDay = LocalTime.of(8, 0); // 8:00 утра
        LocalTime endDay = LocalTime.of(20, 0); // 20:00 вечера

        if (now.isAfter(startDay) && now.isBefore(endDay)) {
            return "D";
        } else {
            return "N";
        }
    }
}
