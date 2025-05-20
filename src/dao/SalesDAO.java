package dao;

import lombok.Data;
import until.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data

public class SalesDAO {

    // 1. 구매내역
    // 1.1. 판매하면 수량 감소
    //      누가 판매했는지?
    //      누가 구매했는지?

    String checkSql = "select sales_idx from sales where member_idx = ? and phone_idx = ? ";
    public void SalesPhone(int memberIdx, int phoneIdx) throws SQLException{
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkSql);) {
            checkPstmt.setInt(1, memberIdx);
            checkPstmt.setInt(2, phoneIdx);
            ResultSet rs1 = checkPstmt.executeQuery();
            if (rs1.next()) {
                String insertSql = "insert into sales(member_idx, phone_idx, sales_at)values (1, 1, current_timestamp()) ";
                String updateSql = "update phone set quantity = quantity - 1 where member_idx = ? and phone_idx = ? ";

                PreparedStatement borrowStmt = conn.prepareStatement(insertSql);
            }



        }


    }




}
