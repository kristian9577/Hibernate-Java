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

        connection.setAutoCommit(false);

        System.out.println("Villain id: ");
        int villaindId = sc.nextInt();
        int releasedMinions = 0;


        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villaindId);
        ResultSet villainNameRS = preparedStatement.executeQuery();
        villainNameRS.next();
        String name = villainNameRS.getString("name");
        if(name.equals("")){
            System.out.println("No such villain was found");
        }
        else{
            preparedStatement = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
            preparedStatement.setInt(1, villaindId);
            releasedMinions = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM villains WHERE id = ?");
            preparedStatement.setInt(1, villaindId);
            preparedStatement.executeUpdate();

            connection.commit();

            System.out.println(name + " was deleted");
            System.out.println(releasedMinions + " minions released");
        }



    }
}
