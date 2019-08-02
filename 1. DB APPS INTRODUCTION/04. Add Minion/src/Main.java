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
        System.out.println("Enter a minion name, age and town (String Int String):");
        String[] minionInfoInput = sc.nextLine().split(" ");
        System.out.println("Enter villain name (String): ");
        String villainNameInput = sc.nextLine();
        if(isTownValid(connection, minionInfoInput[2]) == false){
            insertTown(connection, minionInfoInput[2]);
        }
        if(isVillainValid(connection, villainNameInput) == false){
            insertVillain(connection, villainNameInput);
        }
        insertMinion(connection, minionInfoInput[0], Integer.parseInt(minionInfoInput[1]), minionInfoInput[2], villainNameInput);
        connection.commit();
    }

    private static boolean isTownValid(Connection connection, String town) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM towns WHERE name = ?");

        preparedStatement.setString(1, town);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return true;
        }
        else{
            return false;
        }
    }

    private static boolean isVillainValid(Connection connection, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE name = ?;");

        preparedStatement.setString(1,villainName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return true;
        }
        else{
            return false;
        }
    }

    private static void insertTown(Connection connection, String townName){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO towns(name) VALUES (?);");
            preparedStatement.setString(1, townName);
            preparedStatement.executeUpdate();
            System.out.println("Town " + townName + " was added to the database.");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void insertVillain(Connection connection, String villainName){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO villains(name, evilness_factor) VALUES(?, ?);");
            preparedStatement.setString(1, villainName);
            preparedStatement.setString(2, "evil");
            preparedStatement.executeUpdate();
            System.out.println("Villain " + villainName + " was added to the database");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void insertMinion(Connection connection, String minionName, int minionAge, String minionTown, String villainName){
        try{
            final String sqlMinion = "INSERT into minions(name, age, town_id) VALUES(?, ?, (SELECT t.id FROM towns t WHERE t.name = ?)); ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlMinion, Statement.RETURN_GENERATED_KEYS);


            preparedStatement.setString(1, minionName);
            preparedStatement.setInt(2, minionAge);
            preparedStatement.setString(3, minionTown);

            preparedStatement.executeUpdate();
            ResultSet resultSetMinionId = preparedStatement.getGeneratedKeys();
            int lastMinionId = 0;
            while(resultSetMinionId.next()){
                lastMinionId = resultSetMinionId.getInt(1);
                break;
            }

            preparedStatement = connection.prepareStatement("INSERT INTO minions_villains(minion_id, villain_id) VALUES (?, (SELECT v.id FROM villains v WHERE v.name = ?));");
            preparedStatement.setInt(1, lastMinionId);
            preparedStatement.setString(2, villainName);
            preparedStatement.executeUpdate();

            System.out.println("Successfully added " + minionName + " to be minion of " + villainName + ".");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
