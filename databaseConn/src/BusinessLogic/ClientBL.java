package BusinessLogic;

import DataAccess.ClientRep;
import DataAccess.ConnectionFactory;
import Model.Client;
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

public class ClientBL{
    private ClientRep newClient;
    PreparedStatement st = null;

    public ClientBL(){
        this.newClient = new ClientRep();
    }

    /**
     * The array of clients is created here, using the method for taking all of the clients from the table, from the ClientRep class.
     * @return
     */
    public ArrayList<Client> allClients(){
        ArrayList<Client> clients = new ArrayList<>();
        clients = newClient.getClientsList();
        if (clients == null)
            System.out.println("ERROR - making the clients list");
        return clients;
    }

    /**
     * In this method, we design the format for the Client reports. Those reports will be generated as a PDF files and they should contain a table with 3 columns,
     * corresponding to the Client table, and the table will contain the data extracted from the table.
     * @param clients
     * @param noRep
     */
    public void createReport(ArrayList<Client> clients,int noRep){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream("ClientReport_" + noRep + ".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(3);
            PdfPCell c1 = new PdfPCell(new Phrase("idClient"));
            c1.setHorizontalAlignment((Element.ALIGN_CENTER));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("name"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("city"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);

            for (Client client : clients) {
                table.addCell(Integer.toString(client.getClient_id()));
                table.addCell(client.getClientName());
                table.addCell(client.getCity());
            }
            document.add(table);
            document.close();
        }catch (FileNotFoundException | DocumentException e){
            e.getStackTrace();
            System.out.println("ERROR - ClientBL Report");
        }
    }

    /**
     * This method inserts a client into the database, using the specific query method for inserting from ClientRep. It takes as a parameter an object of type client
     * and sets its value into the table.
     * @param client
     */
    public void insertClient(Client client){
        Connection con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(ClientRep.insertNewClient());
            st.setInt(1,client.getClient_id());
            st.setString(2,client.getClientName());
            st.setString(3,client.getCity());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(con);
        ConnectionFactory.close(st);
    }

    /**
     * This method is used for deletin a certain client from the database, usong the specific query for deleting from ClientRep. It takes as a pasameter an object of
     * type client, gets the positon of that client in the table by ts id, and after that, deleting that client by id.
     * @param client
     */
    public void deleteClient(Client client){
        Connection con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(ClientRep.deleteClient());
            st.setInt(1, client.getClient_id());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(con);
        ConnectionFactory.close(st);
    }

}

