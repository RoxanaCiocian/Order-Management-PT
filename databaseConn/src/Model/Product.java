package Model;

/**
 * This is a mapped class for the Product table from the database
 */
public class Product {
    private int idProduct;
    private String name;
    private int quantity;
    private float price;

    public Product(int idProduct, String name, int quantity, float price){
        setProduct_id(idProduct);
        setProductName(name);
        setProductQuantity(quantity);
        setPrice(price);
    }

    public int getProduct_id() {
        return this.idProduct;
    }

    public void setProduct_id(int idProduct) {
        this.idProduct = idProduct;
    }
    public String getProductName() {
        return this.name;
    }

    public void setProductName(String name) {
        this.name = name;
    }
    public int getProductQuantity() {
        return this.quantity;
    }

    public void setProductQuantity(int quantity) {
        this.quantity = quantity;
    }
    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
