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


    String checkSql = "select * from member where user_id = ?";

    public void addMember(MemberDTO memberDTO) throws SQLException {
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            pstmt.setString(1, memberDTO.getUserId());
            ResultSet resultSet = pstmt.executeQuery();
            //만약 위에서 반환한 resultSet의 값이 하나도 없으면 중복된 아이디가 없는 것 이므로 값을 넣고, 아니면 중복아이디라고 출력
            if(!resultSet.next()){
                String insertSQL = "insert into member(user_name,user_id,password,member_at) values " +
                        "(?,?,?,current_date)";
                PreparedStatement insertpstmt = conn.prepareStatement(insertSQL);
                insertpstmt.setString(1, memberDTO.getUserName());
                insertpstmt.setString(2, memberDTO.getUserId());
                insertpstmt.setString(3, memberDTO.getPassword());
                insertpstmt.executeUpdate();
            }else {
                System.out.println("현재 등록된 아이디가 있습니다");
            }

        }
    }

    public List<MemberDTO> allMember() throws SQLException {
        List<MemberDTO> memberDTOS = new ArrayList<>();
        String sql = "select member_idx,user_name,user_id,member_at from member ";
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

    public boolean isUserIdExists(String userId) throws SQLException {
        String sql = "select 1 from member where user_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}//end of public class
