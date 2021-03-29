package BusinessLogic;


import DataAccess.ConnectionFactory;
import DataAccess.ProductRep;
import Model.Product;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBL {
    private ProductRep newProduct;
    PreparedStatement st = null;
    public ProductBL(){
        this.newProduct = new ProductRep();
    }

    /**
     * The array of products is created here, using the method for taking all of the products from the table, from the ProductRep class.
     * @return
     */
     public ArrayList<Product> allProducts(){
        ArrayList<Product> products = new ArrayList<>();
        products = newProduct.getProductsList();
        if (products == null)
            System.out.println("ERROR - making the products list");
        return products;
     }

    /**
     * In this method, we design the format for the Product reports. Those reports will be generated as a PDF files and they should contain a table with 4 columns,
     * corresponding to the Product table, such as id of the product, the name, its available quantity and its price.The table will contain the data extracted
     * from the table.
     * @param products
     * @param noRep
     */
    public void createReport(ArrayList<Product> products, int noRep){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream("ProductReport_" + noRep + ".pdf"));
            document.open();

            PdfPTable table = new PdfPTable(4);

            PdfPCell c1 = new PdfPCell(new Phrase("idProduct"));
            c1.setHorizontalAlignment((Element.ALIGN_CENTER));
            table.addCell(c1);


            c1 = new PdfPCell(new Phrase("productName"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("price"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("quantity"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);

            for(Product product: products ){
                table.addCell(Integer.toString(product.getProduct_id()));
                table.addCell(product.getProductName());
                table.addCell(Float.toString(product.getPrice()));
                table.addCell(Integer.toString(product.getProductQuantity()));
            }
            document.add(table);
            document.close();
        }catch (FileNotFoundException | DocumentException e){
            e.getStackTrace();
            System.out.println("ERROR - ProductBL Report");
        }

    }

    /**
     * This method is used for inserting a product into the database. Also, it requires the specific insert query from the ProductRep class. It takes as a parameter
     * an object of type product and sets its value into the table.
     * @param product
     */
    public void insertProduct(Product product){
        Connection con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(ProductRep.insertNewProduct());
            st.setInt(1, product.getProduct_id());
            st.setString(2,product.getProductName());
            st.setInt(3,product.getProductQuantity());
            st.setFloat(4, product.getPrice());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(con);
        ConnectionFactory.close(st);
    }

    /**
     * Through this method, a certain product is deleted form the database by its id. In order to delete we use the specific delete query from the ProductRep class.
     * It takes an object of type product and get its id for deleting it after.
     * @param product
     */
    public void deleteProd(Product product){
        Connection con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(ProductRep.deleteProduct());
            st.setInt(1, product.getProduct_id());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(con);
        ConnectionFactory.close(st);
    }

    /**
     * Using this method we can update the quantity of a certain product, using the update query from the ProductRep class. It takes as parameters an object of type
     * product and an integer which will represent the number with which the product's quantity will be updated.
     * @param prod
     * @param quant
     */

    public void insertProdEx(Product prod, int quant){
        Connection con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(ProductRep.updateQuantity());
            st.setInt(1, prod.getProductQuantity() + quant);
            st.setInt(2,prod.getProduct_id());
            prod.setProductQuantity(quant + prod.getProductQuantity());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(con);
        ConnectionFactory.close(st);
    }
}


