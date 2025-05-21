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
        String query = "INSERT INTO phone(phone_name, created_at, price, phone_state, quantity, sales_count) VALUES (?, current_date, ?, ?, ?, 0)";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(query)) {
            psmt.setString(1, phone.getPhoneName());
            psmt.setInt(2, phone.getPrice());
            psmt.setString(3, phone.getPhoneState());
            psmt.setInt(4, phone.getQuantity());
            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 전체 목록 조회
    public List<PhoneDTO> getAllPhone() throws SQLException {
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        String sql = "SELECT * FROM phone";
        try(Connection conn = DataBaseUtil.getConnection();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String phoneName = rs.getString("phone_name");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int price = rs.getInt("price");
                String phoneState = rs.getString("phone_state");
                int quantity = rs.getInt("quantity");
                int salesCount = rs.getInt("sales_count");

                PhoneDTO phoneDTO = new PhoneDTO(id, phoneName, createdAt, price, phoneState, quantity, salesCount);
                phoneDTOList.add(phoneDTO);

            }
        }
        return phoneDTOList;
    }

    // 기종만 조회
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

                PhoneDTO phoneDTO = new PhoneDTO(id, phoneName, createdAt, price, phoneState, quantity, salesCount);
                phoneDTOList.add(phoneDTO);
            }
        }
        return phoneDTOList;

    }

//    public static void main(String[] args) {
//        PhoneDAO phoneDAO = new PhoneDAO();
//        try {
//            phoneDAO.addPhone(new PhoneDTO(0, "승민", LocalDateTime.now(), 1, "나쁨", 1, 1));
//        } catch (Exception e) {
//
//        }
//
//        // 전체 조회
//        PhoneDAO phoneDAO1 = new PhoneDAO();
//        try {
//            ArrayList<PhoneDTO> selectedPhoneList =
//                    (ArrayList) phoneDAO.searchPhoneName("6");
//            for (int i = 0; i < selectedPhoneList.size(); i++) {
//                System.out.println(selectedPhoneList.get(i));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        // 기종만 조회
//        PhoneDAO phoneDAO2 = new PhoneDAO();
//
//        try {
//            // 예시: "Galaxy"를 포함하는 기종 조회
//
//
//            // 조회된 기종 이름 출력
//            for (String name : phoneNames) {
//                System.out.println(name);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//    }
    }













