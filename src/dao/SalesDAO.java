package dao;

import dto.SalesDTO;
import lombok.Data;
import until.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data

public class SalesDAO {

    // 1. 구매내역
    // 1.1. 판매하면 수량 감소
    //      누가 판매했는지?
    //      누가 구매했는지?

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

    // 2. 제일 많이 팔린 기종 보여주기
    public List<SalesDTO> getBestSellPhone() {
        List<SalesDTO> salesList = new ArrayList<>();
        String sql = " ";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                SalesDTO salesDTO = new SalesDTO();
                salesDTO.setMemberIdx(rs.getInt("member_idx"));
                salesDTO.setPhoneIdx(rs.getInt("phone_idx"));
                salesList.add(salesDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salesList;
    }


    public static void main(String[] args) {

        SalesDAO salesDAO = new SalesDAO();
        try {
            salesDAO.SalesPhone(1, 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
