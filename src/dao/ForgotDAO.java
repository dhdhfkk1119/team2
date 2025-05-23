package dao;

import dto.MemberDTO;
import utill.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotDAO {

//-- 1. ID찾기랑
//-- 2. 비밀번호랑 찾기
//-- 3. 수정하는 메서드

//1. ID를 찾으려면 뭐가 필요한가
//
//    user_name을 조회한다. 쿼리를 확인해보자.
//    그리고 user_name이 맞으면
//    ID를 알려준다.(user_id)

    public void findId(String userName) throws SQLException {
        // 일단 조회를 한다.
        String query = "select * from member where user_name = ? ";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement checkpstmt = conn.prepareStatement(query)) {
            checkpstmt.setString(1, userName);
            ResultSet rs = checkpstmt.executeQuery();
            // 이름이 있을 경우

            if (rs.next()) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setUserId(rs.getString("user_id"));
                String userId = memberDTO.getUserId();
                System.out.println("당신의 ID는 " + userId + " 입니다.");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


// 2. 비밀번호 찾기
//    비밀번호를 찾으려면 우선
//    회원이 있는지 부터 확인한다.
//            -->회원을 확인하려면 ID랑 이름을 입력해서 맞는지 체크 1차로

    public void findPassword(String userName, String userId) throws SQLException {
        String checkquery = "select * from member where user_name = ? and user_id = ? ";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkquery)) {
            checkPstmt.setString(1, userName);
            checkPstmt.setString(2, userId);
            ResultSet rs2 = checkPstmt.executeQuery();

            if (rs2.next()) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setUserName(rs2.getString("user_name"));
                memberDTO.setUserId(rs2.getString("user_id"));
                memberDTO.setPassword(rs2.getString("password"));
                String password = memberDTO.getPassword();
                System.out.println("당신의 Password는 " + password + " 입니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//3. 비밀번호 수정하기
//    비밀번호를 수정하려면 본인확인이 되어야함.
//    ID 비밀번호 인증체크 -->

    String checkquery = "select * from member where user_id = ? and password = ? ";

    public void resetPassword(String userId, String password, String newPassword) throws SQLException {

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkquery)) {
            checkPstmt.setString(1, userId);
            checkPstmt.setString(2, password);
            ResultSet rs3 = checkPstmt.executeQuery();
            // 비밀번호 수정전에 ID 비밀번호 입력해서 확인

            if (rs3.next()) {
                String updatequery = "update member set password = ? " +
                        "where user_id = ? ";
                try (PreparedStatement updatepstmt = conn.prepareStatement(updatequery)) {
                    updatepstmt.setString(1, newPassword);
                    updatepstmt.setString(2, userId);

                    updatepstmt.executeUpdate();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 테스트해보기
    public static void main(String[] args) {

        // ID쿼리 조회 해보기
        ForgotDAO forgotDTO = new ForgotDAO();
        try {
            forgotDTO.resetPassword("1", "1", "123456");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}
