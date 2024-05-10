package database;

	import java.sql.*;
import java.util.ArrayList;

import model.Person;

	public class DatabaseHandler {
	    private Connection connect() {
	        String url = "jdbc:mysql://localhost:3306/apphoctoan";
	        String user = "root";
	        String password = "";

	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(url, user, password);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	    }

	    public boolean addUser(String accountName, String password) {
	        String sql = "INSERT INTO nguoidung(tentaikhoan, matkhau, diem,deso) VALUES(?,?,0,0)";

	        try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, accountName);
	            pstmt.setString(2, password);
	            int result = pstmt.executeUpdate();
	            return result > 0;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return false;
	        }
	    }
	    

	    
	    public boolean verifyUser(String accountName, String password) {
	        String sql = "SELECT * FROM nguoidung WHERE tentaikhoan = ? AND matkhau = ?";

	        try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, accountName);
	            pstmt.setString(2, password); // Lưu ý: Trong thực tế nên so sánh mật khẩu đã mã hóa
	            ResultSet rs = pstmt.executeQuery();
	            return rs.next(); // Nếu có kết quả, trả về true
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return false;
	        }
	    }
	 // ...

	    public boolean updateUserScore(String accountName, Double score, int testNumber) {
	        String sql = "UPDATE nguoidung SET diem = ?, deso = ? WHERE tentaikhoan = ?";

	        try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setDouble(1, score);
	            pstmt.setInt(2, testNumber);
	            pstmt.setString(3, accountName);
	            int result = pstmt.executeUpdate();
	            return result > 0;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return false;
	        }
	    }
	    public ArrayList<Person> getUsers() {
	        ArrayList<Person> users = new ArrayList<>();
	        String sql = "SELECT * FROM nguoidung";

	        try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                String accountName = rs.getString("tentaikhoan");
	                String password = rs.getString("matkhau");
	                Double totalScore = rs.getDouble("diem");
	                int testNumber = rs.getInt("deso");
	                Person user = new Person(accountName, totalScore,testNumber);
	                users.add(user);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }

	        return users;
	        }

		

	}



