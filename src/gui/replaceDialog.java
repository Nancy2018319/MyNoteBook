package gui;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ���滻������װ���࣬�������
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class replaceDialog extends JDialog {
	public replaceDialog() {
		super();
	}

	/**
	 * replace����ʵ���ı��滻����
	 * 
	 * @param editArea �ı��༭��
	 */
	public void replace(JTextArea editArea) {
		final JDialog replaceDialog = new JDialog(this, "�滻", false);// falseʱ������������ͬʱ���ڼ���״̬(����ģʽ)
		Container con = replaceDialog.getContentPane();// ���ش˶Ի����contentPane����
		con.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel findContentLabel = new JLabel("��������(N)��");
		final JTextField findText = new JTextField(15);
		JButton findNextButton = new JButton("������һ��(F):");
		JLabel replaceLabel = new JLabel("�滻Ϊ(P)��");
		final JTextField replaceText = new JTextField(15);
		JButton replaceButton = new JButton("�滻(R)");
		JButton replaceAllButton = new JButton("ȫ���滻(A)");
		JButton cancel = new JButton("ȡ��");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceDialog.dispose();
			}
		});
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
				if (upButton.isSelected()) { // k=strA.lastIndexOf(strB,notepad.editArea.getCaretPosition()-1);
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

		// "�滻"��ť����
		replaceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (replaceText.getText().length() == 0 && editArea.getSelectedText() != null)
					editArea.replaceSelection("");
				if (replaceText.getText().length() > 0 && editArea.getSelectedText() != null)
					editArea.replaceSelection(replaceText.getText());
			}
		});// "�滻"��ť��������

		// "ȫ���滻"��ť����
		replaceAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editArea.setCaretPosition(0); // �����ŵ��༭����ͷ
				int k = 0, m = 0, replaceCount = 0;
				if (findText.getText().length() == 0) {
					JOptionPane.showMessageDialog(replaceDialog, "����д��������!", "��ʾ", JOptionPane.WARNING_MESSAGE);
					findText.requestFocus(true);
					return;
				}
				while (k > -1)// ���ı��������ݱ�ѡ��ʱ(k>-1��ѡ��)�����滻�����򲻽���whileѭ��
				{ // "���ִ�Сд(C)"��JCheckBox�Ƿ�ѡ��
					// int k=0,m=0;
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
					if (upButton.isSelected()) { // k=strA.lastIndexOf(strB,notepad.editArea.getCaretPosition()-1);
						if (editArea.getSelectedText() == null)
							k = strA.lastIndexOf(strB, editArea.getCaretPosition() - 1);
						else
							k = strA.lastIndexOf(strB, editArea.getCaretPosition() - findText.getText().length() - 1);
						if (k > -1) { // String
										// strData=strA.subString(k,strB.getText().length()+1);
							editArea.setCaretPosition(k);
							editArea.select(k, k + strB.length());
						} else {
							if (replaceCount == 0) {
								JOptionPane.showMessageDialog(replaceDialog, "�Ҳ��������ҵ�����!", "���±�",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(replaceDialog, "�ɹ��滻" + replaceCount + "��ƥ������!", "�滻�ɹ�",
										JOptionPane.INFORMATION_MESSAGE);
							}
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
							if (replaceCount == 0) {
								JOptionPane.showMessageDialog(replaceDialog, "�Ҳ��������ҵ�����!", "���±�",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(replaceDialog, "�ɹ��滻" + replaceCount + "��ƥ������!", "�滻�ɹ�",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					if (replaceText.getText().length() == 0 && editArea.getSelectedText() != null) {
						editArea.replaceSelection("");
						replaceCount++;
					}

					if (replaceText.getText().length() > 0 && editArea.getSelectedText() != null) {
						editArea.replaceSelection(replaceText.getText());
						replaceCount++;
					}
				} // whileѭ������
			}
		});// "�滻ȫ��"��������

		// ����"�滻"�Ի���Ľ���
		JPanel directionPanel = new JPanel();
		directionPanel.setBorder(BorderFactory.createTitledBorder("����"));
		// ����directionPanel����ı߿�;
		// BorderFactory.createTitledBorder(String
		// title)����һ���±���߿�ʹ��Ĭ�ϱ߿򣨸��񻯣���Ĭ���ı�λ�ã�λ�ڶ����ϣ���Ĭ�ϵ��� (leading)
		// �Լ��ɵ�ǰ���ȷ����Ĭ��������ı���ɫ����ָ���˱����ı���
		directionPanel.add(upButton);
		directionPanel.add(downButton);
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayout(2, 1));
		panel1.add(findContentLabel);
		panel1.add(findText);
		panel1.add(findNextButton);
		panel4.add(replaceButton);
		panel4.add(replaceAllButton);
		panel2.add(replaceLabel);
		panel2.add(replaceText);
		panel2.add(panel4);
		panel3.add(matchCheckBox);
		panel3.add(directionPanel);
		panel3.add(cancel);
		con.add(panel1);
		con.add(panel2);
		con.add(panel3);
		replaceDialog.setSize(420, 220);
		replaceDialog.setResizable(false);// ���ɵ�����С
		replaceDialog.setLocation(230, 280);
		replaceDialog.setVisible(true);
	}
}
