package Mitzury;

import Mitzury.SQL.*;
import Mitzury.SendMessage.Telegramm;
import Mitzury.Service.CustomPrintln;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Mitzury.SendMessage.Telegramm.getTime;
import static Mitzury.Service.PrepareMessage.buildMessageT1;
import static Mitzury.Service.PrepareMessage.buildMessageWAIT;

public class Main {

    // Карта для хранения экземпляров GetData
    private static final Map<String, GetData> getDataInstances = new HashMap<>();

    public static void main(String[] args) throws SQLException, InterruptedException {

        // Заменяем стандартный System.out на наш кастомный PrintStream
        System.setOut(new CustomPrintln(System.out));

        while (true) {
            // Отправка в ночной чат
            work();
            System.out.println("___________");
            // Ожидание перед следующим циклом
            Thread.sleep(90_000);
        }
    }

    public static void work(String id, String sqlQuery, String systext, String time, String func, String... chatids) throws SQLException, InterruptedException {
        System.out.println("Worker начал работу над: " + id + " " + func + " " + time);
        if (time.equalsIgnoreCase(getTime())) {
            Connection connection = null;
            connection = ConnectSQL.getConnection(id);

            // Получаем или создаем экземпляр GetData для данного id + func
            String key = sqlQuery + "_" + func + "_" + time; // Уникальный ключ для каждого вызова
            GetData getDataInstance = getDataInstances.computeIfAbsent(key, k -> new GetData());

            if ("WAIT".equalsIgnoreCase(func)) {
                List<Map<String, Object>> wdata = getDataInstance.getData(connection, sqlQuery);
                if (wdata.isEmpty()) {
                    System.out.println("Нет данных для отправки " + func);
                } else {
                    String result = buildMessageWAIT(wdata);
                    for (String chatid : chatids) {
                        System.out.println("Отправка сообщения в чат о WAIT: " + chatid);
                        Telegramm.sendMessage(chatid, result, systext, time);
                    }
                }
            }

            if ("T1".equalsIgnoreCase(func)) {
                List<Map<String, Object>> data = getDataInstance.getData(connection, sqlQuery);
                if (data.isEmpty()) {
                    System.out.println("Нет данных для отправки " + func);
                } else {
                    String tresult = buildMessageT1(data);
                    for (String chatid : chatids) {
                        System.out.println("Отправка сообщения в чат о T1: " + chatid);
                        Telegramm.sendMessage(chatid, tresult, systext, time);
                    }
                }
            }
            ConnectSQL.closeConnection(connection);
        } else {
            System.out.println("Время worker не пришло");
        }
        System.out.println("Worker закончил работу" + "\n");
    }
}