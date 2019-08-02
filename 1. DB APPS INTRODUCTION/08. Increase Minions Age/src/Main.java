import java.sql.*;
import java.util.Arrays;
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

        System.out.println("Minions ID: ");
        int[] minionsId = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE minions SET age = age + 1, name = CONCAT(UPPER(substring(name, 1, 1)), substring(name, 2)) WHERE id = ?;");

        for (int i = 0; i < minionsId.length; i++) {
            preparedStatement.setInt(1,minionsId[i]);
            preparedStatement.executeUpdate();
        }

        preparedStatement = connection.prepareStatement("SELECT name, age FROM minions");

        ResultSet allMinionsRS = preparedStatement.executeQuery();

        while(allMinionsRS.next()){
            System.out.println(allMinionsRS.getString("name") + " " + allMinionsRS.getString("age"));
        }

    }
}
