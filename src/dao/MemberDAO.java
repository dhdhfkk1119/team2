package dao;

/*
 * 회원 가입
 * 회원 가입을 할때 이름 아이디 비밀번호를 정함
 * 가입을 하면 고유번호가 정해지고 가입 시간이 나옴
 */

import dto.MemberDTO;
import until.DataBaseUtil;

import java.lang.reflect.Member;
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
                "values ('?', '?', '?', current_date) ";
        try(Connection conn = DataBaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, memberDTO.getUserName());
            pstmt.setString(2, memberDTO.getUserId());
            pstmt.setString(3, memberDTO.getPassword());
            pstmt.executeQuery();
        }
    }
    public List<MemberDTO> AllMember() throws SQLException {
        List<MemberDTO> memberDTOS = new ArrayList<>();
        String sql = "select * from member ";
        try(Connection conn = DataBaseUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setUserName(rs.getString("user_name"));
                memberDTO.setUserId(rs.getString("user_id"));
                memberDTO.setPassword(rs.getString("password"));
                memberDTO.setMemberAt(rs.getDate("member_at").toLocalDate());
                memberDTOS.add(memberDTO);
            }
        }
        return memberDTOS;
    }

    public MemberDTO authenticateMember(String memberId) throws SQLException {

        String sql = "SELECT * FROM member ";
            try(Connection conn = DataBaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    MemberDTO memberDTO = new MemberDTO();
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
//        try {
//            memberDAO.AllMember();
//            for (int i =0;i<memberDAO.AllMember().size();i++){
//                System.out.println(memberDAO.AllMember().get(i));
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try(memberDAO.addMember(new MemberDTO("김지훈", "jihun", "jh1234" "20121213"))){
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }



    }//end of main

    }//end of public class
