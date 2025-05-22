package dao;

import dto.PhoneDTO;
import dto.SalesDTO;
import lombok.Data;
import until.DataBaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class SalesDAO {

    // 1. 판매하기 기능
    // 1.1. 판매하면 수량 감소, 판매량 증가(휴대폰 DB에 반영)


    String checkSql = "select phone_idx from phone where phone_idx = ? ";
    String memberIdxSQl = "select * from sales where member_idx = ?";

    public void SalesPhone(int memberIdx, int phoneIdx) throws SQLException {
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkSql);) {
             checkPstmt.setInt(1, phoneIdx);
             ResultSet rs1 = checkPstmt.executeQuery();

            if (rs1.next() || !rs1.next()) {
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
                    System.out.println("판매가 완료되었습니다.");
                }

            } else {
                throw new SQLException("등록된 제품이 없습니다.");
            }
        }

    }

    // 2. 전체 기종에 중에 가장 많이 팔린 기종을 검색
    public List<PhoneDTO> getBestSellPhone(String searchPhone) throws SQLException {

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
        }

        return salesList;
    }

    // 3. 구매 목록 만들기 - 뭐가필요할까
    //    전체 조회해서 필요한 걸 셀렉해야함.
    //    폰을 조회해서 sales_count만 조회하면됨.
    // 다른사람이 산건 필요없음. 내가 산 내목록만 나오면됨.
    // 다중 조회가 될 수는 있음.

    public List<PhoneDTO> buyPhoneList(int useridx)  {
        Connection conn = null;
        List<PhoneDTO> phoneList = new ArrayList<>();
        try {
            conn = DataBaseUtil.getConnection();

            conn.setAutoCommit(false);

            try (PreparedStatement checkPstmt = conn.prepareStatement(memberIdxSQl)) {
                checkPstmt.setInt(1, useridx);
                 ResultSet rs1 = checkPstmt.executeQuery();
                 while(rs1.next()) {
                     Integer id = rs1.getInt("phone_idx");
                    String searchSQL = "select * from phone where phone_idx = ?";
                    PreparedStatement pstmt = conn.prepareStatement(searchSQL);
                     pstmt.setInt(1, id);
                     ResultSet rs2 = pstmt.executeQuery();
                     if(rs2.next()){
                         int idx = rs2.getInt("phone_idx");
                         String phoneName = rs2.getString("phone_name");
                         LocalDateTime createdAt = rs2.getTimestamp("created_at").toLocalDateTime();
                         int price = rs2.getInt("price");
                         String phoneState = rs2.getString("phone_state");
                         int quantity = rs2.getInt("quantity");
                         int salesCount = rs2.getInt("sales_count");

                         PhoneDTO phoneDTO = new PhoneDTO(idx, phoneName, createdAt, price, phoneState, quantity, salesCount);
                         phoneList.add(phoneDTO);
                     }
                 }

            }

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }



        return phoneList;
    }

}
