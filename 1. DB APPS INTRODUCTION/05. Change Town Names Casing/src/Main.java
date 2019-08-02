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

        System.out.println("Enter a country:");
        String countryToUpdate = sc.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ? AND name NOT LIKE BINARY UPPER(name) ");
        preparedStatement.setString(1, countryToUpdate);

        int affectedRows = preparedStatement.executeUpdate();

        if(affectedRows > 0){
            preparedStatement = connection.prepareStatement("SELECT name FROM towns WHERE country = ?");
            preparedStatement.setString(1, countryToUpdate);
            ResultSet updatedCountriesRS = preparedStatement.executeQuery();

            String result = "[";
            while(updatedCountriesRS.next()){
                result += updatedCountriesRS.getString("name") + ", ";
            }
            result = result.substring(0, result.length() - 2);
            result += "]";

            System.out.println(affectedRows + " town names were affected.");
            System.out.println(result);
        }
        else{
            System.out.println("No town names were affected");
        }

    }
}

