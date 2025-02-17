package Mitzury.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetData {
    // Глобальная переменная для хранения предыдущих данных
    private List<Map<String, Object>> oldData = new ArrayList<>();


    public List<Map<String, Object>> getData(Connection connection, String sqlQuery) throws SQLException {
        List<Map<String, Object>> newData = new ArrayList<>();
        List<Map<String, Object>> currentData = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    rowData.put(columnName, columnValue);
                }
                newData.add(rowData);
            }
        }
        // Сравнение значений
        for (Map<String, Object> newRow : newData) {
            if (!containsRow(oldData, newRow)) {
                currentData.add(newRow);
            }
        }
        oldData.clear();
        oldData.addAll(newData); // Обновляем oldData всеми новыми данными
        return currentData;
    }

    // Вспомогательный метод для сравнения строк
    private boolean containsRow(List<Map<String, Object>> oldData, Map<String, Object> newRow) {
        for (Map<String, Object> oldRow : oldData) {
            if (oldRow.equals(newRow)) {
                return true;
            }
        }
        return false;
    }

}
