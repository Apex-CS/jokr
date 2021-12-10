package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  private Timestamp created_at;
  private Timestamp updated_at;
  private Timestamp delete_at;

  @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1", insertable = false)
  private boolean is_active;

  // Foreign Key id_role
  @ManyToOne
  @JoinColumn(name = "id_role")
  Roles roles;

  // Many to Many relationship with Products adding Favorites table
  @ManyToMany
  @JoinTable(name = "favorites", joinColumns = @JoinColumn(name = "id_user", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_product", nullable = false))
  private List<Products> products;

  // ----------------- END of Table structure-----------------

  public Roles getRole() {
    return this.roles;
  }

  public void setRole(Roles rol) {
    this.roles=rol;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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
}
