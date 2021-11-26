package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
public class Products {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String sku;
    private String name;
    private String description;
    private Float price;
    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private int is_active;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int stock;
    private String photo_file_name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subcategory")
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
