package apex.ingagers.ecommerce.tables;

import java.security.Timestamp;

import javax.persistence.*;
import java.util.List;

import javax.persistence.GeneratedValue;

@Entity // This tells Hibernate to make a table out of this class
public class Categories {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private Timestamp created_at;
    private Timestamp updated_At;

    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private int is_active;



    @ManyToMany
    @JoinTable(
        name = "category_product",
        joinColumns = @JoinColumn(name = "id_category", nullable = false),
        inverseJoinColumns = @JoinColumn(name="id_product", nullable = false)
    )
    private List<Products> products;


    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
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
    public Timestamp getUpdated_At() {
        return this.updated_At;
    }
    public void setUpdated_At(Timestamp updated_At) {
        this.updated_At = updated_At;
    }

}
