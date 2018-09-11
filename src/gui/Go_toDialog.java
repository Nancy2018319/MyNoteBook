package gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ��ת��������װ���࣬�������
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class Go_toDialog extends JDialog {

	/**
	 * Go_toMethod����ʵ��ת������
	 * 
	 * @param editArea �ı��༭��
	 */
	public void Go_toMethod(JTextArea editArea) {
		editArea.requestFocus();
		final JDialog gotoDialog = new JDialog(this, "ת��������");
		JLabel gotoLabel = new JLabel("����(L):");
		final JTextField linenum = new JTextField(5);
		linenum.setText("1");
		linenum.selectAll();
		JButton okButton = new JButton("ȷ��");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int totalLine = editArea.getLineCount();
				int[] lineNumber = new int[totalLine + 1];
				String s = editArea.getText();
				int pos = 0, t = 0;
				while (true) {
					pos = s.indexOf('\12', pos);
					// System.out.println("����pos:"+pos);
					if (pos == -1)
						break;
					lineNumber[t++] = pos++;
				}
				int gt = 1;
				try {
					gt = Integer.parseInt(linenum.getText());
				} catch (NumberFormatException efe) {
					JOptionPane.showMessageDialog(null, "����������!", "��ʾ", JOptionPane.WARNING_MESSAGE);
					linenum.requestFocus(true);
					return;
				}
				if (gt < 2 || gt >= totalLine) {
					if (gt < 2)
						editArea.setCaretPosition(0);
					else
						editArea.setCaretPosition(s.length());
				} else
					editArea.setCaretPosition(lineNumber[gt - 2] + 1);
				gotoDialog.dispose();
			}
		});
		JButton cancelButton = new JButton("ȡ��");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoDialog.dispose();
			}
		});
		Container con = gotoDialog.getContentPane();
		con.setLayout(new FlowLayout());
		con.add(gotoLabel);
		con.add(linenum);
		con.add(okButton);
		con.add(cancelButton);
		gotoDialog.setSize(200, 100);
		gotoDialog.setResizable(false);
		gotoDialog.setLocation(300, 280);
		gotoDialog.setVisible(true);
	}
}
