package dao;

import dto.PhoneDTO;

import java.sql.SQLException;

/**
 * 휴대폰 관련 데이터베이스 작업을 처리하는 DAO 클래스
 */
public class PhoneDAO {

    // 새 폰을 데이터 베이스에 추가
    public void addPhone(PhoneDTO phone) throws SQLException{
        String sql = "INSERT INTO phones (phone_id, phone_name, created_at, price, phone_state";
    }
}
