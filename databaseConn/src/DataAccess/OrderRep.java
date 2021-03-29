package DataAccess;

import Model.Order;
import Model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderRep {
    Statement orderStatement = null;

    /**
     * This method takes the elements from the current table, adding them into an array list of type order.
     * @return
     */
    public ArrayList<Order> getOrdersList(){
        Connection myCon = null;
        ArrayList<Order> ordersList = new ArrayList<Order>();
        ResultSet rs = null;
        try {
            myCon = ConnectionFactory.getConnection();
            orderStatement = myCon.createStatement();
            rs = orderStatement.executeQuery("select * from `order`");
            while (rs.next()) {
                Order newOrder = new Order(rs.getInt("idOrder"), rs.getString("name"), rs.getString("product"), rs.getInt("quantity"));
                ordersList.add(newOrder);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getOrdersList Error");
        }
        finally {
            ConnectionFactory.close(myCon);
            ConnectionFactory.close(orderStatement);
            ConnectionFactory.close(rs);
        }
        return ordersList;
    }

    /**
     * Here is a method containing a query which inserts a new order into the database
     * @return
     */
    public static String insertNewOrder(){
        String s = "insert into `order`(idOrder,name,product,quantity) VALUES(?,?,?,?)";
        return s;
    }

}
