package service;

import dao.ForgotDAO;

import java.sql.SQLException;

public class ForgotService {
    private final ForgotDAO forgotDAO = new ForgotDAO();

    // 1. ID 찾기
    public void findId(String userName) throws SQLException {
        if (userName == null || userName.trim().isEmpty()) {
            throw new SQLException("다시 입력해주세요.");
        }
        forgotDAO.findId(userName);
    }

    // 2. 비밀번호 찾기
    public void findPassword(String userName, String userId) throws Exception {
        if (userName == null || userId == null || userName.trim().isEmpty() || userId.trim().isEmpty()) {
            throw new SQLException("사용자 정보가 없습니다. 다시 입력해주세요.");
        }
        forgotDAO.findPassword(userName, userId);
    }


    // 3. 비밀번호 변경하기

    public void resetPassword(String userId, String password, String newPassword) throws Exception {
        if (userId == null || password == null || userId.trim().isEmpty()
                || password.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            throw new SQLException("잘못입력하셨습니다.");
        }
        forgotDAO.resetPassword(userId,password,newPassword);
    }
}
