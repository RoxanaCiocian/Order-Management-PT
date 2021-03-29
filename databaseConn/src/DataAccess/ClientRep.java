package DataAccess;

import Model.Client;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientRep {

    private Statement clientStatement;

    /**
     * This method takes the elements from the current table, adding them into an array list of type client.
     * @return
     */
    public ArrayList<Client> getClientsList(){
        Connection myCon = null;
        ArrayList<Client> clientsList = new ArrayList<Client>();
        ResultSet rs = null;
        try {
            myCon = ConnectionFactory.getConnection();
            clientStatement = myCon.createStatement();
            rs = clientStatement.executeQuery("select * from client");
            while (rs.next()) {
                Client newClient = new Client(rs.getInt("idclient"), rs.getString("name"), rs.getString("city"));
                clientsList.add(newClient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getClientList Error");
        }
        finally {
            ConnectionFactory.close(myCon);
            ConnectionFactory.close(clientStatement);
            ConnectionFactory.close(rs);
        }
        return clientsList;
    }

    /**
     * This method contains the query through which we insert the client into the database
     * @return
     */
    public static String insertNewClient(){
        String s = "insert into client(idClient, name, city) values(?,?,?)";
        return s;
    }

    /**
     * This method contains the query through which we delete the client from the database
     * @return
     */
    public static String deleteClient(){
        String s = "delete from client where idClient=?";
        return s;
    }

}
