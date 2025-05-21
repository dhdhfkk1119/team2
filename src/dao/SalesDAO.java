package dao;

import dto.PhoneDTO;
import dto.SalesDTO;
import lombok.Data;
import until.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class SalesDAO {

    // 1. 판매하기 기능
    // 1.1. 판매하면 수량 감소, 판매량 증가(휴대폰 DB에 반영)


    String checkSql = "select sales_idx from sales where member_idx = ? and phone_idx = ? ";

    public void SalesPhone(int memberIdx, int phoneIdx) throws SQLException {
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkSql);) {
            checkPstmt.setInt(1, memberIdx);
            checkPstmt.setInt(2, phoneIdx);
            ResultSet rs1 = checkPstmt.executeQuery();

            if (rs1.next()) {
                String insertSql = "insert into sales(member_idx, phone_idx, sales_at)values (?, ?, current_timestamp()) ";
                String updateSql = "update phone set quantity = quantity - 1, sales_count = sales_count + 1 where phone_idx = ? ";

                try (PreparedStatement searchStmt = conn.prepareStatement(insertSql);
                     PreparedStatement updateStmt = conn.prepareStatement(updateSql);) {
                    searchStmt.setInt(1, memberIdx);
                    searchStmt.setInt(2, phoneIdx);
                    System.out.println("------------------------------");
                    updateStmt.setInt(1, phoneIdx);

                    searchStmt.executeUpdate();
                    updateStmt.executeUpdate();
                }

            } else {
                throw new SQLException("등록된 제품이 없습니다.");
            }


        }


    }

    // 2. 전체 기종에 중에 가장 많이 팔린 기종을 검색
    public List<PhoneDTO> getBestSellPhone(String searchPhone) {
        List<PhoneDTO> salesList = new ArrayList<>();
        String sql = "select * \n" +
                "from phone\n" +
                "where sales_count > 0 and phone_name like ?\n" +
                "order by sales_count desc ";


        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchPhone + "%");


            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PhoneDTO phoneDTO = new PhoneDTO();
                phoneDTO.setPhoneId(rs.getInt("phone_idx"));
                phoneDTO.setPhoneName(rs.getString("phone_name"));
                phoneDTO.setPrice(rs.getInt("price"));
                phoneDTO.setPhoneState(rs.getString("phone_state"));
                phoneDTO.setQuantity(rs.getInt("quantity"));
                phoneDTO.setSalesCount(rs.getInt("sales_count"));
                salesList.add(phoneDTO);
            }

            for (PhoneDTO phone : salesList) {
                System.out.println(phone);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salesList;
    }


    public static void main(String[] args) {

        SalesDAO salesDAO = new SalesDAO();

    }


}
