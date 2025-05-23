package view;

import dao.RecentlyDAO;
import dto.PhoneDTO;
import dto.RecentlyDTO;

import java.sql.SQLException;
import java.util.List;

public class RecentlyService {

    private final RecentlyDAO recentlyDAO = new RecentlyDAO();

    public void addRecentlyViewed(int memberId, int phoneId) {
        try {
            RecentlyDTO recentlyDTO = new RecentlyDTO();
            recentlyDTO.setMemberIdx(memberId);
            recentlyDTO.setPhoneIdx(phoneId);
            recentlyDAO.insert(recentlyDTO);
        } catch (SQLException e) {
            System.err.println("최근 본 상품 추가 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 특정 회원의 최근 본 상품 목록 조회
    public List<PhoneDTO> getRecentlyViewedPhones(int memberId) {
        try {
            return recentlyDAO.getAllRecently(memberId);
        } catch (SQLException e) {
            System.err.println("최근 본 상품 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // 특정 최근 본 상품 기록 삭제
    public void deleteRecentlyViewed(int viewedIdx) {
        try {
            recentlyDAO.deleteViewedIdx(viewedIdx);
        } catch (SQLException e) {
            System.err.println("최근 본 상품 삭제 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RecentlyService recentlyService = new RecentlyService();

        int testMemberId = 1; // 테스트용 회원 ID
        int testPhoneId = 2;  // 테스트용 폰 ID (phone 테이블에 실제로 존재해야 함)
        int testViewedIdx = 10; // 삭제 테스트용 viewed_idx (테이블에 있는 값이어야 함)

        // 1. 최근 본 상품 추가
        System.out.println(" 최근 본 상품 추가 중...");
        recentlyService.addRecentlyViewed(testMemberId, testPhoneId);

        // 2. 최근 본 상품 조회
        System.out.println("\n 최근 본 상품 목록:");
        List<PhoneDTO> phoneList = recentlyService.getRecentlyViewedPhones(testMemberId);
        if (phoneList != null && !phoneList.isEmpty()) {
            for (PhoneDTO phone : phoneList) {
                System.out.println(" - ID: " + phone.getPhoneId()
                        + ", 이름: " + phone.getPhoneName()
                        + ", 가격: " + phone.getPrice()
                        + ", 등록일: " + phone.getCreatedAt());
            }
        } else {
            System.out.println("최근 본 상품이 없습니다.");
        }

//         3. 특정 기록 삭제 (옵션)
//        System.out.println("\n️ viewed_idx = " + testViewedIdx + " 삭제 중...");
//        recentlyService.deleteRecentlyViewed(testViewedIdx);
//        System.out.println("삭제 완료.");

    }
    }



