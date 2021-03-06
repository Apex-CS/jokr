package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity // This tells Hibernate to make a table out of this class
public class Addresses {

    // @ApiModelProperty(hidden = true)  --- Para no pedir el dato en swagger pero si devolver en json
    // @JsonIgnore -- Para no mandar ni mostrar el dato en swagger

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
    
    @Column(nullable = false)
    private String street1;
    private String street2;
    private String colonia;
    @Column(nullable = false)
    private String municipio;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String postal_code;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String recipient_name;

    @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1",insertable = false,nullable = false)
    @ApiModelProperty(hidden = true) @JsonIgnore
    private boolean is_active;

    @Column(nullable = false)
    @ApiModelProperty(hidden = true) @JsonIgnore
    private Timestamp created_at;
    @ApiModelProperty(hidden = true) @JsonIgnore
    private Timestamp updated_at;
    @ApiModelProperty(hidden = true) @JsonIgnore
    private Timestamp delete_at;
    // * Default billing and shipping address columns
    // * Only 1 shipping and 1 billing should be true for each user
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0",nullable = false)
    private boolean is_default_billing_address;
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0",nullable = false)
    private boolean is_default_shipping_address;

    //Foreign Key id_user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private Users users;

    //----------------- END of Table structure -----------------

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

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

    public String getRecipient_name() {
        return this.recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
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

    public Timestamp getDelete_at() {
        return this.delete_at;
    }

    public void setDelete_at(Timestamp delete_at) {
        this.delete_at = delete_at;
    }

    public boolean isIs_default_billing_address() {
        return this.is_default_billing_address;
    }

    public void setIs_default_billing_address(boolean is_default_billing_address) {
        this.is_default_billing_address = is_default_billing_address;
    }

    public boolean isIs_default_shipping_address() {
        return this.is_default_shipping_address;
    }

    public void setIs_default_shipping_address(boolean is_default_shipping_address) {
        this.is_default_shipping_address = is_default_shipping_address;
    }
 
}
