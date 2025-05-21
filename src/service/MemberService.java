package service;

import dao.MemberDAO;
import dto.MemberDTO;

import java.sql.SQLException;
import java.util.List;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();

    // 회원 가입 하기 기능
    public void registerMember(MemberDTO memberDTO) throws SQLException{
        memberDAO.addMember(memberDTO);
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
