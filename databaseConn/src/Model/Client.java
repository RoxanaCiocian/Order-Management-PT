package Model;

/**
 * This is the mapped class for the Client table from the database
 */
public class Client {
    private int idClient;
    private String name;
    private String city;

    public Client(int idClient, String name, String city){
        setClient_id(idClient);
        setClientName(name);
        setCity(city);
    }
    public int getClient_id() {
        return this.idClient;
    }

    public void setClient_id(int idClient) {
        this.idClient = idClient;
    }

    public String getClientName() {
        return this.name;
    }

    public void setClientName(String name) {
        this.name = name;
    }
    public String getCity(){
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}


