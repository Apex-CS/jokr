package apex.ingagers.ecommerce.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

public class PurchaseDone {

    private Addresses address;
    private int amount_total;
    
    private List<OrderProduct_metadata> items= new ArrayList<OrderProduct_metadata>();

    public static class OrderProduct_metadata implements Serializable{
        public String id;
        public String description;
        public int quantity;
    }

    // ----------------- END of Table structure-----------------
    public Addresses getAddress() {
        return this.address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    public int getAmount_total() {
        return this.amount_total;
    }

    public void setAmount_total(int amount_total) {
        this.amount_total = amount_total;
    }

    public List<OrderProduct_metadata> getItems() 
    {
        return this.items;
    }
   
}
