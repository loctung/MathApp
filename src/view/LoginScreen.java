package view;

import javax.swing.*;
import database.DatabaseHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton registerButton;

	private DatabaseHandler dbHandler; // Tạo thể hiện của DatabaseHandler

	public LoginScreen(DatabaseHandler dbHandler) {
		this.dbHandler = dbHandler; // Lưu trữ tham chiếu đến DatabaseHandler
		initializeUI(); // Gọi phương thức để khởi tạo UI
	}

	private void initializeUI() {
		setTitle("Đăng Nhập / Đăng Ký");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);

		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton("Đăng nhập");
		registerButton = new JButton("Đăng ký");

		// Sử dụng GridBagLayout để sắp xếp các thành phần
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;

		// Thêm trường tên tài khoản
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(new JLabel("Tên tài khoản:"), constraints);

		constraints.gridx = 1;
		add(usernameField, constraints);

		// Thêm trường mật khẩu
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(new JLabel("Mật khẩu:"), constraints);

		constraints.gridx = 1;
		add(passwordField, constraints);

		// Thêm nút đăng nhập và đăng ký
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		add(buttonPanel, constraints);

		// Thêm sự kiện cho nút Đăng nhập
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogin();
			}
		});

		// Thêm sự kiện cho nút Đăng ký
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleRegister();
			}
		});

		setVisible(true);
	}

	private void handleLogin() {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());

		// Sử dụng DatabaseHandler để kiểm tra thông tin đăng nhập
		if (dbHandler.verifyUser(username, password)) {
			// Đăng nhập thành công
			JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
			// Chuyển đến màn hình chính hoặc đóng cửa sổ đăng nhập
			setVisible(false);
			new MainScreen(username);
		} else {
			// Đăng nhập thất bại
			JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleRegister() {
		// Logic cho việc mở form đăng ký hoặc xử lý đăng ký
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());

		// Kiểm tra input hợp lệ, rồi...
		boolean success = dbHandler.addUser(username, password);
		if (success) {
			JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
		} else {
			JOptionPane.showMessageDialog(this, "Đăng ký không thành công.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}
}
