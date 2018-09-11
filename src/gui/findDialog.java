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
 * 将查找方法封装成类，方便调用
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
	 * find方法实现查找功能
	 * 
	 * @param editArea 文本编辑区
	 */
	public void find(JTextArea editArea) {
		final JDialog findDialog = new JDialog(this, "查找", false);// false时允许其他窗口同时处于激活状态(即无模式)
		Container con = findDialog.getContentPane();// 返回此对话框的contentPane对象
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel findContentLabel = new JLabel("查找内容(N)：");
		final JTextField findText = new JTextField(15);
		JButton findNextButton = new JButton("查找下一个(F)：");
		final JCheckBox matchCheckBox = new JCheckBox("区分大小写(C)");
		ButtonGroup bGroup = new ButtonGroup();
		final JRadioButton upButton = new JRadioButton("向上(U)");
		final JRadioButton downButton = new JRadioButton("向下(U)");
		downButton.setSelected(true);
		bGroup.add(upButton);
		bGroup.add(downButton);
		/*
		 * ButtonGroup此类用于为一组按钮创建一个多斥（multiple-exclusion）作用域。 使用相同的 ButtonGroup
		 * 对象创建一组按钮意味着“开启”其中一个按钮时，将关闭组中的其他所有按钮。
		 */
		/*
		 * JRadioButton此类实现一个单选按钮，此按钮项可被选择或取消选择，并可为用户显示其状态。 与 ButtonGroup
		 * 对象配合使用可创建一组按钮，一次只能选择其中的一个按钮。 （创建一个 ButtonGroup 对象并用其 add 方法将
		 * JRadioButton 对象包含在此组中。）
		 */
		JButton cancel = new JButton("取消");
		// 取消按钮事件处理
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findDialog.dispose();
			}
		});
		// "查找下一个"按钮监听
		findNextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // "区分大小写(C)"的JCheckBox是否被选中
				int k = 0, m = 0;
				final String str1, str2, str3, str4, strA, strB;
				str1 = editArea.getText();
				str2 = findText.getText();
				str3 = str1.toUpperCase();
				str4 = str2.toUpperCase();
				if (matchCheckBox.isSelected())// 区分大小写
				{
					strA = str1;
					strB = str2;
				} else// 不区分大小写,此时把所选内容全部化成大写(或小写)，以便于查找
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
						JOptionPane.showMessageDialog(null, "找不到您查找的内容！", "查找", JOptionPane.INFORMATION_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "找不到您查找的内容！", "查找", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});// "查找下一个"按钮监听结束
			// 创建"查找"对话框的界面
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel directionPanel = new JPanel();
		directionPanel.setBorder(BorderFactory.createTitledBorder("方向"));
		// 设置directionPanel组件的边框;
		// BorderFactory.createTitledBorder(String
		// title)创建一个新标题边框，使用默认边框（浮雕化）、默认文本位置（位于顶线上）、默认调整 (leading)
		// 以及由当前外观确定的默认字体和文本颜色，并指定了标题文本。
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
		findDialog.setResizable(false);// 不可调整大小
		findDialog.setLocation(230, 280);
		findDialog.setVisible(true);
	}
}
