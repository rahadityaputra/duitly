package duitly.Database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
  private static String jdbc_driver;
  private static String databaseName;
  private static String databasePort;
  private static String databaseURL;
  private static String databaseUser;
  private static String userPassword;
  static Connection conn;

  public static Connection Connect() {
    Dotenv dotenv = Dotenv.load();
    jdbc_driver = "com.mysql.cj.jdbc.Driver";
    databaseName = dotenv.get("DATABASE_NAME");
    databasePort = dotenv.get("DATABASE_PORT");
    databaseURL = "jdbc:mysql://localhost:" + databasePort + "/" + databaseName;
    databaseUser = dotenv.get("DATABASE_USER");
    userPassword = dotenv.get("USER_PASSWORD");
    try {
      Class.forName(jdbc_driver);
      conn = DriverManager.getConnection(databaseURL, databaseUser, userPassword);
    } catch (ClassNotFoundException | SQLException exception) {
      System.out.println("Connection Failed: " + exception.getLocalizedMessage());
    }
    return conn;
  }
}
