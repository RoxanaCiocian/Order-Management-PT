package Model;

/**
 * This is a mapped class for the Order table from the database, with the additon of totalPrice variable, which represents the final sum for an order
 */

public class Order {
    private int idOrder;
    private String name;
    private String product;
    private int quantity;
    private float totalPrice;

    public Order( int idOrder, String name, String product, int quantity){
        setOrder_id(idOrder);
        setOrderName(name);
        setProduct(product);
        setQuantity(quantity);
    }

    public int getOrder_id() {
        return this.idOrder;
    }

    public void setOrder_id(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderName() {
        return this.name;
    }

    public void setOrderName(String name) {
        this.name = name;
    }

    public String getProduct(){
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", name='" + name + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
