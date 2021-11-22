package apex.ingagers.ecommerce.tables;

import java.sql.Timestamp;
import javax.persistence.*;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String email;
  private String password;
  private String name;
  private String lastName;
  private Timestamp created_at;
  private Timestamp updated_at;
  @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
  private int is_active;

  //FK relation with Orders
  @OneToMany(mappedBy = "users")
  List<Orders> orders;
  //FK relation with Addresses
  @OneToMany(mappedBy = "addresses")
  List<Addresses> addresses;

  //Many to Many relationship with Products adding Favorites table 
  @ManyToMany
  @JoinTable(
    name = "favorites",
    joinColumns = @JoinColumn(name = "id_user", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "id_product", nullable = false)
  )
  private List<Products> products;

  //----------------- END of Table structure-----------------

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
