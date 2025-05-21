package service;

import dao.PhoneDAO;
import dto.PhoneDTO;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.awt.*;

public class PhoneService {

    private final PhoneDAO phoneDAO = new PhoneDAO();

    // 중고폰을 추가하는 서비스
    public void addPhone(PhoneDTO phoneDTO) throws SQLException {
        // 입력 값 유효성 검사
        if(phoneDTO.getPhoneName() == null || phoneDTO.getPhoneState() == null
                || phoneDTO.getPhoneName().trim().isEmpty() || phoneDTO.getPhoneState().trim().isEmpty()  ) {
            throw new SQLException("휴대폰 이름과 상태는 필수 입력 항목입니다.");
        }
        // 유효성 검사 통과후 PhoneDAO 에게 일을 협력 요청 한다.
        phoneDAO.addPhone(phoneDTO);
    }

    // 중고폰 전체 조회 하는 서비스
    public List<PhoneDTO> getAllPhone() throws SQLException {
        return phoneDAO.getAllPhone();
    }

    // 특정 기종만 조회 하는 서비스
    public List<PhoneDTO> searchPhoneByName(String PhoneName) throws SQLException {
        // 입력값 유효성 검사
        if(PhoneName == null || PhoneName.trim().isEmpty()) {
            throw new SQLException("검색 제목을 입력해주세요");
        }
        return phoneDAO.searchPhoneName(PhoneName);
    }

}
