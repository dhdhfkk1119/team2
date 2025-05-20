package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsedPhoneView extends JFrame implements ActionListener {
    private String userId = null;
    private String password = null;
    JButton loginBtn;
    JButton logoutBtn;
    JButton registerBtn;
    JButton phoneAllView;
    JButton phoneSearch;
    JButton phoneSalse;
    JButton submit;
    JButton home;

    // 로그인 입력 기능
    JTextField useridInput;
    JTextField passwordInput;

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


        submit = new JButton("로그인확인");
        registerBtn = new JButton("회원가입");
        logoutBtn = new JButton("로그아웃");
        phoneAllView = new JButton("전체 목록 조회");
        phoneSalse = new JButton("중고폰 구매");
        phoneSearch = new JButton("중고폰 서치");
        home = new JButton("메인으로");
        useridInput = new JTextField(20);
        passwordInput = new JTextField(20);
    }


    private void setInitLayOut(){
        JLabel label = new JLabel("여기는 중고폰을 등록하고 구매하는 사이트입니다"); // 제일 상단에 띄워줌
        // 기존 메인 화면
        Container container = getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.RIGHT,20,10));
        container.add(label,BorderLayout.NORTH);
        container.add(home);
        if(userId == null){
            container.add(loginBtn);
            container.add(registerBtn);
        }else {
            container.add(phoneAllView);
            container.add(phoneSearch);
            container.add(phoneSalse);
            container.add(logoutBtn);
        }
        setVisible(true);	//창을 눈에 보이도록 함
        revalidate();
        repaint();
    }

    // 로그인 기능
    private void loginView(){
        Container container = getContentPane();
        container.removeAll(); // 기존 컴포넌트 제거
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // 중앙 정렬을 위해 각 행을 패널로 구성
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel idLabel = new JLabel("아이디 : ");
        useridInput.setPreferredSize(new Dimension(200, 50));
        idPanel.add(idLabel);
        idPanel.add(useridInput);

        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel pwLabel = new JLabel("비밀번호 : ");
        passwordInput.setPreferredSize(new Dimension(200, 50));
        pwPanel.add(pwLabel);
        pwPanel.add(passwordInput);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submit.setPreferredSize(new Dimension(200, 50));
        btnPanel.add(submit);

        // 가운데 여백용 패널 (옵션)
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 50));
        container.add(spacer);

        // 컨테이너에 행별 패널 추가
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
    
    private void addEventListener(){
        logoutBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        phoneSearch.addActionListener(this);
        phoneSalse.addActionListener(this);
        phoneAllView.addActionListener(this);
        loginBtn.addActionListener(this);
        submit.addActionListener(this);
        home.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == home){
            RePaint();
            setInitLayOut();
        }
        if(e.getSource() == loginBtn){
            RePaint();
            loginView();
        }else if(e.getSource() == logoutBtn){
            RePaint();
            logOutView();
        }else if(e.getSource() == submit){
            if(useridInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty()){
                System.out.println("값을 입력해주시기 바랍니다");
            }else {
                userId = useridInput.getText();
                password = passwordInput.getText();
                System.out.println(userId);
                RePaint();
                setInitLayOut();
            }

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
