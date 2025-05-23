package dao;

import dto.PhoneDTO;
import dto.RecentlyDTO;
import until.DataBaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 최근 본 상품의 대한 데이터베이스 작업을 처리
 * 1. 최근 본 상품 추가
 * 2. 최근 본 상품 조회 -> 상품에 에 대한 정보를(phone 정보를 가져와야함)
 * 3. 단일 삭제
 * 4. 최근 내역에서 구매하기
 * 5. 최그 내역에서 장바구니 담기
 */
public class RecentlyDAO {

    // 최근 본 상품 삽입
    public void insert(RecentlyDTO recentlyViewedDTO) throws SQLException {
        String query = "INSERT INTO recently_viewed (member_idx, phone_idx) VALUES (?, ?)";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, recentlyViewedDTO.getMemberIdx());
            pstmt.setInt(2, recentlyViewedDTO.getPhoneIdx());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    // 최근 본  특정 상품 조회
    public List<PhoneDTO> getAllRecently(int memberId) throws SQLException {
        String sql = "SELECT * FROM recently_viewed WHERE member_idx = ?";
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("phone_idx");
                String sql1 = "SELECT * FROM phone WHERE phone_idx = ?";
                PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                pstmt1.setInt(1, id);
                ResultSet rs1 = pstmt1.executeQuery();
                if (rs1.next()) {
                    PhoneDTO phoneDTO = new PhoneDTO();
                    phoneDTO.setPhoneId(rs1.getInt("phone_idx"));
                    phoneDTO.setPhoneName(rs1.getString("phone_name"));
                    phoneDTO.setCreatedAt(rs1.getTimestamp("created_at").toLocalDateTime());
                    phoneDTO.setPrice(rs1.getInt("price"));
                    phoneDTOList.add(phoneDTO);
                }

            }
        }
        return phoneDTOList;
    }

    // 특정 기록만 삭제
    public void deleteViewedIdx(int viewedIdx) throws SQLException {
        String sql = "DELETE FROM recently_viewed WHERE viewed_idx = ?";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, viewedIdx);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("삭제할 기록이 존재하지 않습니다: viewedIdx=" + viewedIdx);
            }
        }
    }


public static void main(String[] args) {
    // 최근 본 상품 추가
    RecentlyDAO recentlyDAO = new RecentlyDAO();
    try {
        System.out.println(recentlyDAO.getAllRecently(1).get(0));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }





}
}


