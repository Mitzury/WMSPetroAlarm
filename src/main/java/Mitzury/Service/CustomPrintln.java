package Mitzury.Service;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomPrintln extends PrintStream {

    public CustomPrintln(PrintStream original) {
        super(original);
    }

    @Override
    public void println(String x) {
        // Получаем текущую дату и время
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Форматируем дату и время в формате dd.MM.yy HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
        String formattedTime = currentDateTime.format(formatter);
        super.println("[" + formattedTime + "] " + x);
    }

}
