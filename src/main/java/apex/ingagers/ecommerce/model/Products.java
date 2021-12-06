package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
public class Products {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String sku;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Float price;
<<<<<<< HEAD
    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1",nullable = false,insertable = false)
    private boolean  is_active;
    //TODO Revisar si la forma de asignacion de TINYINIT es correcta
    @Column(nullable = false)
=======
    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1")

    //TODO Revisar si la forma de asignacion de TINYINIT es correcta
    private boolean  is_active = true; 
>>>>>>> c579e21 (Comments to clarify and search structures)
    private Timestamp created_at;
    // @Column(nullable = false)
    private Timestamp updated_at;
    // @Column(nullable = false)
    private Timestamp delete_at;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private String photo_file_name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subcategory",nullable = false)
    private SubCategories subcategories;
    
    //----------------- END of Table structure-----------------
    public SubCategories getSubcategories() {
        return this.subcategories;
    }

    public void setSubcategories(SubCategories subcategories) {
        this.subcategories = subcategories;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdescription() {
        return this.description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
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
    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPhoto_file_name() {
        return this.photo_file_name;
    }

    public void setPhoto_file_name(String photo_file_name) {
        this.photo_file_name = photo_file_name;
    }

}
