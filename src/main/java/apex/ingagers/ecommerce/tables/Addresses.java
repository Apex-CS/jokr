package apex.ingagers.ecommerce.tables;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Addresses {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String street1;
    private String street2;
    private String colonia;
    private String municipio;
    private String state;
    private String country;
    private String postal_code;
    private String phone;
    private String client_name;
    @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private int is_active;
    private Timestamp created_at;
    private Timestamp updated_at;

    //Foreign Key id_user
    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;

    //FK relation with Orders 
    @OneToMany(mappedBy = "billing_address")
    List<Orders> billing_address;

    //FK relation with Orders 
    @OneToMany(mappedBy = "shipping_address")
    List<Orders> shipping_address;

    //----------------- END of Table structure-----------------

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet1() {
        return this.street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return this.street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getColonia() {
        return this.colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal_code() {
        return this.postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClient_name() {
        return this.client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public int getIs_active() {
        return this.is_active;
    }

    public void setIs_active(int is_active) {
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

}