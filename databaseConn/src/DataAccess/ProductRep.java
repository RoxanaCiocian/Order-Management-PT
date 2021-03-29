package DataAccess;

import Model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductRep {
    Statement productStatement = null;

    /**
     * This method takes the elements from the current table, adding them into an array list of type product.
     * @return
     */
    public ArrayList<Product> getProductsList(){
        Connection myCon = null;
        ArrayList<Product> productsList = new ArrayList<Product>();
        ResultSet rs = null;
        try {
            myCon = ConnectionFactory.getConnection();
            productStatement = myCon.createStatement();
            rs = productStatement.executeQuery("select * from product");
            while (rs.next()) {
                Product newProduct = new Product(rs.getInt("idProduct"), rs.getString("name"), rs.getInt("quantity"), rs.getFloat("price"));
                productsList.add(newProduct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getProductList Error");
        }
        finally {
            ConnectionFactory.close(myCon);
            ConnectionFactory.close(productStatement);
            ConnectionFactory.close(rs);
        }
        return productsList;
    }

    /**
     * In this method we have the necessary query for inserting a new product into the database
     * @return
     */
    public static String insertNewProduct(){
        String s = "insert into product(idProduct, name,quantity,price) VALUES(?,?,?,?)";
        return s;
    }

    /**
     * In this method we have the necessary query for deleting a product from the database
     * @return
     */
    public static String deleteProduct(){
        String s = "delete from product where idProduct=?";
        return s;
    }

    /**
     * In this method we have the necessary query for updating a certain product's quantity
     * @return
     */
    public static String updateQuantity(){
        String s = "update product set quantity=? where idProduct=?";
        return s;
    }

}
