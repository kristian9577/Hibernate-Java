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

        List<Minion> minions = new ArrayList<Minion>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            minions.add(new Minion(resultSet.getString("name")));
        }

        for (int i = 0; i < minions.size() / 2; i++) {
            System.out.println(minions.get(i).name);
            System.out.println(minions.get(minions.size() - 1 - i).name);
        }


        if((double)minions.size() % 2 != 0){
            System.out.println(minions.get((int)Math.ceil(minions.size() / 2)).name);
        }
    }
}
