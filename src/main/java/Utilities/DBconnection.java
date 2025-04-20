package Utilities;

import java.io.IOException;
import java.sql.*;

import static Utilities.PlaywrightFactory.env;

public class DBconnection {

    public ResultSet reteiveDBInformation(String sqlquery) throws SQLException, IOException {

        ConfigHandler configHandler = new ConfigHandler();

        String dbip = configHandler.getPropertyValue(env + "dbip");
        String dbport = configHandler.getPropertyValue(env + "dbport");
        String dbservice = configHandler.getPropertyValue(env + "dbservice");
        String dbusername = configHandler.getPropertyValue(env + "dbusername");
        String dbpassword = configHandler.getPropertyValue(env + "dbpassword");
        String url = "jdbc:oracle:thin:@" + dbip + ":" + dbport + "/" + dbservice;
        Connection connection = DriverManager.getConnection(url, dbusername, dbpassword);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(sqlquery);
        return res;
    }
}
