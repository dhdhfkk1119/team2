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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UsedPhoneView extends JFrame implements ActionListener {

    private final MemberService memberService = new MemberService();
    private final PhoneService phoneService = new PhoneService();
    private final SalesService salesService = new SalesService();

    // 로그인시 회원 ID 값을 저장(인증정보)
    private Integer userId = null;
    private String userName = null;
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
    JTextField rePasswordInput;

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
        rePasswordInput = new JTextField(20);
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

        Container container = getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        // 상태 라벨
        JLabel label = new JLabel("\uD83E\uDEA7 여기는 중고폰을 등록하고 구매하는 사이트입니다 \uD83E\uDEA7", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // 위쪽 여백
        container.add(label, BorderLayout.NORTH);

        // 센터 패널에 라벨(환영 메시지) + 버튼 묶음
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        container.add(centerPanel, BorderLayout.CENTER);

        if (userId != null) {
            // 로그인한 사용자에게 환영 메시지 표시
            JLabel welcomeLabel = new JLabel(userName + "님 환영합니다", SwingConstants.CENTER);
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 16));
            welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // 위아래 여백
            centerPanel.add(welcomeLabel);
        }

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        if (userId == null) {
            // 로그인 전 버튼 - 가운데 정렬
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
            buttonPanel.add(loginViewBtn);
            buttonPanel.add(registerViewBtn);
        } else {
            // 로그인 후 버튼 - 3줄 구성
            buttonPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 20, 10, 20); // 버튼 간격

            // 첫 줄
            gbc.gridx = 0;
            gbc.gridy = 0;
            buttonPanel.add(phoneAddViewBtn, gbc);
            gbc.gridx = 1;
            buttonPanel.add(buyPhoneListViewBtn, gbc);
            gbc.gridx = 2;
            buttonPanel.add(phoneAllViewBtn, gbc);

            // 둘째 줄
            gbc.gridx = 0;
            gbc.gridy = 1;
            buttonPanel.add(phoneSearchViewBtn, gbc);
            gbc.gridx = 1;
            buttonPanel.add(phoneSalseViewBtn, gbc);
            gbc.gridx = 2;
            buttonPanel.add(phoneBestSalesViewBtn, gbc);

            // 셋째 줄 - 로그아웃 버튼 중앙
            gbc.gridx = 1;
            gbc.gridy = 2;
            buttonPanel.add(logoutBtn, gbc);
        }

        setVisible(true);
        revalidate();
        repaint();
    }

    // 에러 발생시 새로운 Frame 생성
    private void errorMessageFrame(String errorMessage) {
        JFrame errorFrame = new JFrame("오류");
        errorFrame.setSize(400, 200);
        errorFrame.setLocationRelativeTo(null); // 화면 중앙에 위치
        errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기만 하고 앱은 종료하지 않음

        JPanel panel = new JPanel();
        panel.setBackground(Color.PINK); // 배경색
        panel.setLayout(new BorderLayout());

        JButton closeBtn = new JButton("닫기");
        closeBtn.setSize(100, 50);
        closeBtn.setLocation(140, 100);
        panel.add(closeBtn);

        JLabel messageLabel = new JLabel(errorMessage, SwingConstants.CENTER);
        messageLabel.setFont(new Font("굴림", Font.BOLD, 16));
        panel.add(messageLabel, BorderLayout.CENTER);

        errorFrame.add(panel);
        errorFrame.setVisible(true);
        errorFrame.setAlwaysOnTop(true);
        closeBtn.addActionListener(e -> errorFrame.setVisible(false));

    }
    
    // 현재 페이지에 위치를 보여줌
    private JPanel currentLocation(String message) {
        // 라벨 생성 및 설정
        JLabel titleLabel = new JLabel(message, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // 패널에 라벨 추가 및 레이아웃 설정
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    // 로그인 기능 --- O
    private void loginView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));


        // 중앙 정렬을 위해 JLabel을 감쌀 JPanel 생성
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(currentLocation("✅ 현재 페이지는 로그인 하는 페이지 입니다 ✅"));

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

        loginSubmitBtn.addActionListener(e -> loginSubmit());

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가\
        container.add(titlePanel); // 제목을 가장 위에 추가
        container.add(spacer);     // 여백
        container.add(homePanel);
        container.add(idPanel);
        container.add(Box.createVerticalStrut(10)); // 아이디-비밀번호 간격
        container.add(pwPanel);
        container.add(Box.createVerticalStrut(15)); // 비밀번호-버튼 간격
        container.add(btnPanel);
        revalidate();
        repaint();
    }

    // 로그인 하기 Submit 버튼 --- O
    private void loginSubmit() {
        if (useridInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty()) {
            errorMessageFrame("값을 입력해주시기 바랍니다");
        } else {
            MemberDTO memberDTO = null;
            try {
                memberDTO = memberService.authenticateMember(useridInput.getText(), passwordInput.getText());
                if (memberDTO != null) {
                    userId = memberDTO.getMemberIdx();
                    userName = memberDTO.getUserName();
                    errorMessageFrame("로그인 성공");
                    RePaint();
                    setInitLayOut();
                } else {
                    errorMessageFrame("아이디 또는 비밀번호가 틀립니다");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    // 회원가입 기능 --- O
    private void registerView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위해 JLabel을 감쌀 JPanel 생성
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(currentLocation("✅ 현재 페이지는 회원 가입 하는 페이지 입니다 ✅"));

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

        // 비밀번호 확인 패널
        JPanel rePwPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel rePwLabel = new JLabel("비밀번호 확인 : ");
        rePasswordInput.setPreferredSize(new Dimension(175, 40));
        rePwPanel.add(rePwLabel);
        rePwPanel.add(rePasswordInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerSubmitBtn.setPreferredSize(new Dimension(175, 40));
        btnPanel.add(registerSubmitBtn);

        // 회원가입 버튼
        registerSubmitBtn.addActionListener(e -> registerSubmit());

        container.add(titlePanel);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 100));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(homePanel);
        container.add(namePanel);
        container.add(idPanel);
        container.add(Box.createVerticalStrut(10)); // 아이디-비밀번호 간격
        container.add(pwPanel);
        container.add(Box.createVerticalStrut(15)); // 비밀번호-버튼 간격
        container.add(rePwPanel);
        container.add(btnPanel);
        revalidate();
        repaint();
    }

    // 회원가입 submit 기능 --- O
    private void registerSubmit() {
        String userName = userNameInput.getText().trim();
        String userId = useridInput.getText().trim();
        String password = passwordInput.getText().trim();
        String rePassword = rePasswordInput.getText().trim();

        if (userName.isEmpty()) {
            errorMessageFrame("이름을 입력해주시기 바랍니다");
            return;
        }
        if (userId.isEmpty()) {
            errorMessageFrame("아이디를 입력해 주시기 바랍니다");
            return;
        }
        if (password.isEmpty()) {
            errorMessageFrame("비밀번호를 입력해 주시기 바랍니다");
            return;
        }
        if (rePassword.isEmpty()) {
            errorMessageFrame("비밀번호 확인 문구를 입력해 주시기 바랍니다");
            return;
        }
        if (!password.equals(rePassword)) {
            errorMessageFrame("비밀번호랑 재 비밀번호 가 다릅니다!!");
            return;
        }

        try {
            // 아이디 중복검사 
            if (memberService.isUserIdExists(userId)) {
                errorMessageFrame("중복되는 아이디입니다.");
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
            errorMessageFrame("회원 가입 성공");

            userNameInput.setText("");
            useridInput.setText("");
            passwordInput.setText("");
            rePasswordInput.setText("");

            revalidate();
            repaint();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그아웃 기능 --- O
    private void logOutView() {
        userId = null;
        homeBtnApi();
        revalidate();
        repaint();
    }

    // 중고폰 등록하기 VIEW --- O
    private void phoneAddView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));


        // 중앙 정렬을 위해 JLabel을 감쌀 JPanel 생성
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(currentLocation("\uD83E\uDEA7 현재 페이지는 중고폰을 등록해서 판매하는 페이지 입니다 \uD83E\uDEA7"));

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

        phoneAddSubmitBtn.addActionListener(e -> phoneAddSubmit());

        container.add(titlePanel);

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

    // 중고폰 등록하기 Submit --- O
    private void phoneAddSubmit() {
        if (phoneAddNameInput.getText().trim().isEmpty()) {
            errorMessageFrame("값을 입력해 주시기 바랍니다");
            return;
        }
        if (priceInput.getText().trim().isEmpty()) {
            errorMessageFrame("가격을 입력해 주시기 바랍니다");
            return;
        }
        if (phoneStateInput.getText().trim().isEmpty()) {
            errorMessageFrame("상태를 입력해 주시기 바랍니다");
            return;
        }
        if (quantityInput.getText().trim().isEmpty()) {
            errorMessageFrame("수량을 입력해 주시기 바랍니다");
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
            errorMessageFrame("정상적으로 등록되었습니다 :" + phoneDTO.getPhoneId());
            RePaint();
            setInitLayOut();

        } catch (NumberFormatException n) {
            errorMessageFrame("가격 또는 수량을 정수형으로 입력해 주시기 바랍니다");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 자신이 구매한 폰 목록 보기 View --- O
    private void buyPhoneListView() {
        Container container = getContentPane();
        container.removeAll(); // 기존 내용 제거
        container.setLayout(new BorderLayout());

        // Home 버튼 패널
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 상단에 제목 + 버튼을 묶어서 넣을 패널
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.add(currentLocation("\uD83E\uDEA7 현재 페이지는 자신이 구매한 휴대폰 목록 페이지 입니다 \uD83E\uDEA7"));
        topPanel.add(homePanel);
        container.add(topPanel, BorderLayout.NORTH);

        // 3. 구매 리스트 패널 (스크롤 내장)
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // 패딩

        try {
            List<PhoneDTO> phoneDTOS = salesService.buyPhoneList(userId);
            if (phoneDTOS != null && !phoneDTOS.isEmpty()) {
                for (PhoneDTO phoneDTO : phoneDTOS) {

                    // 필요한 필드만 표시
                    String labelText = String.format(
                            "휴대폰 번호: %d | 휴대폰 이름: %s | 가격: %d | 등록일: %s | 등록자 번호: %d",
                            phoneDTO.getPhoneId(),
                            phoneDTO.getPhoneName(),
                            phoneDTO.getPrice(),
                            phoneDTO.getCreatedAt(),
                            phoneDTO.getMemberId()
                    );

                    JPanel phoneItemPanel = new JPanel();
                    phoneItemPanel.setBackground(new Color(240, 240, 240)); // 연회색 배경
                    phoneItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    phoneItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // 고정 높이
                    JLabel phoneLabel = new JLabel(labelText);
                    phoneItemPanel.add(phoneLabel);

                    listPanel.add(phoneItemPanel);
                    listPanel.add(Box.createVerticalStrut(5)); // 항목 사이 간격
                }
            } else {
                JLabel noDataLabel = new JLabel("구매한 폰이 없습니다.");
                noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                listPanel.add(noDataLabel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 4. 스크롤 패널로 감싸기
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 개선
        container.add(scrollPane, BorderLayout.CENTER);

        // 최종 갱신
        revalidate();
        repaint();
    }

    // 전체 휴대폰 View 검색 --- O
    private void getPhoneAllView() {
        Container container = getContentPane();
        container.removeAll(); // 기존 내용 제거
        container.setLayout(new BorderLayout());

        // 중앙 정렬을 위해 JLabel을 감쌀 JPanel 생성
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(currentLocation("\uD83E\uDEA7 현재 전체 중고폰 기종을 보여주는 페이지 입니다 \uD83E\uDEA7"));

        // 2. Home 버튼 패널
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 상단에 제목 + 버튼을 묶어서 넣을 패널
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.add(titlePanel);
        topPanel.add(homePanel);
        container.add(topPanel, BorderLayout.NORTH);

        // 3. 구매 리스트 패널 (스크롤 내장)
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // 패딩

        try {
            List<PhoneDTO> phoneDTOS = phoneService.getAllPhone();
            if (phoneDTOS != null && !phoneDTOS.isEmpty()) {
                for (PhoneDTO phoneDTO : phoneDTOS) {

                    // 필요한 필드만 표시
                    String labelText = String.format(
                            "휴대폰 번호: %d | 휴대폰 이름: %s | 가격: %d | 등록일: %s | 등록자 번호: %d",
                            phoneDTO.getPhoneId(),
                            phoneDTO.getPhoneName(),
                            phoneDTO.getPrice(),
                            phoneDTO.getCreatedAt(),
                            phoneDTO.getMemberId()
                    );

                    JPanel phoneItemPanel = new JPanel();
                    phoneItemPanel.setBackground(new Color(240, 240, 240)); // 연회색 배경
                    phoneItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    phoneItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // 고정 높이
                    JLabel phoneLabel = new JLabel(labelText);
                    phoneItemPanel.add(phoneLabel);

                    listPanel.add(phoneItemPanel);
                    listPanel.add(Box.createVerticalStrut(5)); // 항목 사이 간격
                }
            } else {
                JLabel noDataLabel = new JLabel("등록된 폰이 없습니다");
                noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                listPanel.add(noDataLabel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 4. 스크롤 패널로 감싸기
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 개선
        container.add(scrollPane, BorderLayout.CENTER);

        // 최종 갱신
        revalidate();
        repaint();
    }

    // 중고폰 검색 View 기능 --- O
    private void getPhoneSearchView() {
        Container container = getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(currentLocation("\uD83E\uDEA7 현재 페이지는 중고폰을 검색하는 페이지 입니다 \uD83E\uDEA7"));

        // 홈 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 현재 위치 + 홈 패널
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titlePanel);
        topPanel.add(homePanel);

        container.add(topPanel, BorderLayout.NORTH);

        // 검색 입력 + 버튼 패널 (한 줄)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel phoneLabel = new JLabel("기종명 : ");
        phoneNameInput.setPreferredSize(new Dimension(200, 30));
        phoneNameSubmitBtn.setPreferredSize(new Dimension(100, 30));
        searchPanel.add(phoneLabel);
        searchPanel.add(phoneNameInput);
        searchPanel.add(phoneNameSubmitBtn);

        // 결과 표시될 패널 (초기에는 빈 상태)
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resultPanel.setPreferredSize(new Dimension(800, 400)); // 전체 크기 키움

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 버튼 클릭 시 동작 연결
        phoneNameSubmitBtn.addActionListener(e -> searchPhoneSubmit(resultPanel));

        // 화면에 구성
        container.add(searchPanel, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
        revalidate();
        repaint();
    }

    // 중고폰 검색하기 Submit 버튼 --- O
    private void searchPhoneSubmit(JPanel resultPanel) {
        resultPanel.removeAll(); // 기존 결과 제거

        String input = phoneNameInput.getText();
        if (input == null || input.trim().isEmpty()) {
            JLabel warningLabel = new JLabel("값을 입력해주세요");
            warningLabel.setForeground(Color.RED);
            warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultPanel.add(warningLabel);
            resultPanel.revalidate();
            resultPanel.repaint();
            return;
        }

        try {
            List<PhoneDTO> phoneDTOList = phoneService.searchPhoneByName(input);
            if (phoneDTOList.isEmpty()) {
                JLabel emptyLabel = new JLabel("검색 결과가 없습니다.");
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultPanel.add(emptyLabel);
            } else {
                for (PhoneDTO phoneDTO : phoneDTOList) {

                    // 필요한 필드만 표시
                    String labelText = String.format(
                            "휴대폰 번호: %d | 휴대폰 이름: %s | 가격: %d | 등록일: %s | 등록자 번호: %d",
                            phoneDTO.getPhoneId(),
                            phoneDTO.getPhoneName(),
                            phoneDTO.getPrice(),
                            phoneDTO.getCreatedAt(),
                            phoneDTO.getMemberId()
                    );

                    JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    itemPanel.setBackground(new Color(240, 240, 240)); // 백그라운드 색 변경
                    itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 테두리 추가
                    itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                    JLabel itemLabel = new JLabel(labelText);
                    itemPanel.add(itemLabel);
                    resultPanel.add(itemPanel);
                    resultPanel.add(Box.createVerticalStrut(5));
                }
            }
        } catch (SQLException e) {
            JLabel errorLabel = new JLabel("데이터 불러오기 오류: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            resultPanel.add(errorLabel);
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    // 가장 많이 팔린 중고폰 검색  VIEW
    private void getBestSellPhoneView() {
        Container container = getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(currentLocation("\uD83E\uDEA7 현재 페이지는 가장 많이 팔린 기종명을 검색하는 페이지 입니다 \uD83E\uDEA7"));

        // 홈 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 현재 위치 + 홈 패널
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titlePanel);
        topPanel.add(homePanel);

        container.add(topPanel, BorderLayout.NORTH);

        // 검색 입력 및 버튼 패널 한줄
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel phoneLabel = new JLabel("검색 하고 싶은 기종명 : ");
        phoneBestSalesInput.setPreferredSize(new Dimension(200, 30));
        phoneBestSalesSubmitBtn.setPreferredSize(new Dimension(100, 30));
        searchPanel.add(phoneLabel);
        searchPanel.add(phoneBestSalesInput);
        searchPanel.add(phoneBestSalesSubmitBtn);

        container.add(searchPanel, BorderLayout.CENTER);

        // 결과 표시 데이터 많을 경우 스크롤 가능
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resultPanel.setPreferredSize(new Dimension(800, 400)); // 결과 영역 사이즈

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        container.add(scrollPane, BorderLayout.SOUTH);

        // 버튼 클릭시 submit 이벤트 작동
        phoneBestSalesSubmitBtn.addActionListener(e -> getBestPhoneSubmit(resultPanel));

        // 화면 갱신
        setVisible(true);
        revalidate();
        repaint();
    }

    // 가장 많이 팔린 중고폰 검색 버튼 submit
    private void getBestPhoneSubmit(JPanel resultPanel) {
        resultPanel.removeAll(); // 기존 결과 제거

        String input = phoneBestSalesInput.getText();
        if (input == null || input.trim().isEmpty()) {
            JLabel warningLabel = new JLabel("값을 입력해주세요");
            warningLabel.setForeground(Color.RED);
            warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultPanel.add(warningLabel);
            resultPanel.revalidate();
            resultPanel.repaint();
            return;
        }

        try {
            List<PhoneDTO> phoneDTOList = salesService.getBestSellPhone(input);
            if (phoneDTOList.isEmpty()) {
                JLabel emptyLabel = new JLabel("검색 결과가 없습니다.");
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultPanel.add(emptyLabel);
            } else {
                for (PhoneDTO phoneDTO : phoneDTOList) {
                    JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    itemPanel.setBackground(new Color(240, 240, 240)); // 백그라운드 색 변경
                    itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 테두리 추가
                    itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));


                    // 필요한 필드만 표시
                    String labelText = String.format(
                            "휴대폰 번호: %d | 휴대폰 이름: %s | 가격: %d | 등록일: %s | 등록자 번호: %d",
                            phoneDTO.getPhoneId(),
                            phoneDTO.getPhoneName(),
                            phoneDTO.getPrice(),
                            phoneDTO.getCreatedAt(),
                            phoneDTO.getMemberId()
                    );

                    JLabel itemLabel = new JLabel(labelText);
                    itemPanel.add(itemLabel);
                    resultPanel.add(itemPanel);
                    resultPanel.add(Box.createVerticalStrut(5));
                }
            }
        } catch (SQLException e) {
            JLabel errorLabel = new JLabel("데이터 불러오기 오류: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            resultPanel.add(errorLabel);
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    // 휴대폰 버튼 눌러서 구매하기 --- O
    private void buyPhoneSubmit() {
        String input = buyPhoneInput.getText().trim();

        if (input.isEmpty()) {
            errorMessageFrame("값을 입력해주시기 바랍니다");
            buyPhoneInput.setText("");
            return;
        }
        try {
            int buyIdx = Integer.parseInt(input);
            salesService.SalesPhone(userId, buyIdx);
            errorMessageFrame("구매 성공");
            RePaint();
            setInitLayOut();
            buyPhoneInput.setText("");
        } catch (NumberFormatException e) {
            errorMessageFrame("숫자만 입력해주세요.");
        } catch (SQLException e) {
            errorMessageFrame("존재하지 않는 상품입니다.");
            throw new RuntimeException(e);
        }
    }

    // 휴대폰 구매하기 기종 번호 VIEW --- O
    private void buyPhoneView() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(currentLocation("\uD83D\uDCBB현재 페이지는 중고폰을 구매하는 페이지 입니다\uD83D\uDCBB"));

        // 홈 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 현재 위치 + 홈 패널
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titlePanel);
        topPanel.add(homePanel);

        container.add(topPanel, BorderLayout.NORTH);

        // 구매하기 휴대폰 번호
        JPanel buyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel buyLabel = new JLabel("휴대폰 기종 번호 : ");
        buyPhoneInput.setPreferredSize(new Dimension(175, 40));
        buyPanel.add(buyLabel);
        buyPanel.add(buyPhoneInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buyPhoneSubmitBtn.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(buyPhoneSubmitBtn);

        // 구매하기 버튼
        buyPhoneSubmitBtn.addActionListener(e -> buyPhoneSubmit());

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

    // 버튼에 따라 이벤트
    private void addEventListener() {
        logoutBtn.addActionListener(this);
        registerViewBtn.addActionListener(this);
        phoneSearchViewBtn.addActionListener(this);
        phoneSalseViewBtn.addActionListener(this);
        phoneAllViewBtn.addActionListener(this);
        loginViewBtn.addActionListener(this);
        phoneBestSalesViewBtn.addActionListener(this);
        phoneAddViewBtn.addActionListener(this);
        buyPhoneListViewBtn.addActionListener(this);
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
        rePasswordInput.setText("");

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
        }
        if (e.getSource() == loginViewBtn) {
            RePaint();
            loginView();
        } else if (e.getSource() == logoutBtn) {
            RePaint();
            logOutView();
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
        } else if (e.getSource() == phoneBestSalesViewBtn) {
            RePaint();
            getBestSellPhoneView();
        } else if (e.getSource() == phoneAddViewBtn) {
            RePaint();
            phoneAddView();
        } else if (e.getSource() == buyPhoneListViewBtn) {
            repaint();
            buyPhoneListView();
        }
    }

    // 페이지 다시 그리기
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
