package Mitzury.SQL;
import java.sql.*;


public class ConnectSQL {

    public static Connection getConnection(String id) throws SQLException {
        ConnectionData.ConnectionDataInner data = null;
        Connection connection = null;

        try {
            // Получаем данные для подключения
            data = ConnectionData.getConnectionData(id);
            if (data == null) {
                throw new SQLException("No connection data found for ID: " + id);
            }
            // Устанавливаем соединение с базой данных
            connection = DriverManager.getConnection(data.getUrl(), data.getUser(), data.getPassword());
            System.out.println(id + " Соединение с базой данных успешно установлено.");

        } catch (SQLException e) {
            // Логируем ошибку и выбрасываем исключение с более информативным сообщением
            System.err.println(id + " Ошибка при установке соединения с базой данных: " + e.getMessage());
            throw new SQLException("Не удалось установить соединение с базой данных для ID: " + id, e);
        }

        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение с базой данных успешно закрыто.");
            } catch (SQLException e) {
                // Логируем ошибку и выбрасываем исключение с более информативным сообщением
                System.err.println("Ошибка при закрытии соединения с базой данных: " + e.getMessage());
                throw new SQLException("Не удалось закрыть соединение с базой данных", e);
            }
        }
    }
}
