package view;

import dto.MemberDTO;
import dto.PhoneDTO;
import dto.SalesDTO;
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
    private final MemberService service = new MemberService();
    private final PhoneService phoneService = new PhoneService();
    private final SalesService salesService = new SalesService();


    private JPanel studentPanel; // 학생 목록을 보여줄 패널
    private Integer userId = null;
    private String password = null;
    JButton loginBtn;
    JButton logoutBtn;
    JButton registerBtn;
    JButton phoneAllView;
    JButton phoneSearch;
    JButton phoneSalse;
    JButton loginSubmitBtn;
    JButton homeBtn;
    JButton SignSubmitBtn;
    JButton PhoneNameBtn;
    JButton buyPhoneBtn;

    // 로그인 입력 기능
    JTextField useridInput;
    JTextField passwordInput;

    // 회원 가입 입력 기능
    JTextField userNameInput;

    // 기종 검색 입력 기능
    JTextField PhoneNameInput;

    // 구매하기 기능 회원 번호
    JTextField buyPhoneInput;

    public UsedPhoneView(){
        initData();
        setInitLayOut();
        addEventListener();
    }

    private void initData(){
        setSize(1000, 600);	//사이즈는 640*480
        setResizable(false);	//사이즈 재조정 불가능
        setLocationRelativeTo(null);	//창이 가운데에 뜨도록 함
        setDefaultCloseOperation(EXIT_ON_CLOSE);	//창을 끄면 프로그램을 종료

        loginBtn = new JButton("로그인");


        loginSubmitBtn = new JButton("로그인확인");
        registerBtn = new JButton("회원가입");
        logoutBtn = new JButton("로그아웃");
        phoneAllView = new JButton("전체 목록 조회");
        phoneSalse = new JButton("중고폰 구매");
        phoneSearch = new JButton("중고폰 서치");
        homeBtn = new JButton("메인으로");
        SignSubmitBtn = new JButton("회원 가입");
        PhoneNameBtn = new JButton("검색하기");
        buyPhoneBtn = new JButton("구매하기");
        useridInput = new JTextField(20);
        passwordInput = new JTextField(20);
        userNameInput = new JTextField(20);
        PhoneNameInput = new JTextField(20);
        buyPhoneInput = new JTextField(20);
        studentPanel = new JPanel();
    }


    private void setInitLayOut(){
        // 현재 목차 띄워 주기

        JLabel label = new JLabel("\uD83E\uDEA7 여기는 중고폰을 등록하고 구매하는 사이트입니다"); // 제일 상단에 띄워줌
        label.setFont(new Font("Serif",Font.BOLD,20));
        // 기존 메인 화면
        Container container = getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.RIGHT,20,10));
        container.add(label,BorderLayout.NORTH);
        container.add(homeBtn);

        if(userId == null){
            container.add(loginBtn);
            container.add(registerBtn);
        }else {
            container.add(phoneAllView); // 전체 기종 검색
            container.add(phoneSearch); // 기종 검색
            container.add(phoneSalse); // 판매 목록
            container.add(logoutBtn); // 로그아웃 버튼
        }
        setVisible(true);	//창을 눈에 보이도록 함
        revalidate();
        repaint();
    }

    // 로그인 기능
    private void loginView(){
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

    // 회원가입 기능
    private void registerView(){
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
        SignSubmitBtn.setPreferredSize(new Dimension(175, 40));
        btnPanel.add(SignSubmitBtn);

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

    // 로그아웃 기능
    private void logOutView(){
        userId = null;
        password = null;
        setInitLayOut();
        revalidate();
        repaint();
    }

    // 전체 휴대폰 검색
    private void getPhoneAllView() throws SQLException {
        Container container = getContentPane();
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 맨위에 목차 띄워주기
        JPanel historyPanel = new JPanel();
        JLabel HistoryLabel = new JLabel("\uD83E\uDEA7 현재 등록된 모든 중고폰 목록입니다."); // 제일 상단에 띄워줌
        HistoryLabel.setFont(new Font("Serif",Font.BOLD,20));
        historyPanel.add(HistoryLabel);

        // 학생 목록을 담을 패널 (세로 정렬)
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위한 정렬 설정
        studentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 리스트 받아오기
        List<MemberDTO> memberDTOS = service.allMember();

        for (MemberDTO student : memberDTOS) {
            JLabel label = new JLabel(student.toString());
            label.setAlignmentX(Component.CENTER_ALIGNMENT); // 각 라벨 중앙 정렬
            studentPanel.add(label);
            studentPanel.add(Box.createVerticalStrut(10)); // 라벨 사이 여백
        }

        // 스크롤 필요하면 JScrollPane으로 감싸기 (옵션)
        JScrollPane scrollPane = new JScrollPane(studentPanel);
        scrollPane.setBorder(null);

        // 전체를 가운데에 배치

        container.setLayout(new BorderLayout());
        container.add(historyPanel,BorderLayout.NORTH);
        container.add(homePanel,BorderLayout.NORTH);
        container.add(HistoryLabel,BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // 중고폰 검색 기능
    private void getPhoneSearchView(){
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 기종명 검색 기능
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel phoneLabel = new JLabel("기종명 검색 : ");
        PhoneNameInput.setPreferredSize(new Dimension(200, 50));
        phonePanel.add(phoneLabel);
        phonePanel.add(PhoneNameInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        PhoneNameBtn.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(PhoneNameBtn);

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
    private void searchPhone() throws SQLException {
        if(PhoneNameInput.getText() == null || PhoneNameInput.getText().trim().isEmpty()){
            System.out.println("값을 입력해주시기 바랍니다");
        }

        Container container = getContentPane();
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel,BoxLayout.Y_AXIS));
        container.add(homePanel,BorderLayout.NORTH);

        // 학생 목록을 담을 패널 (세로 정렬)
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위한 정렬 설정
        studentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 리스트 받아오기
        List<PhoneDTO> phoneDTO = phoneService.searchPhoneByName(PhoneNameInput.getText());

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
        container.add(homePanel,BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();

    }

    // 휴대폰 버튼 눌러서 구매하기
    private void buyPhoneSubmit(){
        String input = buyPhoneInput.getText().trim();

        if (input.isEmpty()) {
            System.out.println("값을 입력해주시기 바랍니다");
            return;  // 입력이 없으면 함수 종료
        }

        try {
            int buyIdx = Integer.parseInt(input);
            salesService.SalesPhone(userId, buyIdx);
            System.out.println("구매 성공");
            RePaint();
            setInitLayOut();
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해주세요.");
        } catch (SQLException e) {
            System.out.println("존재하지 않는 상품입니다.");
            throw new RuntimeException(e);
        }
    }

    // 휴대폰 구매하기 기종 번호로 VIEW
    private void buyPhoneView() throws SQLException {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        // 메인으로 가는 패널
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.add(homeBtn);

        // 구매하기 휴대폰 번호
        JPanel buyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel buyLabel = new JLabel("휴대폰 기종 번호 : ");
        buyPhoneInput.setPreferredSize(new Dimension(200, 50));
        buyPanel.add(buyLabel);
        buyPanel.add(passwordInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buyPhoneBtn.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(buyPhoneBtn);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
        container.add(Box.createVerticalStrut(10)); // 아이디-비밀번호 간격
        container.add(buyPanel);
        container.add(Box.createVerticalStrut(15)); // 비밀번호-버튼 간격
        container.add(btnPanel);
        revalidate();
        repaint();

    }


    // 로그인 하기 버튼
    private void loginSubmit() throws SQLException {

        if(useridInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty()){
            System.out.println("값을 입력해주시기 바랍니다");
        }
        MemberDTO memberDTO = service.authenticateMember(useridInput.getText() , passwordInput.getText());
        if (memberDTO != null){
            userId = memberDTO.getMemberIdx();
            System.out.println("로그인 성공");
            RePaint();
            setInitLayOut();
        } else {
            System.out.println("존재 하지 않는 아이디 입니다");
        }
    }

    // 버튼에 따라 이벤트
    private void addEventListener(){
        logoutBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        phoneSearch.addActionListener(this);
        phoneSalse.addActionListener(this);
        phoneAllView.addActionListener(this);
        loginBtn.addActionListener(this);
        loginSubmitBtn.addActionListener(this);
        SignSubmitBtn.addActionListener(this);
        PhoneNameBtn.addActionListener(this);
        buyPhoneBtn.addActionListener(this);
        homeBtn.addActionListener(this);
    }

    // 해당 버튼에 따라 이벤트 발생
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == homeBtn){
            RePaint();
            setInitLayOut();
        }
        if(e.getSource() == loginBtn){
            RePaint();
            loginView();
        }else if(e.getSource() == logoutBtn){
            RePaint();
            logOutView();
        }else if(e.getSource() == loginSubmitBtn){
            try {
                loginSubmit();
            } catch (SQLException ex) {
                System.out.println("예기치 못한 오류 발생 :" + ex.getMessage());
            }
        }else if(e.getSource() == registerBtn){
            RePaint();
            registerView();
        }else if(e.getSource() == phoneAllView){
            try {
                RePaint();
                getPhoneAllView();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else if(e.getSource() == phoneSearch){
            RePaint();
            getPhoneSearchView();
        }else if(e.getSource() == phoneSalse){
            RePaint();
            try {
                buyPhoneView();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else if(e.getSource() == PhoneNameBtn){
            try {
                searchPhone();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else if(e.getSource() == buyPhoneBtn){
            buyPhoneSubmit();
        }
    }

    private void RePaint(){
        Container container = getContentPane();
        container.removeAll();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new UsedPhoneView();
    }

}
