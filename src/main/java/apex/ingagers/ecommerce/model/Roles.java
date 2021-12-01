package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Roles {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String role_name;
    @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private int is_active;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole_name() {
        return this.role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
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
