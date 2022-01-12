package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Orders {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private float total_cost;

    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1",insertable = false)
    private boolean  is_active; 
    private Timestamp created_at;
    private Timestamp updated_at;

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    Users users;

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address", nullable = false)
    Addresses billing_address;

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address", nullable = false)
    Addresses shipping_address;

    // ----------------- END of Table structure-----------------

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getTotal_cost() {
        return this.total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost = total_cost;
    }

    public boolean getIs_active() {
        return this.is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Timestamp getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Addresses getBilling_address() {
        return this.billing_address;
    }

    public void setBilling_address(Addresses billing_address) {
        this.billing_address = billing_address;
    }

    public Addresses getShipping_address() {
        return this.shipping_address;
    }

    public void setShipping_address(Addresses shipping_address) {
        this.shipping_address = shipping_address;
    }
}
