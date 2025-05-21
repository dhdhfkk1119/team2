package service;

import dao.MemberDAO;
import dto.MemberDTO;
import until.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();

    // 회원 가입 하기 기능
    public void registerMember(MemberDTO memberDTO) throws SQLException{
        memberDAO.addMember(memberDTO);
    }

    // 중복 확인 하기
    public boolean isUserIdExists(String userId) throws SQLException {
        String sql = "select 1 from member where user_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    // 회원 목록 검색 하기 기능
    public List<MemberDTO> allMember() throws SQLException{
        return memberDAO.allMember();
    }

    // 회원 로그인 검사 하기
    public MemberDTO authenticateMember(String userid ,String password) throws SQLException{
        return memberDAO.authenticateMember(userid,password);
    }

}
