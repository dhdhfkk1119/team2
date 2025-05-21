package service;

import dao.PhoneDAO;
import dao.SalesDAO;
import dto.PhoneDTO;

import java.security.Provider;
import java.sql.SQLException;
import java.util.List;

public class SalesService {

    private final SalesDAO salesDAO = new SalesDAO();

    // 유효성체크 - 정확히 입력된 값만 확인해야함. 맞는 데이터만 가지고 와야함
    // 판매시스템 -

    public void SalesPhone(int memberIdx, int phoneIdx) throws SQLException {
        if(memberIdx <= 0 || phoneIdx <= 0 ) {
            throw new SQLException("유효한 유저 ID와 휴대폰 ID를 입력해주세요");
        }
        salesDAO.SalesPhone(memberIdx, phoneIdx);
    }

    // 팔린 기종을 검색
    public List<PhoneDTO> getBestSellPhone(String searchPhone) throws SQLException {
        if(searchPhone == null || searchPhone.trim().isEmpty()) {
            throw new SQLException("검색어를 입력해주세요");
        }

        return salesDAO.getBestSellPhone(searchPhone);
    }




    public static void main(String[] args) {
        SalesService salesService = new SalesService();
        try {
            salesService.getBestSellPhone("14");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            salesService.SalesPhone(1,1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
