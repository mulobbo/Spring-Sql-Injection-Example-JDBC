package sqlinjection.example.demo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import sqlinjection.example.demo.entities.Login;

@Service
public class LoginService {

	private static final String POSTGRESQL_URL_SERVER = "jdbc:postgresql://localhost:5432/sqlinjectionexample?user=postgres&password=password";

	// Sql injection yapilamayan guvenli yol
	public Boolean securedLogIn(Login login) {
		boolean success = false;
		try {
			Connection conn = DriverManager.getConnection(POSTGRESQL_URL_SERVER);

			String sql = "select * from members where username= ?  and password= ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, login.getUsername());
			preparedStatement.setString(2, login.getPassword());

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				success = true;

			} else {
				success = false;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return success;
	}

	// Sql injection yapılabilen guvensiz yol
	public boolean unsecuredLogIn(Login login) {
		boolean success = false;
		try {
			Connection conn = DriverManager.getConnection(POSTGRESQL_URL_SERVER);
			Statement stmt = conn.createStatement();

			String sql = "select * from members where username='" + login.getUsername() + "' and password='"
					+ login.getPassword() + "'";

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				success = true;

			} else {
				success = false;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return success;

	}

	//Yeni uye icin kayit metodu
	public void createMember(Login login) {
		try {
			Connection conn = DriverManager.getConnection(POSTGRESQL_URL_SERVER);
			String sql = "insert into members (username,password) values(?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, login.getUsername());
			preparedStatement.setString(2, login.getPassword());
			preparedStatement.executeQuery();
			System.out.println("kayit basarili");
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

}
