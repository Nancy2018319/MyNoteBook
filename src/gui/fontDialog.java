package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * ���������÷�����װ���࣬�������
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class fontDialog extends JDialog {
	public fontDialog() {
		super();
	}

	/**
	 * fontʵ�������ı��༭��������
	 * 
	 * @param editArea �ı��༭��
	 */
	public void font(JTextArea editArea) {
		final JDialog fontDialog = new JDialog(this, "��������", false);
		Container con = fontDialog.getContentPane();
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel fontLabel = new JLabel("����(F)��");
		fontLabel.setPreferredSize(new Dimension(100, 20));// ����һ��Dimension���������ʼ��Ϊָ����Ⱥ͸߶�
		JLabel styleLabel = new JLabel("����(Y)��");
		styleLabel.setPreferredSize(new Dimension(100, 20));
		JLabel sizeLabel = new JLabel("��С(S)��");
		sizeLabel.setPreferredSize(new Dimension(100, 20));
		final JLabel sample = new JLabel("Nancy�ļ��±�-ZXZ's Notepad");
		// sample.setHorizontalAlignment(SwingConstants.CENTER);
		final JTextField fontText = new JTextField(9);
		fontText.setPreferredSize(new Dimension(200, 20));
		final JTextField styleText = new JTextField(8);
		styleText.setPreferredSize(new Dimension(200, 20));
		final int style[] = { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC };
		final JTextField sizeText = new JTextField(5);
		sizeText.setPreferredSize(new Dimension(200, 20));
		JButton okButton = new JButton("ȷ��");
		JButton cancel = new JButton("ȡ��");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontDialog.dispose();
			}
		});
		Font currentFont = editArea.getFont();
		fontText.setText(currentFont.getFontName());
		fontText.selectAll();
		// styleText.setText(currentFont.getStyle());
		// styleText.selectAll();
		if (currentFont.getStyle() == Font.PLAIN)
			styleText.setText("����");
		else if (currentFont.getStyle() == Font.BOLD)
			styleText.setText("����");
		else if (currentFont.getStyle() == Font.ITALIC)
			styleText.setText("б��");
		else if (currentFont.getStyle() == (Font.BOLD + Font.ITALIC))
			styleText.setText("��б��");
		styleText.selectAll();
		String str = String.valueOf(currentFont.getSize());
		sizeText.setText(str);
		sizeText.selectAll();
		final JList fontList, styleList, sizeList;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final String fontName[] = ge.getAvailableFontFamilyNames();
		fontList = new JList(fontName);
		fontList.setFixedCellWidth(86);
		fontList.setFixedCellHeight(20);
		fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final String fontStyle[] = { "����", "����", "б��", "��б��" };
		styleList = new JList(fontStyle);
		styleList.setFixedCellWidth(86);
		styleList.setFixedCellHeight(20);
		styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if (currentFont.getStyle() == Font.PLAIN)
			styleList.setSelectedIndex(0);
		else if (currentFont.getStyle() == Font.BOLD)
			styleList.setSelectedIndex(1);
		else if (currentFont.getStyle() == Font.ITALIC)
			styleList.setSelectedIndex(2);
		else if (currentFont.getStyle() == (Font.BOLD + Font.ITALIC))
			styleList.setSelectedIndex(3);
		final String fontSize[] = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36",
				"48", "72" };
		sizeList = new JList(fontSize);
		sizeList.setFixedCellWidth(43);
		sizeList.setFixedCellHeight(20);
		sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fontList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				fontText.setText(fontName[fontList.getSelectedIndex()]);
				fontText.selectAll();
				Font sampleFont1 = new Font(fontText.getText(), style[styleList.getSelectedIndex()],
						Integer.parseInt(sizeText.getText()));
				sample.setFont(sampleFont1);
			}
		});
		styleList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int s = style[styleList.getSelectedIndex()];
				styleText.setText(fontStyle[s]);
				styleText.selectAll();
				Font sampleFont2 = new Font(fontText.getText(), style[styleList.getSelectedIndex()],
						Integer.parseInt(sizeText.getText()));
				sample.setFont(sampleFont2);
			}
		});
		sizeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				sizeText.setText(fontSize[sizeList.getSelectedIndex()]);
				// sizeText.requestFocus();
				sizeText.selectAll();
				Font sampleFont3 = new Font(fontText.getText(), style[styleList.getSelectedIndex()],
						Integer.parseInt(sizeText.getText()));
				sample.setFont(sampleFont3);
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Font okFont = new Font(fontText.getText(), style[styleList.getSelectedIndex()],
						Integer.parseInt(sizeText.getText()));
				editArea.setFont(okFont);
				fontDialog.dispose();
			}
		});
		JPanel samplePanel = new JPanel();
		samplePanel.setBorder(BorderFactory.createTitledBorder("ʾ��"));
		// samplePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		samplePanel.add(sample);
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel2.add(fontText);
		panel2.add(styleText);
		panel2.add(sizeText);
		panel2.add(okButton);
		panel3.add(new JScrollPane(fontList));// JList��֧��ֱ�ӹ���������Ҫ��JList��ΪJScrollPane���ӿ���ͼ
		panel3.add(new JScrollPane(styleList));
		panel3.add(new JScrollPane(sizeList));
		panel3.add(cancel);
		con.add(panel1);
		con.add(panel2);
		con.add(panel3);
		con.add(samplePanel);
		fontDialog.setSize(350, 340);
		fontDialog.setLocation(200, 200);
		fontDialog.setResizable(false);
		fontDialog.setVisible(true);
	}
}
