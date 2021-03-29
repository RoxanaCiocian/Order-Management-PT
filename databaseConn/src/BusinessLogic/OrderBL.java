package BusinessLogic;

import DataAccess.ConnectionFactory;
import DataAccess.OrderRep;
import DataAccess.ProductRep;
import Model.Order;
import Model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBL {
    private OrderRep newOrder;

    PreparedStatement st = null;

    public OrderBL(){
        this.newOrder = new OrderRep();
    }

    public ArrayList<Order> allOrders(){
        /**
         * The array of orders is created here, using the method for getting all of the orders from the table, from the OrderRep clas.
         */
        ArrayList<Order> orders = new ArrayList<>();
        if (newOrder != null) {
            orders = newOrder.getOrdersList();
        }
        if (orders == null)
            System.out.println("ERROR - making the orders list");
        return orders;
    }

    /**
     * Here we design the format of the pdf which will be created in case of an order with too many products than their actual quantity in the database.
     * This method takes as a parameter the name of the product for which the stock is not enough, and writes into the file that the product is under stock.
     * @param productName
     */
    public void underStockProduct(String productName){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream("UnderStockBill.pdf"));
            document.open();

            Paragraph message = new Paragraph("The '"+productName+"' product is under stock - the order cannot be proccessed.");
            document.add(message);

            document.close();
        }catch (FileNotFoundException | DocumentException e){
            e.getStackTrace();
            System.out.println("ERROR - OrdersBL UnderStockBill");
        }
    }

    /**
     * In this method, we design the format for the Order reports. Those reports will be generated as a PDF files and they should contain a table with 4 columns,
     * corresponding to the Order table, and the table will contain the data extracted from the database.
     * @param orders
     * @param noRep
     */
    public void createReport(ArrayList<Order> orders, int noRep){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream("OrderReport_" + noRep + ".pdf"));
            document.open();

            PdfPTable table = new PdfPTable(4);

            PdfPCell c1 = new PdfPCell(new Phrase("idOrder"));
            c1.setHorizontalAlignment((Element.ALIGN_CENTER));
            table.addCell(c1);


            c1 = new PdfPCell(new Phrase("client"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("product"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("quantity"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);

            for(Order order : orders){
                table.addCell(Integer.toString(order.getOrder_id()));
                table.addCell(order.getOrderName());
                table.addCell(order.getProduct());
                table.addCell(Integer.toString(order.getQuantity()));
            }
            document.add(table);
            document.close();
        }catch (FileNotFoundException | DocumentException e){
            e.getStackTrace();
            System.out.println("ERROR - ProductBL Report");
        }

    }

    /**
     * In this method, we design the format of the bill. Those bills will be generated as a PDF files containing specific data about the order, such as the name of
     * the client, the name of the product, its quantity and also, the total price for this order.
     * @param order
     * @param noBill
     */
    public void createBill(Order order, int noBill){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream("OrderBill_" + order.getOrderName()+ noBill + ".pdf"));
            document.open();

            Paragraph message = new Paragraph("The client: "+ order.getOrderName() + "\n");
            document.add(message);

            message = new Paragraph("The product: "+ order.getProduct() +"\n");
            document.add(message);

            message = new Paragraph("Ordered quantity: "+ order.getQuantity() + "\n");
            document.add(message);

            message = new Paragraph("Total price:: " + order.getTotalPrice());
            document.add(message);

            document.close();
        }catch (FileNotFoundException | DocumentException e){
            e.getStackTrace();
            System.out.println("ERROR - OrderBL Bill");
        }

    }

    /**
     * In this method, the quantity of a product which appears into one or more orders, will be decremeneted corresponding to the quantity requested from the order.
     * @param prod
     * @param quant
     */

    public void orderProdEx(Product prod, int quant){
        Connection con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(ProductRep.updateQuantity());
            st.setInt(1, prod.getProductQuantity() - quant);
            st.setInt(2,prod.getProduct_id());
            prod.setProductQuantity(quant - prod.getProductQuantity());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(con);
        ConnectionFactory.close(st);
    }

    /**
     * In this method, the order is created usong the specific query method from the OrderRep class. Here are set the elements of an object of type order, such as
     * id, name of the client, name of the product and desired quantity.
     * @param order
     */
    public void createOrder(Order order){
        Connection con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(OrderRep.insertNewOrder());
            st.setInt(1,order.getOrder_id());
            st.setString(2,order.getOrderName());
            st.setString(3, order.getProduct());
            st.setInt(4,order.getQuantity());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(con);
        ConnectionFactory.close(st);
    }

}
