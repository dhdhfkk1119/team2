package dao;

/*
 * 회원 가입
 * 회원 가입을 할때 이름 아이디 비밀번호를 정함
 * 가입을 하면 고유번호가 정해지고 가입 시간이 나옴
 */

import dto.MemberDTO;
import until.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public void addMember(MemberDTO memberDTO) throws SQLException {
        String sql = "insert into member (user_name, user_id, password, member_at) \n" +
                "values (?, ?, ?, current_date) ";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberDTO.getUserName());
            pstmt.setString(2, memberDTO.getUserId());
            pstmt.setString(3, memberDTO.getPassword());
            pstmt.executeUpdate();
        }
    }

    public List<MemberDTO> allMember() throws SQLException {
        List<MemberDTO> memberDTOS = new ArrayList<>();
        String sql = "select * from member ";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setMemberIdx(rs.getInt("member_idx"));
                memberDTO.setUserName(rs.getString("user_name"));
                memberDTO.setUserId(rs.getString("user_id"));
                memberDTO.setMemberAt(rs.getDate("member_at").toLocalDate());
                memberDTOS.add(memberDTO);
            }
        }
        return memberDTOS;
    }

    public MemberDTO authenticateMember(String memberId, String password) throws SQLException {

        MemberDTO memberDTO = new MemberDTO();
        String sql = "select * from member where user_id = ? and password = ? ";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                memberDTO.setMemberIdx(rs.getInt("member_idx"));
                memberDTO.setUserName(rs.getString("user_name"));
                memberDTO.setUserId(rs.getString("user_id"));
                memberDTO.setPassword(rs.getString("password"));
                memberDTO.setMemberAt(rs.getDate("member_at").toLocalDate());
                return memberDTO;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MemberDAO memberDAO = new MemberDAO();
        try {
            System.out.println(memberDAO.authenticateMember("abcd","asd123"));
            System.out.println("로그인 성공");
        } catch (SQLException e) {
            System.out.println("오류가 발생");
        }
    }
}//end of public class
