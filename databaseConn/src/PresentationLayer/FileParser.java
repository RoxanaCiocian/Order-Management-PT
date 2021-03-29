package PresentationLayer;

import BusinessLogic.ProductBL;
import BusinessLogic.*;

import Model.Client;
import Model.Order;
import Model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParser {
    int idClient = 0;
    int idProduct = 0;
    int idOrder = 0;
    int noBill = 1;
    int orderIdClient = 0; int orderIdProduct = 0; float orderPriceProd = 0; int orderQuantProd = 0;
    ClientBL newClient = new ClientBL();
    ArrayList<Client> clients = newClient.allClients();
    int clientNoRep = 1;
    ProductBL newProduct = new ProductBL();
    ArrayList<Product> products = newProduct.allProducts();
    int productNoRep = 1;
    OrderBL newOrder = new OrderBL();
    ArrayList<Order> orders = newOrder.allOrders();
    int ordertNoRep = 1;
    Client client1;
    Product product1;
    public FileParser(){}

    /**
     * Here is the method used to insert a new client by its name and city. We call the insert method from the ClientBL class in order to insert the client into the
     * database, and also add this new client in the clients array list.
     * @param name
     * @param city
     */
    private void insC(String name, String city){
        idClient++;
        Client client = new Client(idClient,name, city);
        newClient.insertClient(client);
        clients.add(client);
    }

    /**
     * Here is the method used to delete a client by its name and city. We call the delete method from the ClientBL class in order to delete the client from
     * the database, and also we need to remove it from the client array list too.
     * @param name
     * @param city
     */
    private void delC(String name, String city) {
        int cl = 0;
        for (Client client : clients ){
            if (client.getClientName().equals(name) && client.getCity().equals(city)){
                cl = client.getClient_id();
                newClient.deleteClient(client);
            }
        }
        clients.remove(cl-1);
    }

    /**
     * Here is the method used to insert a new product by its name, available quantity and its price. We call the insert method from the ProductBL class in order to
     * insert the product into the database. Also, when we insert a new product, we check if it already exists in the table, going through all products list. If it is,
     * we update its quantity with the current one, but if it does not exist in the database, we insert it and add it to the products list.
     * @param name
     * @param quantity
     * @param price
     */
    private void insP(String name, int quantity, float price){
        int oldProd = 0;
        for (Product product : products){
            if (product.getProductName().equals(name)) {
                newProduct.insertProdEx(product, quantity);
                oldProd = 1;
            }
        }
        if (oldProd == 0){
            idProduct++;
            Product product = new Product(idProduct,name, quantity, price);
            newProduct.insertProduct(product);
            products.add(product);
        }
    }

    /**
     * Here is the method used to delete a certain product from the table by its name. We compare the name of the product to be deleted with the name of all product from
     * the products list. When we found the desired product, we get its id and use the delete method from the ProductBL class in order to delete it form the database.
     * @param name
     */
    private void delP(String name){
        int pr = 0;
        for(Product product : products){
            if (product.getProductName().equals(name)){
                pr = product.getProduct_id();
                newProduct.deleteProd(product);
            }
        }
        products.remove(pr-1);
    }

    /**
     * Here is the method used to create an order into the table by its client name, product and desired quantity. Firstly, we search for the client name into the clients list
     * and make a copy of that client. Secondly, we check for the product name in the products list and also make a copy of that product and save its price and quantity
     * into 2 new variables. In order to insert that order into the table we check if that product has enough quantity for the order to be created. If the product is
     * available, the order is created and also the bill for the order, but if the product is not available, then a pdf with an under stock message will be generated and
     * the order will not be created.
     * @param name
     * @param p
     * @param q
     */
    public void order(String name, String p, String q){
        int quantity = Integer.parseInt(q.substring(1,q.length()));
        String prod = p.substring(1,p.length());
        for(Client client : clients){
            if (client.getClientName().equals(name)) {
                orderIdClient = client.getClient_id();
                client1 = client;
                break; }
        }
        for (Product product : products) {
            if (product.getProductName().equals(prod)) {
                orderIdProduct = product.getProduct_id();
                orderPriceProd = product.getPrice();
                orderQuantProd = product.getProductQuantity();
                product1 = product;
                break; }
        }
        if (quantity <= orderQuantProd){
            idOrder++;
            Order order = new Order(idOrder, client1.getClientName(), product1.getProductName(),quantity);
            newOrder.createOrder(order);
            order.setTotalPrice(orderPriceProd * quantity);
            newOrder.orderProdEx(product1,quantity);
            orders.add(order);
            newOrder.createBill(order, noBill);
            noBill++;
        } else {
            OrderBL ord = new OrderBL();
            ord.underStockProduct(product1.getProductName());
        }
    }

    /**
     * In this method we read from the commands.txt file line by line an execute each commmand from the file.
     * @param input
     * @throws FileNotFoundException
     */
    public void readFromFile(File input) throws FileNotFoundException {
        Scanner commands = new Scanner(input);
        for (Client client : clients){
            if (idClient < client.getClient_id())
                idClient = client.getClient_id();}
        for (Product product : products){
            if (idProduct < product.getProduct_id())
                idProduct = product.getProduct_id();}
        for (Order order : orders) {
            if (idOrder < order.getOrder_id())
                idOrder = order.getOrder_id();}
        while (commands.hasNextLine()) {
            String[] s = commands.nextLine().split(":");
            if (s[0].equals("Insert client")) {
                String[] r = s[1].split(",");
                insC(r[0],r[1]); }
            if (s[0].equals("Delete client")) {
              String[] r = s[1].split(",");
              delC(r[0],r[1]); }
            if (s[0].equals("Report client")) {
              newClient.createReport(clients, clientNoRep);
              clientNoRep++;}
            if (s[0].equals("Insert product")) {
                String[] r = s[1].split(",");
                insP(r[0].substring(1,r[0].length()), Integer.parseInt(r[1].substring(1, r[1].length())), Float.parseFloat(r[2].substring(1, r[2].length())) ); }
            if (s[0].equals("Delete Product")) {
                delP(s[1].substring(1,s[1].length()));}
            if (s[0].equals("Report product")) {
                 newProduct.createReport(products, productNoRep);
                 productNoRep++; }
            if (s[0].equals("Order")) {
                    String[] r = s[1].split(",");
                    order(r[0], r[1], r[2]);            }
            if (s[0].equals("Report order")) {
                   newOrder.createReport(orders, ordertNoRep);
                   ordertNoRep++;}
        }
    }
}
