package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;


@Entity // This tells Hibernate to make a table out of this class
public class Roles {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true) 
    private Integer id;
    
    @Column(nullable = false)
    private String rolename;

    @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1",insertable = false)
    @JsonIgnore @ApiModelProperty(hidden = true) 
    private boolean is_active;

    @JsonIgnore @ApiModelProperty(hidden = true) 
    private Timestamp created_at;

    @JsonIgnore @ApiModelProperty(hidden = true) 
    private Timestamp updated_at;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getrolename() {
        return this.rolename;
    }

    public void setrolename(String rolename) {
        this.rolename = rolename;
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

}
