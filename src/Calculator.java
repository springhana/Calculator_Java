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
	private ArrayList<String> equation = new ArrayList<String>(); //ArrayList �߰�
	
	public Calculator() {
		setLayout(null); //�⺻���� �ִ� ���̾ƿ� �����ڸ� ����
		
		//ȭ��
		inputSpace = new JTextField(); //�� ������ JTextField�� �����ϰ� ��ư���θ� �Է��� ���� ���̱� ���� 
		inputSpace.setEditable(false); //���� ���� ���δ� �Ұ�������
		inputSpace.setBackground(Color.white); //����
		inputSpace.setHorizontalAlignment(JTextField.RIGHT); //���� ��ġ
		inputSpace.setFont(new Font("Arial", Font.BOLD, 50)); //�۾�ü(�۾�ü,����,ũ��)
		inputSpace.setBounds(8, 10, 270, 70); //ȭ�� ��ġ, ũ�� Bounds(x, y, 270��70)
		
		//��ư
		JPanel buttonPanel = new JPanel();//�г� �����
		buttonPanel.setLayout(new GridLayout(4, 4, 10, 10)); //�������·� ��ġ GridLayout(����,����,�¿� ����, ���ϰ���)
		buttonPanel.setBounds(8, 90, 270, 235); //��ư ��ġ, ũ�� (x, y, 270��2)
		
		String button_names[] = {"C", "��" ,"��", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0" }; //��ư ����
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
				buttons[i].setForeground(Color.white); //���� ����
				buttons[i].setBorderPainted(false); //�׵θ� (����)
				buttons[i].addActionListener(new PanActionListener()); //�׼Ǹ����ʸ� ��ư�� �߰�
				buttonPanel.add(buttons[i]); //buttonPanel�� buttons[i]�� �߰�	
		}
		
		add(inputSpace); //inputSpace �߰�
		add(buttonPanel); //buttonPanel �߰�
		
		setTitle("����"); //â�� ����
		setVisible(true); //â ���̱� ����
		setSize(300, 370); //������(x, y)
		setLocationRelativeTo(null); // null���� �ٰ�� ȭ�� ����� ���� ������ ���� �Ұ�
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	 //���
	
	class PanActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String operation = e.getActionCommand(); //getActionCommand�� � ��ư�� �������� �޾ƿ´�
			if(operation.equals("C")) { //equals ��
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
		
		for(int i = 0;i < inputText.length(); i++) { //for������ ���� �ϳ��ϳ� ���İ��� ��
			char ch = inputText.charAt(i);
			
			if(ch == '-' | ch == '+' | ch == '��' | ch == '��') { //�����ȣ�� ���� �� ArrayList�� �߰��ǰ� �ʱ�ȭ
				equation.add(num); 
				num = ""; //���� �����̹Ƿ� ���ڰ� ����
				equation.add(ch + "");
			} else { 
				num = num + ch;
			}
		}
		equation.add(num); //�ݺ����� ������ ���������� �ִ� num�� ArrayList�� �߰�
	}
	
	public double calculator(String inputText) {
		fullTextParsing(inputText);
		
		double prev = 0; //�����
		double current = 0;
		String mode = ""; // mode ��� ������ ������ ���� ��ȣ�� ���� ó��
		
		for(String s : equation) {
			if(s.equals("+")) {
				mode = "add";
			} else if(s.equals("-")) {
				mode = "sub";
			} else if(s.equals("��")) {
				mode = "mul";
			} else if(s.equals("��")) {
				mode = "div";
			} else {
				current = Double.parseDouble(s); //������(����)�� ��� ���ڿ��� Double�� ����ȯ�� ����� ��
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
