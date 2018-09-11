package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * �����ҷ�����װ���࣬�������
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class findDialog extends JDialog {
	public findDialog() {
		super();
	}

	/**
	 * find����ʵ�ֲ��ҹ���
	 * 
	 * @param editArea �ı��༭��
	 */
	public void find(JTextArea editArea) {
		final JDialog findDialog = new JDialog(this, "����", false);// falseʱ������������ͬʱ���ڼ���״̬(����ģʽ)
		Container con = findDialog.getContentPane();// ���ش˶Ի����contentPane����
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel findContentLabel = new JLabel("��������(N)��");
		final JTextField findText = new JTextField(15);
		JButton findNextButton = new JButton("������һ��(F)��");
		final JCheckBox matchCheckBox = new JCheckBox("���ִ�Сд(C)");
		ButtonGroup bGroup = new ButtonGroup();
		final JRadioButton upButton = new JRadioButton("����(U)");
		final JRadioButton downButton = new JRadioButton("����(U)");
		downButton.setSelected(true);
		bGroup.add(upButton);
		bGroup.add(downButton);
		/*
		 * ButtonGroup��������Ϊһ�鰴ť����һ����⣨multiple-exclusion�������� ʹ����ͬ�� ButtonGroup
		 * ���󴴽�һ�鰴ť��ζ�š�����������һ����ťʱ�����ر����е��������а�ť��
		 */
		/*
		 * JRadioButton����ʵ��һ����ѡ��ť���˰�ť��ɱ�ѡ���ȡ��ѡ�񣬲���Ϊ�û���ʾ��״̬�� �� ButtonGroup
		 * �������ʹ�ÿɴ���һ�鰴ť��һ��ֻ��ѡ�����е�һ����ť�� ������һ�� ButtonGroup �������� add ������
		 * JRadioButton ��������ڴ����С���
		 */
		JButton cancel = new JButton("ȡ��");
		// ȡ����ť�¼�����
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findDialog.dispose();
			}
		});
		// "������һ��"��ť����
		findNextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // "���ִ�Сд(C)"��JCheckBox�Ƿ�ѡ��
				int k = 0, m = 0;
				final String str1, str2, str3, str4, strA, strB;
				str1 = editArea.getText();
				str2 = findText.getText();
				str3 = str1.toUpperCase();
				str4 = str2.toUpperCase();
				if (matchCheckBox.isSelected())// ���ִ�Сд
				{
					strA = str1;
					strB = str2;
				} else// �����ִ�Сд,��ʱ����ѡ����ȫ�����ɴ�д(��Сд)���Ա��ڲ���
				{
					strA = str3;
					strB = str4;
				}
				if (upButton.isSelected()) { // k=strA.lastIndexOf(strB,notepad1.editArea.getCaretPosition()-1);
					if (editArea.getSelectedText() == null)
						k = strA.lastIndexOf(strB, editArea.getCaretPosition() - 1);
					else
						k = strA.lastIndexOf(strB, editArea.getCaretPosition() - findText.getText().length() - 1);
					if (k > -1) { // String
									// strData=strA.subString(k,strB.getText().length()+1);
						editArea.setCaretPosition(k);
						editArea.select(k, k + strB.length());
					} else {
						JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ����ݣ�", "����", JOptionPane.INFORMATION_MESSAGE);
					}
				} else if (downButton.isSelected()) {
					if (editArea.getSelectedText() == null)
						k = strA.indexOf(strB, editArea.getCaretPosition() + 1);
					else
						k = strA.indexOf(strB, editArea.getCaretPosition() - findText.getText().length() + 1);
					if (k > -1) { // String
									// strData=strA.subString(k,strB.getText().length()+1);
						editArea.setCaretPosition(k);
						editArea.select(k, k + strB.length());
					} else {
						JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ����ݣ�", "����", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});// "������һ��"��ť��������
			// ����"����"�Ի���Ľ���
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel directionPanel = new JPanel();
		directionPanel.setBorder(BorderFactory.createTitledBorder("����"));
		// ����directionPanel����ı߿�;
		// BorderFactory.createTitledBorder(String
		// title)����һ���±���߿�ʹ��Ĭ�ϱ߿򣨸��񻯣���Ĭ���ı�λ�ã�λ�ڶ����ϣ���Ĭ�ϵ��� (leading)
		// �Լ��ɵ�ǰ���ȷ����Ĭ��������ı���ɫ����ָ���˱����ı���
		directionPanel.add(upButton);
		directionPanel.add(downButton);
		panel1.setLayout(new GridLayout(2, 1));
		panel1.add(findNextButton);
		panel1.add(cancel);
		panel2.add(findContentLabel);
		panel2.add(findText);
		panel2.add(panel1);
		panel3.add(matchCheckBox);
		panel3.add(directionPanel);
		con.add(panel2);
		con.add(panel3);
		findDialog.setSize(410, 180);
		findDialog.setResizable(false);// ���ɵ�����С
		findDialog.setLocation(230, 280);
		findDialog.setVisible(true);
	}
}
