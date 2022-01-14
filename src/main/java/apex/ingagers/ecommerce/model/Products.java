package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;



@Entity // This tells Hibernate to make a table out of this class
@Setter @Getter
public class Products {

    // @ApiModelProperty(hidden = true)  --- Para no pedir el dato en swagger pero si devolver en json
    // @JsonIgnore -- Para no mandar ni mostrar el dato en swagger

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true) 
    private Integer id;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1",nullable = false,insertable = false)
    @ApiModelProperty(hidden = true) 
    @JsonIgnore
    private boolean  is_active;

    @Column(nullable = false)
    @ApiModelProperty(hidden = true) 
    @JsonIgnore
    private Timestamp created_at;

    @ApiModelProperty(hidden = true) 
    @JsonIgnore
    private Timestamp updated_at;

    @ApiModelProperty(hidden = true) 
    @JsonIgnore
    private Timestamp delete_at;

    @Column(nullable = false)
    private int stock;

    @Column
    private String photoUrl;
    
    @Column
    private String photoPublicId;

 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subcategory",nullable = false)
    private SubCategories subcategories;
    
    //----------------- END of Table structure-----------------
    public String getPhotoPublicId() {
        return this.photoPublicId;
    }

    public void setPhotoPublicId(String photoPublicId) {
        this.photoPublicId = photoPublicId;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public SubCategories getSubcategories() {
        return this.subcategories;
    }

    public String getSubcategoriesName() {
        return this.subcategories.getName();
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



}
