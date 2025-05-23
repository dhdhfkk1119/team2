package dao;


import dto.CartDTO;
import dto.PhoneDTO;
import until.DataBaseUtil;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 있어야 할 기능
 * 1. 장바구니 추가 o
 * 2. 장바구니 삭제 o
 * 3. 장바구니에서 구매 o
 * 4. 장바구니에서 수량 수정
 * 5. 장바구니에 담긴 상품 정보 o
 * <p>
 * 장바구니안에 있는 상품을 선택해서 구매하는 -> 선택하는 매개변수 phone_idx , member_idx
 */

// cart DTO


//private int cartIdx;
//private int memberIdx;
//private int phoneIdx;
//private int quantity;
//private LocalDate insertAt;
public class CartDAO {

    // 장바구니 추가
    public void insertCart(int memberIdx, int phoneIdx, int quantity) throws SQLException {
        String isCheckSql = "SELECT * FROM cart WHERE member_idx = ? AND phone_idx = ?";
        String insertSql = "insert into cart (member_idx, phone_idx, quantity) values (?, ?, ?)";
        String updateSql = "UPDATE cart Set quantity = quantity + ?";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement ChecPpstmt = conn.prepareStatement(isCheckSql)) {
            ChecPpstmt.setInt(1, memberIdx);
            ChecPpstmt.setInt(2, phoneIdx);
            ResultSet rs = ChecPpstmt.executeQuery();
            if (!rs.next()) {
                PreparedStatement pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, memberIdx);
                pstmt.setInt(2, phoneIdx);
                pstmt.setInt(3, quantity);
                pstmt.executeUpdate();
            } else {
                PreparedStatement pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, quantity);
                pstmt.executeUpdate();
            }

        }
    }

    // 장바구니에서 삭제
    public void deleteCart(int cartIdx) throws SQLException {
        // 사용할 쿼리문 먼저 생각
        String deleteCartSql = "delete from cart where cartIdx = ? ";


        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement deletePstmt = conn.prepareStatement(deleteCartSql)) {
            deletePstmt.setInt(1, cartIdx);

            int deletedRow = deletePstmt.executeUpdate();
            if (deletedRow > 0) {
                System.out.println("삭제 완료.");
            } else {
                System.out.println("삭제 된 것 없음.");
            }

        }

    }
    //장바구니 수량 수정하기


    public void updateQuantity(int cartIdx, int newQuantity) throws SQLException {
        //사용할 쿼리문 먼저 생각
        String updateCart = "update cart set quantity = ? where cart_idx = ?";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement updatePstmt = conn.prepareStatement(updateCart)) {
            updatePstmt.setInt(1, cartIdx);
            updatePstmt.setInt(2, newQuantity);
            int updatedRow=updatePstmt.executeUpdate();

            if(updatedRow>0){
                System.out.println("수정완료 . 수정된 행 : " + updatedRow);
            }else{
                System.out.println("수정된 것 없음.");
            }

        }
    }


    // 장바구니에서 구매
    // ? 에 해당하는값이 필요함 날짜는 자동이니 member_idx, phone_idx 필요
    public void buyCart(int memberIdx, int phoneIdx) throws SQLException {

        //  일단 사용할 쿼리문만 먼저 생각
        // 구매할려고할때 장바구니에 수량이 몇개있는지 체크
        String selectQuantitySql = "select quantity from cart where member_idx = ? and phone_idx = ?";
        // sales에 구매 기록 남기기 위해서 있어야함
        String insertSalesSql = "insert into sales (member_idx, phone_idx) values (?, ?)";
        // 몇번 팔렸는지 기록
        String updatePhoneSql = "update phone set sales_count = sales_count + ? where phone_idx = ?";
        // 구매하면 장바구니에서 없어지게끔
        String deleteCartSql = "delete from cart where member_idx = ? and phone_idx = ?";


        try (Connection conn = DataBaseUtil.getConnection()) {
            // 1. 수량 확인
            int quantity = 0;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuantitySql)) {
                selectStmt.setInt(1, memberIdx);
                selectStmt.setInt(2, phoneIdx);
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    quantity = rs.getInt("quantity");
                    try (PreparedStatement updateStmt = conn.prepareStatement(updatePhoneSql)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setInt(2, phoneIdx);
                        updateStmt.executeUpdate();
                    }
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteCartSql)) {
                        deleteStmt.setInt(1, memberIdx);
                        deleteStmt.setInt(2, phoneIdx);
                        deleteStmt.executeUpdate();
                    }
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSalesSql)) {
                        insertStmt.setInt(1, memberIdx);
                        insertStmt.setInt(2, phoneIdx);
                        insertStmt.executeUpdate();
                    }

                    System.out.println("구매 완료 phoneIdx: " + phoneIdx + ",quantity: " + quantity);
                } else {
                    System.out.println("장바구니에 해당 상품 없음");
                    return;
                }
            }


        }
    }


    //장바구니 상품 정보

    public List<PhoneDTO> showCart(int memberIdx) throws SQLException {

        String sql = "select * from cart where member_idx = ?";
        String sql2 = "select * from phone where phone_idx = ?";
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberIdx);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int phoneIdx = rs.getInt("phone_idx");
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setInt(1, phoneIdx);
                ResultSet phoneRs = pstmt2.executeQuery();
                if (phoneRs.next()) {
                    PhoneDTO phoneDTO = new PhoneDTO();
                    phoneDTO.setPhoneId(phoneRs.getInt("phone_idx"));
                    phoneDTO.setPhoneName(phoneRs.getString("phone_name"));
                    phoneDTO.setPrice(phoneRs.getInt("price"));
                    phoneDTO.setPhoneState(phoneRs.getString("phone_state"));
                    phoneDTOList.add(phoneDTO);
                }

            }

        }
        return phoneDTOList;
    }

    public static void main(String[] args) throws SQLException {
        CartDAO cartDAO = new CartDAO();

        cartDAO.buyCart(1, 1);

    }

}







