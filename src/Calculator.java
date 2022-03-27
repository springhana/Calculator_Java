import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame {
	
	private JTextField inputSpace; 
	private String num = ""; 
	private ArrayList<String> equation = new ArrayList<String>(); //ArrayList 추가
	
	public Calculator() {
		setLayout(null); //기본으로 있는 레이아웃 관리자를 제거
		
		//화면
		inputSpace = new JTextField(); //빈 공간의 JTextField를 생성하고 버튼으로만 입력을 받을 것이기 때문 
		inputSpace.setEditable(false); //편집 가능 여부는 불가능으로
		inputSpace.setBackground(Color.white); //배경색
		inputSpace.setHorizontalAlignment(JTextField.RIGHT); //정렬 위치
		inputSpace.setFont(new Font("Arial", Font.BOLD, 50)); //글씨체(글씨체,굵기,크기)
		inputSpace.setBounds(8, 10, 270, 70); //화면 위치, 크기 Bounds(x, y, 270×70)
		
		//버튼
		JPanel buttonPanel = new JPanel();//패널 만들기
		buttonPanel.setLayout(new GridLayout(4, 4, 10, 10)); //격자형태로 배치 GridLayout(가로,세로,좌우 간격, 상하각격)
		buttonPanel.setBounds(8, 90, 270, 235); //버튼 위치, 크기 (x, y, 270×2)
		
		String button_names[] = {"C", "÷" ,"×", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0" }; //버튼 종류
		JButton buttons[] = new JButton[button_names.length];
		for(int i =0; i<button_names.length;i++) {
			buttons[i] = new JButton(button_names[i]);
			buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
			if(button_names[i] == "C") { 
				buttons[i].setBackground(Color.red);
			}
			else if((i >= 4 && i <= 6) || (i >= 8 && i <= 10) || (i >= 12 && i <= 14)) {
				buttons[i].setBackground(Color.BLACK);
			}
			else {
				buttons[i].setBackground(Color.GRAY);
			}
				buttons[i].setForeground(Color.white); //글자 색깔
				buttons[i].setBorderPainted(false); //테두리 (없음)
				buttons[i].addActionListener(new PanActionListener()); //액션리스너를 버튼에 추가
				buttonPanel.add(buttons[i]); //buttonPanel에 buttons[i]를 추가	
		}
		
		add(inputSpace); //inputSpace 추가
		add(buttonPanel); //buttonPanel 추가
		
		setTitle("계산기"); //창의 제목
		setVisible(true); //창 보이기 여부
		setSize(300, 370); //사이즈(x, y)
		setLocationRelativeTo(null); // null값을 줄경우 화면 가운데에 띄우며 사이즈 조절 불가
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	 //기능
	
	class PanActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String operation = e.getActionCommand(); //getActionCommand로 어떤 버튼을 눌렀는지 받아온다
			if(operation.equals("C")) { //equals 비교
				inputSpace.setText("");
			}
			else if(operation.equals("=")) {
				String result = Double.toString(calculator(inputSpace.getText()));
				inputSpace.setText("" + result);
				num = "";
			} 
			else {
				inputSpace.setText(inputSpace.getText() + e.getActionCommand());
			}
		}
	}
	
	private void fullTextParsing(String inputText) {
		equation.clear();
		
		for(int i = 0;i < inputText.length(); i++) { //for문으로 계산식 하나하나 거쳐가게 함
			char ch = inputText.charAt(i);
			
			if(ch == '-' | ch == '+' | ch == '×' | ch == '÷') { //연산기호가 나올 시 ArrayList에 추가되고 초기화
				equation.add(num); 
				num = ""; //앞은 숫자이므로 숫자가 먼저
				equation.add(ch + "");
			} else { 
				num = num + ch;
			}
		}
		equation.add(num); //반복문이 끝나고 최종적으로 있는 num도 ArrayList에 추가
	}
	
	public double calculator(String inputText) {
		fullTextParsing(inputText);
		
		double prev = 0; //결과값
		double current = 0;
		String mode = ""; // mode 라는 변수를 선언해 연산 기호에 대한 처리
		
		for(String s : equation) {
			if(s.equals("+")) {
				mode = "add";
			} else if(s.equals("-")) {
				mode = "sub";
			} else if(s.equals("×")) {
				mode = "mul";
			} else if(s.equals("÷")) {
				mode = "div";
			} else {
				current = Double.parseDouble(s); //나머지(숫자)의 경우 문자열로 Double로 형변환을 해줘야 함
				if(mode == "add") {
					prev += current;
				} else if(mode == "sub") {
					prev -= current;
				} else if(mode == "mul") {
					prev *= current;
				} else if(mode == "div") {
					prev /= current;
				} else {
					prev = current;
				}
			}
		}
		return prev;
		
	}
	public static void main(String[] args) {
		new Calculator();
	}

}
