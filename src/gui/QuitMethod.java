package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * 将退出的方法进行封装，方便调用
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class QuitMethod extends JDialog {
	/**
	 * 退出函数,退出时进行判断是否需要保存
	 * 
	 * @param editArea 文本编辑区
	 * @param oldValue 存放编辑区原来的内容
	 * @param currentFile 当前文件名
	 * @param isNewFile 是否新文件
	 * @param statusLabel 状态栏标签
	 */
	public void exitWindowChoose(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile,
			JLabel statusLabel) {
		editArea.requestFocus();
		String currentValue = editArea.getText();
		if (currentValue.equals(oldValue) == true) {
			System.exit(0);
		} else {
			int exitChoose = JOptionPane.showConfirmDialog(this, "您的文件尚未保存，是否保存？", "退出提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (exitChoose == JOptionPane.YES_OPTION) { // boolean isSave=false;
				if (isNewFile) {
					String str = null;
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChooser.setApproveButtonText("确定");
					fileChooser.setDialogTitle("另存为");
					int result = fileChooser.showSaveDialog(this);
					if (result == JFileChooser.CANCEL_OPTION) {
						statusLabel.setText("　您没有保存文件");
						return;
					}
					File saveFileName = fileChooser.getSelectedFile();
					if (saveFileName == null || saveFileName.getName().equals("")) {
						JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							FileWriter fw = new FileWriter(saveFileName);
							BufferedWriter bfw = new BufferedWriter(fw);
							bfw.write(editArea.getText(), 0, editArea.getText().length());
							bfw.flush();
							fw.close();
							isNewFile = false;
							currentFile = saveFileName;
							oldValue = editArea.getText();
							this.setTitle(saveFileName.getName() + "  - 记事本");
							statusLabel.setText("　当前打开文件:" + saveFileName.getAbsoluteFile());
							// isSave=true;
						} catch (IOException ioException) {
						}
					}
				} else {
					try {
						FileWriter fw = new FileWriter(currentFile);
						BufferedWriter bfw = new BufferedWriter(fw);
						bfw.write(editArea.getText(), 0, editArea.getText().length());
						bfw.flush();
						fw.close();
						// isSave=true;
					} catch (IOException ioException) {
					}
				}
				System.exit(0);
				// if(isSave)System.exit(0);
				// else return;
			} else if (exitChoose == JOptionPane.NO_OPTION) {
				System.exit(0);
			} else {
				return;
			}
		}
	}
}
