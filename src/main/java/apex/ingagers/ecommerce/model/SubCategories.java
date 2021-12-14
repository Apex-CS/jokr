package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.persistence.GeneratedValue;


@Entity // This tells Hibernate to make a table out of this class
public class SubCategories {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Timestamp created_at;
    // @Column(nullable = false)
    private Timestamp updated_At;
    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1",nullable = false,insertable = false)
    private boolean is_active;

    @ManyToOne(fetch = FetchType.LAZY)   
    @JoinColumn(name = "id_category",nullable = false)
    Categories categories;

//----------------- END of Table structure-----------------
    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

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
    public Timestamp getUpdated_At() {
        return this.updated_At;
    }
    public void setUpdated_At(Timestamp updated_At) {
        this.updated_At = updated_At;
    }

}
