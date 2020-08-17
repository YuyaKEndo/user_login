package kadai;


//SQLに関連したクラスライブラリをインポート
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDbaccess {
	protected int num;
	protected int user_id = -1;
	protected String user_name;
	protected String user_password;
	protected String user_mail;



	public boolean  userRegister(String name, String password, String mail)throws Exception{//ユーザ登録用のメソッド
		num=0;
		Class.forName("com.mysql.jdbc.Driver").newInstance(); //com.mysql.jdbc.Drive
		String url="jdbc:mysql://localhost/users?serverTimezone=JST";
		Connection conn = DriverManager.getConnection(url, "root", "");
		String sql2 = "select * from users where user_mail = ?";
		PreparedStatement stmt2 =conn.prepareStatement(sql2);
		stmt2.setString(1,mail);
		ResultSet rs2 = stmt2.executeQuery();
		while(rs2.next()) {
			num+=1;
		}
		if (num==0) {
			String sql = "INSERT INTO users(user_name,user_password,user_mail)VALUES(?,?,?)";
			PreparedStatement stmt =conn.prepareStatement(sql);
			stmt.setString(1,name);
			stmt.setString(2, password);
			stmt.setString(3,mail);
			stmt.executeUpdate();
			stmt.close();
			rs2.close();
			stmt2.close();
			conn.close();
			return true;
		}else {
			stmt2.close();
			rs2.close();
			conn.close();
			return false;

		}


	}
	public List<String> userLogin(String mail,String password)throws Exception{//ユーザログイン用のメソッド
		List<String> result = new ArrayList<String>();
		Class.forName("com.mysql.jdbc.Driver").newInstance(); //com.mysql.jdbc.Drive
		String url="jdbc:mysql://localhost/users?serverTimezone=JST";
		Connection conn = DriverManager.getConnection(url, "root", "");
		String sql = "select * from users where user_mail = ?";
		PreparedStatement stmt =conn.prepareStatement(sql);
		stmt.setString(1,mail);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			user_id = rs.getInt("user_id");
			user_name=rs.getString("user_name");
			user_password = rs.getString("user_password");
			user_mail=rs.getString("user_mail");
		}


		if (user_password.equals(password)){
		    rs.close();
			stmt.close();
			conn.close();
			if(user_id == -1){
			    rs.close();
				stmt.close();
				conn.close();
				return result;
			}
			result.add(user_name);
			result.add(user_password);
			result.add(user_mail);
			result.add(String.valueOf(user_id));
			return result;
		}else {
		    rs.close();
			stmt.close();
			conn.close();
			return result;
		}

	}
}
