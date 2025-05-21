package dao;

/*
 * 회원 가입
 * 회원 가입을 할때 이름 아이디 비밀번호를 정함
 * 가입을 하면 고유번호가 정해지고 가입 시간이 나옴
 */

import until.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    public void addMember(String userName, String userId, String password) throws SQLException {

        String Sql = "select * from member ";
        try(Connection conn = DataBaseUtil.getConnection()){
            PreparedStatement Pstmt = conn.prepareStatement(Sql);
            ResultSet set = Pstmt.executeQuery();
            if (set.next()){

            }else {
                String insertSql = "insert into member (user_name, user_id, password, member_at) \n" +
                        "values ('?', '?', '?', ?)";
            }

        }

    }

}//end of public class
