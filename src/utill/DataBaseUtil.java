package utill;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Getter
@Setter
public class DataBaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/usedphone?serverTimezone=Asia/Seoul";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "asd1234";

    // 데이터벵스 연결 객체를 변환 하는 함수
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PWD);
    }


}
