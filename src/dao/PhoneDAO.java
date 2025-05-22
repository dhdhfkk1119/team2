package dao;

import dto.PhoneDTO;
import until.DataBaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 휴대폰 관련 데이터베이스 작업을 처리하는 DAO 클래스
 */
public class PhoneDAO {

    // 새 폰을 데이터 베이스에 추가
    public void addPhone(PhoneDTO phone) {
        String query = "INSERT INTO phone(phone_name, created_at, price, phone_state, quantity, sales_count, member_idx) VALUES (?, current_date, ?, ?, ?, 0, ?)";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, phone.getPhoneName());
            pstmt.setInt(2, phone.getPrice());
            pstmt.setString(3, phone.getPhoneState());
            pstmt.setInt(4, phone.getQuantity());
            pstmt.setInt(5, phone.getMemberId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 전체 목록 조회
    public List<PhoneDTO> getAllPhone() throws SQLException {
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        String sql = "SELECT * FROM phone";

        try (Connection conn = DataBaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("phone_idx");
                String phoneName = rs.getString("phone_name");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int price = rs.getInt("price");
                String phoneState = rs.getString("phone_state");
                int quantity = rs.getInt("quantity");
                int salesCount = rs.getInt("sales_count");
                int memberIdx = rs.getInt("member_idx");

                PhoneDTO phoneDTO = new PhoneDTO(id, phoneName, createdAt, price, phoneState, quantity, salesCount, memberIdx);
                phoneDTOList.add(phoneDTO);

            }
        }
        return phoneDTOList;
    }

    // 특정 기종만 조회
    public List<PhoneDTO> searchPhoneName(String searchPhone) throws SQLException {
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        String sql = "SELECT * FROM phone WHERE phone_name LIKE ?";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, "%" + searchPhone + "%"); // 기종 이름에 해당하는 폰을 조회

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("phone_idx");
                String phoneName = rs.getString("phone_name");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int price = rs.getInt("price");
                String phoneState = rs.getString("phone_state");
                int quantity = rs.getInt("quantity");
                int salesCount = rs.getInt("sales_count");
                int memberIdx = rs.getInt("member_idx");

                PhoneDTO phoneDTO = new PhoneDTO(id, phoneName, createdAt, price, phoneState, quantity, salesCount, memberIdx);
                phoneDTOList.add(phoneDTO);
            }
        }
        return phoneDTOList;

    }

    public static void main(String[] args) {
        // 새폰 데이터 베이스에 추가
        PhoneDAO phoneDAO = new PhoneDAO();
        try {
            System.out.println(phoneDAO.getAllPhone().get(1));;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}













