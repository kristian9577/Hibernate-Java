import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username default (root): ");
        String user = sc.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.println();

        System.out.print("Enter password default (empty):");
        String password = sc.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db?useSSL=false", props);

        PreparedStatement preparedStatement = connection.prepareStatement("call usp_get_older(?)");
        System.out.println("Enter minion ID: ");
        int minionId = sc.nextInt();

        preparedStatement.setInt(1, minionId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("name") + " " + resultSet.getString("age"));
        }

    }
}
