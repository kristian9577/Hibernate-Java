import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        PreparedStatement statement =
                connection.prepareStatement("SELECT v.name, COUNT(v.name) AS `minions_count` FROM villains AS `v`\n" +
                        "JOIN minions_villains AS `mv`\n" +
                        "ON mv.villain_id = v.id\n" +
                        "GROUP BY v.name\n" +
                        "HAVING `minions_count` > 3\n" +
                        "ORDER BY `minions_count` DESC;");


        ResultSet resultSet = statement.executeQuery();

        List<Villain> villains = new ArrayList<Villain>();

        while(resultSet.next()){
            Villain villain = new Villain(resultSet.getString("name"), resultSet.getLong("minions_count"));
            villains.add(villain);
        }

        villains.forEach(villain -> System.out.println(villain.name + " " + villain.minionsCount));
    }
}
