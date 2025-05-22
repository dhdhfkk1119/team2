package view;

import dto.MemberDTO;
import dto.PhoneDTO;
import service.MemberService;
import service.PhoneService;
import service.SalesService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UsedPhoneView extends JFrame implements ActionListener {

    private final MemberService memberService = new MemberService();
    private final PhoneService phoneService = new PhoneService();
    private final SalesService salesService = new SalesService();

    // 로그인시 회원 ID 값을 저장(인증정보)
    private Integer userId = null;

    JButton homeBtn; // 메인으로 가는 버튼

    // View 버튼
    JButton loginViewBtn; //  로그인 View 버튼
    JButton registerViewBtn; // 회원가입 View 버튼
    JButton phoneAllViewBtn; // 중고폰 View 버튼
    JButton phoneSalseViewBtn; // 폰 구매하기 View 버튼
    JButton phoneSearchViewBtn; // 검색으로 가는 View 버튼
    JButton phoneBestSalesViewBtn; // 가장 많이 팔린 View 버튼
    JButton phoneAddViewBtn; // 중고폰 등록하기 View 버튼
    JButton buyPhoneListViewBtn; // 내가 구매한 목록

    // 실제 기능 버튼
    JButton loginSubmitBtn; // 로그인 버튼
    JButton logoutBtn; // 로그아웃 버튼
    JButton registerSubmitBtn; // 회원가입 버튼
    JButton phoneNameSubmitBtn; // 이름으로 휴대폰 기종 검색 버튼
    JButton buyPhoneSubmitBtn; // 구매 하기 버튼
    JButton phoneBestSalesSubmitBtn; // 가장 많이 팔린 submit 버튼
    JButton phoneAddSubmitBtn; // 중고폰 등록하기 submit 버튼

    // 회원 가입 및 로그인 input
    JTextField useridInput;
    JTextField passwordInput;
    JTextField userNameInput;

    // 원하는 기종 검색 input 값
    JTextField phoneNameInput;

    // 가장 많이 팔린 기종 검색 input 값
    JTextField phoneBestSalesInput;

    // 구매 하기 원하는 기종 input 값
    JTextField buyPhoneInput;

    // 중고폰 등록하기
    JTextField phoneAddNameInput;
    JTextField priceInput;
    JTextField phoneStateInput;
    JTextField quantityInput;


    // 기본 데이터 초기화
    public UsedPhoneView() {
        initData();
        setInitLayOut();
        addEventListener();
    }

    // 기본 데이터 설정
    private void initData() {
        setSize(1000, 600);    //사이즈는 640*480
        setResizable(false);    //사이즈 재조정 불가능
        setLocationRelativeTo(null);    //창이 가운데에 뜨도록 함
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //창을 끄면 프로그램을 종료

        logoutBtn = new JButton("로그아웃");
        homeBtn = new JButton("메인으로");


        // 회원 가입
        registerViewBtn = new JButton("회원가입");
        registerSubmitBtn = new JButton("회원 가입");

        // 로그인 하기
        loginViewBtn = new JButton("로그인");
        loginSubmitBtn = new JButton("로그인확인");

        // 중고폰 등록하기
        phoneAddSubmitBtn = new JButton("등록하기");
        phoneAddViewBtn = new JButton("중고폰 등록");

        // 중고 폰 검색
        phoneAllViewBtn = new JButton("전체 목록 조회");
        phoneSearchViewBtn = new JButton("중고폰 검색");
        phoneNameSubmitBtn = new JButton("검색하기");

        // 제일 많이 팔린 중고폰 검색
        phoneBestSalesViewBtn = new JButton("현재 판매 순위");
        phoneBestSalesSubmitBtn = new JButton("검색하기");

        // 중고폰 구매하기
        phoneSalseViewBtn = new JButton("중고폰 구매");
        buyPhoneSubmitBtn = new JButton("구매하기");

        // 자신이 구매한 폰 목록보기
        buyPhoneListViewBtn = new JButton("구매목록보기");


        // 실직적으로 데이터가 담기는 Input
        useridInput = new JTextField(20);
        passwordInput = new JTextField(20);
        userNameInput = new JTextField(20);
        phoneNameInput = new JTextField(20);
        buyPhoneInput = new JTextField(20);
        phoneBestSalesInput = new JTextField(20);

        // 중고폰 등록 input
        phoneAddNameInput = new JTextField(20);
        priceInput = new JTextField(20);
        phoneStateInput = new JTextField(20);
        quantityInput = new JTextField(20);


    }

    // 기본 메인 화면
    private void setInitLayOut() {
        // 현재 목차 띄워 주기
        JLabel label = new JLabel("\uD83E\uDEA7 여기는 중고폰을 등록하고 구매하는 사이트입니다 \uD83E\uDEA7"); // 제일 상단에 띄워줌
        label.setFont(new Font("Serif", Font.BOLD, 20));
        // 기존 메인 화면
        Container container = getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        container.add(label, BorderLayout.NORTH);

        if (userId == null) {
            container.add(loginViewBtn); // 로그인 버튼
            container.add(registerViewBtn); // 회원 가입 버튼
        } else {
            container.add(phoneAddViewBtn); // 핸드폰 등록하기
            container.add(buyPhoneListViewBtn); // 자신이 구매한 핸드폰 보기
            container.add(phoneAllViewBtn); // 전체 기종 검색
            container.add(phoneSearchViewBtn); // 기종 검색
            container.add(phoneSalseViewBtn); // 판매 목록
            container.add(phoneBestSalesViewBtn); // 판매 순위
            container.add(logoutBtn); // 로그아웃 버튼
        }
        setVisible(true);    //창을 눈에 보이도록 함
        revalidate();
        repaint();
    }

    // 로그인 기능 --- O
    private void loginView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 아이디 패널
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel idLabel = new JLabel("아이디 : ");
        useridInput.setPreferredSize(new Dimension(200, 50));
        idPanel.add(idLabel);
        idPanel.add(useridInput);

        // 비밀번호 패널
        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel pwLabel = new JLabel("비밀번호 : ");
        passwordInput.setPreferredSize(new Dimension(200, 50));
        pwPanel.add(pwLabel);
        pwPanel.add(passwordInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginSubmitBtn.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(loginSubmitBtn);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(homePanel);
        container.add(idPanel);
        container.add(Box.createVerticalStrut(10)); // 아이디-비밀번호 간격
        container.add(pwPanel);
        container.add(Box.createVerticalStrut(15)); // 비밀번호-버튼 간격
        container.add(btnPanel);
        revalidate();
        repaint();
    }

    // 회원가입 기능 --- O
    private void registerView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 회원 이름 패널
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("회원 이름 : ");
        userNameInput.setPreferredSize(new Dimension(175, 40));
        namePanel.add(nameLabel);
        namePanel.add(userNameInput);

        // 아이디 패널
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel idLabel = new JLabel("아이디 : ");
        useridInput.setPreferredSize(new Dimension(175, 40));
        idPanel.add(idLabel);
        idPanel.add(useridInput);

        // 비밀번호 패널
        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel pwLabel = new JLabel("비밀번호 : ");
        passwordInput.setPreferredSize(new Dimension(175, 40));
        pwPanel.add(pwLabel);
        pwPanel.add(passwordInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerSubmitBtn.setPreferredSize(new Dimension(175, 40));
        btnPanel.add(registerSubmitBtn);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(homePanel);
        container.add(namePanel);
        container.add(idPanel);
        container.add(Box.createVerticalStrut(10)); // 아이디-비밀번호 간격
        container.add(pwPanel);
        container.add(Box.createVerticalStrut(15)); // 비밀번호-버튼 간격
        container.add(btnPanel);
        revalidate();
        repaint();
    }

    // 회원가입 submit 기능 --- O
    private void registerSubmit() {
        String userName = userNameInput.getText().trim();
        String userId = useridInput.getText().trim();
        String password = passwordInput.getText().trim();

        if (userName.isEmpty()) {
            System.out.println("이름을 입력해주시기 바랍니다");
            return;
        }
        if (userId.isEmpty()) {
            System.out.println("아이디를 입력해 주시기 바랍니다");
            return;
        }
        if (password.isEmpty()) {
            System.out.println("비밀번호를 입력해 주시기 바랍니다");
            return;
        }

        try {
            // 아이디 중복검사 
            if (memberService.isUserIdExists(userId)) {
                System.out.println("중복되는 아이디입니다.");
                return;
            }

            // 아이디가 없을 경우 -> 진행
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUserName(userName);
            memberDTO.setUserId(userId);
            memberDTO.setPassword(password);
            // 값을 넣음 
            memberService.registerMember(memberDTO);
            // 이후 이동
            setInitLayOut();
            System.out.println("회원 가입 성공");
            revalidate();
            repaint();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그인 하기 버튼
    private void loginSubmit() {
        if (useridInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty()) {
            System.out.println("아이디 또는 비밀번호를 입력해주시기 바랍니다");
        } else {
            MemberDTO memberDTO = null;
            try {
                memberDTO = memberService.authenticateMember(useridInput.getText(), passwordInput.getText());
                if (memberDTO != null) {
                    userId = memberDTO.getMemberIdx();
                    System.out.println("로그인 성공");
                    RePaint();
                    setInitLayOut();
                } else {
                    System.out.println("아이디 또는 비밀번호가 틀립니다");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    // 로그아웃 기능 --- O
    private void logOutView() {
        userId = null;
        setInitLayOut();
        revalidate();
        repaint();
    }

    // 중고폰 등록하기 VIEW --- o
    private void phoneAddView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 폰 기종 명
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("폰 기종 명 : ");
        phoneAddNameInput.setPreferredSize(new Dimension(175, 40));
        namePanel.add(nameLabel);
        namePanel.add(phoneAddNameInput);

        // 폰 가격
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel priceLabel = new JLabel("폰 가격 : ");
        priceInput.setPreferredSize(new Dimension(175, 40));
        pricePanel.add(priceLabel);
        pricePanel.add(priceInput);

        // 폰 상태
        JPanel phoneStatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel phoneStateLabel = new JLabel("폰 상태 : ");
        phoneStateInput.setPreferredSize(new Dimension(175, 40));
        phoneStatePanel.add(phoneStateLabel);
        phoneStatePanel.add(phoneStateInput);

        // 폰 수량
        JPanel phoneQuantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel phoneQuantityLabel = new JLabel("폰 수량 : ");
        quantityInput.setPreferredSize(new Dimension(175, 40));
        phoneQuantityPanel.add(phoneQuantityLabel);
        phoneQuantityPanel.add(quantityInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        phoneAddSubmitBtn.setPreferredSize(new Dimension(175, 40));
        btnPanel.add(phoneAddSubmitBtn);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(homePanel);
        container.add(namePanel);
        container.add(pricePanel);
        container.add(Box.createVerticalStrut(10)); // 아이디-비밀번호 간격
        container.add(phoneStatePanel);
        container.add(Box.createVerticalStrut(15)); // 비밀번호-버튼 간격
        container.add(phoneQuantityPanel);
        container.add(btnPanel);
        revalidate();
        repaint();
    }

    // 중고폰 등록하기 Submit
    private void phoneAddSubmit() {
        if (phoneAddNameInput.getText().trim().isEmpty()) {
            System.out.println("값을 입력해 주시기 바랍니다");
            return;
        }
        if (priceInput.getText().trim().isEmpty()) {
            System.out.println("가격을 입력해 주시기 바랍니다");
            return;
        }
        if (phoneStateInput.getText().trim().isEmpty()) {
            System.out.println("상태를 입력해 주시기 바랍니다");
            return;
        }
        if (quantityInput.getText().trim().isEmpty()) {
            System.out.println("수량을 입력해 주시기 바랍니다");
            return;
        }

        try {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setPhoneName(phoneAddNameInput.getText());
            phoneDTO.setPrice(Integer.parseInt(priceInput.getText()));
            phoneDTO.setPhoneState(phoneStateInput.getText());
            phoneDTO.setQuantity(Integer.parseInt(quantityInput.getText()));
            phoneDTO.setMemberId(userId);
            phoneService.addPhone(phoneDTO);
            System.out.println("정상적으로 등록되었습니다 :" + phoneDTO.getPhoneId());
            RePaint();
            setInitLayOut();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 자신이 구매한 폰 목록 보기
    private void buyPhoneListView() {
        Container container = getContentPane();
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 맨위에 목차 띄워주기
        JPanel historyPanel = new JPanel();
        JLabel HistoryLabel = new JLabel("\uD83E\uDEA7 현재 구매한 휴대폰 목록입니다 \uD83E\uDEA7"); // 제일 상단에 띄워줌
        HistoryLabel.setFont(new Font("Serif", Font.BOLD, 20));
        historyPanel.add(HistoryLabel, BorderLayout.CENTER);

        // 구매한 핸드폰 목옥을 담는 패널
        JPanel buyPhonePanel = new JPanel();
        buyPhonePanel.setLayout(new BoxLayout(buyPhonePanel, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위한 정렬 설정
        buyPhonePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 리스트 받아오기
        List<MemberDTO> memberDTOS = null;
        try {
            memberDTOS = memberService.allMember();

            for (MemberDTO student : memberDTOS) {
                JLabel label = new JLabel(student.toString());
                label.setAlignmentX(Component.CENTER_ALIGNMENT); // 각 라벨 중앙 정렬
                buyPhonePanel.add(label);
                buyPhonePanel.add(Box.createVerticalStrut(10)); // 라벨 사이 여백
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 전체를 가운데에 배치
        container.setLayout(new BorderLayout());
        container.add(historyPanel, BorderLayout.NORTH);
        container.add(homePanel, BorderLayout.NORTH);
        container.add(HistoryLabel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // 전체 휴대폰 검색 --- O
    private void getPhoneAllView() {
        Container container = getContentPane();
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 맨위에 목차 띄워주기
        JPanel historyPanel = new JPanel();
        JLabel HistoryLabel = new JLabel("\uD83E\uDEA7 현재 등록된 모든 중고폰 목록입니다. \uD83E\uDEA7"); // 제일 상단에 띄워줌
        HistoryLabel.setFont(new Font("Serif", Font.BOLD, 20));
        historyPanel.add(HistoryLabel, BorderLayout.CENTER);

        // 학생 목록을 담을 패널 (세로 정렬)
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위한 정렬 설정
        studentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 리스트 받아오기
        List<MemberDTO> memberDTOS = null;
        try {
            memberDTOS = memberService.allMember();
            for (MemberDTO student : memberDTOS) {
                JLabel label = new JLabel(student.toString());
                label.setAlignmentX(Component.CENTER_ALIGNMENT); // 각 라벨 중앙 정렬
                studentPanel.add(label);
                studentPanel.add(Box.createVerticalStrut(10)); // 라벨 사이 여백
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 전체를 가운데에 배치
        container.setLayout(new BorderLayout());
        container.add(historyPanel, BorderLayout.NORTH);
        container.add(homePanel, BorderLayout.NORTH);
        container.add(HistoryLabel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // 중고폰 검색 기능
    private void getPhoneSearchView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 기종명 검색 기능
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel phoneLabel = new JLabel("기종명 검색 : ");
        phoneNameInput.setPreferredSize(new Dimension(175, 40));
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneNameInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        phoneNameSubmitBtn.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(phoneNameSubmitBtn);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(homePanel);
        container.add(phonePanel);
        container.add(Box.createVerticalStrut(15));
        container.add(btnPanel);
        revalidate();
        repaint();
    }

    // 기종명 검색하기 버튼
    private void searchPhoneSubmit() {
        if (phoneNameInput.getText() == null || phoneNameInput.getText().trim().isEmpty()) {
            System.out.println("값을 입력해주시기 바랍니다");
        }

        Container container = getContentPane();
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        container.add(homePanel, BorderLayout.NORTH);

        // 학생 목록을 담을 패널 (세로 정렬)
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위한 정렬 설정
        studentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 리스트 받아오기
        List<PhoneDTO> phoneDTO = null;
        try {
            phoneDTO = phoneService.searchPhoneByName(phoneNameInput.getText());
            for (PhoneDTO phoneDTOList : phoneDTO) {
                JLabel label = new JLabel(phoneDTOList.toString());
                label.setAlignmentX(Component.CENTER_ALIGNMENT); // 각 라벨 중앙 정렬
                studentPanel.add(label);
                studentPanel.add(Box.createVerticalStrut(10)); // 라벨 사이 여백
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 스크롤 필요하면 JScrollPane으로 감싸기 (옵션)
        JScrollPane scrollPane = new JScrollPane(studentPanel);
        scrollPane.setBorder(null);

        // 전체를 가운데에 배치
        container.setLayout(new BorderLayout());
        container.add(homePanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();

    }

    // 가장 많이 팔린 중고폰 검색  VIEW
    private void getBestSellPhoneView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 기종명 검색 기능
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel phoneLabel = new JLabel("가장 많이 팔린 기종 검색 : ");
        phoneBestSalesInput.setPreferredSize(new Dimension(175, 40));
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneBestSalesInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        phoneBestSalesSubmitBtn.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(phoneBestSalesSubmitBtn);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(homePanel);
        container.add(phonePanel);
        container.add(Box.createVerticalStrut(15));
        container.add(btnPanel);
        revalidate();
        repaint();
    }

    // 가장 많이 팔린 중고폰 검색 버튼 submit
    private void getBestPhoneSubmit() {
        if (phoneBestSalesInput.getText() == null || phoneBestSalesInput.getText().trim().isEmpty()) {
            System.out.println("정확한 기종명 또는 공백을 입력하지 마세요!!");
        }
        try {
            Container container = getContentPane();
            JPanel homePanel = new JPanel();
            homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
            container.add(homePanel, BorderLayout.NORTH);

            // 학생 목록을 담을 패널 (세로 정렬)
            JPanel studentPanel = new JPanel();
            studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

            // 중앙 정렬을 위한 정렬 설정
            studentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // 리스트 받아오기
            List<PhoneDTO> phoneDTO = salesService.getBestSellPhone(phoneBestSalesInput.getText());

            for (PhoneDTO phoneDTOList : phoneDTO) {
                JLabel label = new JLabel(phoneDTOList.toString());
                label.setAlignmentX(Component.CENTER_ALIGNMENT); // 각 라벨 중앙 정렬
                studentPanel.add(label);
                studentPanel.add(Box.createVerticalStrut(10)); // 라벨 사이 여백
            }

            // 스크롤 필요하면 JScrollPane으로 감싸기 (옵션)
            JScrollPane scrollPane = new JScrollPane(studentPanel);
            scrollPane.setBorder(null);

            // 전체를 가운데에 배치
            container.setLayout(new BorderLayout());
            container.add(homePanel, BorderLayout.NORTH);
            container.add(scrollPane, BorderLayout.CENTER);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        revalidate();
        repaint();
    }

    // 휴대폰 구매하기 기종 번호 VIEW
    private void buyPhoneView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 구매하기 휴대폰 번호
        JPanel buyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel buyLabel = new JLabel("휴대폰 기종 번호 : ");
        buyPhoneInput.setPreferredSize(new Dimension(175, 40));
        buyPanel.add(buyLabel);
        buyPanel.add(buyPhoneInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buyPhoneSubmitBtn.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(buyPhoneSubmitBtn);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(homePanel, BorderLayout.NORTH);
        container.add(Box.createVerticalStrut(10)); // 아이디-비밀번호 간격
        container.add(buyPanel);
        container.add(Box.createVerticalStrut(15)); // 비밀번호-버튼 간격
        container.add(btnPanel);
        revalidate();
        repaint();

    }

    // 휴대폰 버튼 눌러서 구매하기
    private void buyPhoneSubmit() {
        String input = buyPhoneInput.getText().trim();

        if (input.isEmpty()) {
            System.out.println("값을 입력해주시기 바랍니다");
            buyPhoneInput.setText("");
        }
        try {
            int buyIdx = Integer.parseInt(input);
            salesService.SalesPhone(userId, buyIdx);
            System.out.println("구매 성공");
            RePaint();
            setInitLayOut();
            buyPhoneInput.setText("");
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해주세요.");
        } catch (SQLException e) {
            System.out.println("존재하지 않는 상품입니다.");
            throw new RuntimeException(e);
        }
    }

    // 버튼에 따라 이벤트
    private void addEventListener() {
        logoutBtn.addActionListener(this);
        registerViewBtn.addActionListener(this);
        phoneSearchViewBtn.addActionListener(this);
        phoneSalseViewBtn.addActionListener(this);
        phoneAllViewBtn.addActionListener(this);
        loginViewBtn.addActionListener(this);
        loginSubmitBtn.addActionListener(this);
        registerSubmitBtn.addActionListener(this);
        phoneNameSubmitBtn.addActionListener(this);
        buyPhoneSubmitBtn.addActionListener(this);
        phoneBestSalesViewBtn.addActionListener(this);
        phoneBestSalesSubmitBtn.addActionListener(this);
        phoneAddSubmitBtn.addActionListener(this);
        phoneAddViewBtn.addActionListener(this);
        homeBtn.addActionListener(this);
    }

    // 메인 페이지로 이동
    private void homeBtnApi() {
        // 각자 값을 초기화
        buyPhoneInput.setText("");
        phoneNameInput.setText("");
        useridInput.setText("");
        passwordInput.setText("");
        userNameInput.setText("");
        phoneBestSalesInput.setText("");

        // 휴대폰 등록하기 초기화
        phoneAddNameInput.setText("");
        priceInput.setText("");
        quantityInput.setText("");
        phoneStateInput.setText("");
        RePaint();
        setInitLayOut();
    }

    // 해당 버튼에 따라 이벤트 발생
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeBtn) {
            homeBtnApi();
            System.out.println(userId);
        }
        if (e.getSource() == loginViewBtn) {
            RePaint();
            loginView();
        } else if (e.getSource() == logoutBtn) {
            RePaint();
            logOutView();
        } else if (e.getSource() == loginSubmitBtn) {
            loginSubmit();
        } else if (e.getSource() == registerViewBtn) {
            RePaint();
            registerView();
        } else if (e.getSource() == phoneAllViewBtn) {
            RePaint();
            getPhoneAllView();
        } else if (e.getSource() == phoneSearchViewBtn) {
            RePaint();
            getPhoneSearchView();
        } else if (e.getSource() == phoneSalseViewBtn) {
            RePaint();
            buyPhoneView();
        } else if (e.getSource() == phoneNameSubmitBtn) {
            searchPhoneSubmit();
        } else if (e.getSource() == buyPhoneSubmitBtn) {
            buyPhoneSubmit();
        } else if (e.getSource() == registerSubmitBtn) {
            registerSubmit();
        } else if (e.getSource() == phoneBestSalesViewBtn) {
            RePaint();
            getBestSellPhoneView();
        } else if (e.getSource() == phoneBestSalesSubmitBtn) {
            getBestPhoneSubmit();
        } else if (e.getSource() == phoneAddViewBtn) {
            RePaint();
            phoneAddView();
        } else if (e.getSource() == phoneAddSubmitBtn) {
            phoneAddSubmit();
        } else if (e.getSource() == buyPhoneListViewBtn) {
            repaint();
            buyPhoneListView();
        }
    }

    private void RePaint() {
        Container container = getContentPane();
        container.removeAll();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new UsedPhoneView();
    }

}
