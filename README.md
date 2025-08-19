# 휴대폰 중고거래 시스템

이 프로젝트는 **Java Swing**을 활용하여 개발된 휴대폰중고 거래 사이트입니다. **MySQL 8** 데이터베이스와 연동하여 회원, 중고폰, 판매에 대한 기본적인 관리 기능을 제공하며, 사용자가 직관적으로 시스템을 조작할 수 있는 GUI 환경을 목표로 합니다.

---

## 🚀 주요 기능

### 회원 관리
* **회원 추가**: 새로운 사용자를 등록합니다. 
* **전체 회원 조회**: 시스템에 등록된 모든 회원의 목록을 확인합니다.
* **선택 회원 조회**: 이름 또는 회원아이디로 특정 회원을 검색하여 조회합니다.
* **회원 인증**: 아이디와 비밀번호를 통해 로그인 기능을 제공합니다.

### 판매 관리
* **판매하기 기능**: 판매에 관한 정보를 제공합니다.
* **가장 많이 팔린 기종 조회**: 등록된 모든 휴대폰중 가장 많이 팔린 기종을 검색하는 기능을 제공합니다.
* **구매 목록 만들기**: 회원이 구매했던 목록을 조회하는 기능을 제공합니다.

### 휴대폰 정보 관리
* **새 폰을 데이터 베이스에 추가하는 기능**: 새로운 휴대폰에 대한 정보를 추가합니다.
* **모든 목록 조회**: 휴대폰에 관한 모든 정보를 조회 합니다.
* **기종 조회**: 특정 휴대폰을 조회하는 기능을 제공합니다.


---

## 🛠️ 기술 스택

* **언어**: Java 21
* **데이터베이스**: MySQL 8
* **데이터베이스 연결**: JDBC
* **의존성 관리/코드 간소화**: Lombok
* **개발 환경**: IntelliJ IDEA
* **버전 관리**: Git

---

## 📦 패키지 구조

```
src/main/java
├── util
│   └── DatabaseUtil.java     // 데이터베이스 연결 유틸리티
├── dto
│   ├── MemberDTO.java         // 회원 데이터 객체 (@Getter, @Setter 등 Lombok 적용)
│   ├── SalesDTO.java          // 판매 기능 객체 (@Getter, @Setter 등 Lombok 적용)
│   └── PhoneDTO.java          // 휴대폰 정보 객체 (@Getter, @Setter 등 Lombok 적용)
├── dao
│   ├── MemberDAO.java      // 회원 정보 접근 객체 (CRUD)
│   ├── SalesDAO.java       // 판매 기능 접근 객체 (CRUD)
│   └── PhoneAO.java          // 휴대폰 정보 접근 객체 (CRUD)
├── service
│   ├── MemebrService.java  // 회원 정보 관련 비즈니스 로직
│   ├── SalesService.java   // 판매 기능 관련 비즈니스 로직
│   └── PhoneService.java    // 휴대폰 정보 관련 비즈니스 로직
├── view                     //view
│   ├── UsedPhoneView.java    
└── Main.java                 // 프로그램 진입점
```

---

## 📅 개발 일정

* **Day 1: 설정 및 설계**
    * GitHub 리포지토리 생성 및 브랜치 전략 수립.
    * MySQL 데이터베이스 및 테이블 스키마 생성.
    * DTO 및 DAO  설계 (ERD 다이어그램 포함).
* **Day 2: DAO 및 DTO 구현**
    * `MemberDTO`, `SalesDTO`, `PhoneDTO` DTO 클래스 구현 (Lombok 적용).
    * `MemberDAO`, `SalesDAO`, `PhoneDAO` 클래스에 기본 CRUD 기능 구현 및 테스트.
    * Swing UI 초기 구현.
* **Day 3: Service 및 View 개발**
    * Service 레이어에서 비즈니스 로직 구현 및 DAO와 연동.
    * Swing으로 각 화면 UI (로그인, 회원가입, 목록 등) 완성 및 기능 통합.
    * 추가기능 구현.
* **Day 4: 테스트 및 마무리**
    * 최종 기능 테스트 및 디버깅.
    * UI 개선 및 코드 리뷰.
    * Git `main` 브랜치로 최종 병합.

---
