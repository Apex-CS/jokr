package apex.ingagers.ecommerce.model;

import java.util.List;
import java.sql.Timestamp;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

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
  @JsonIgnore @ApiModelProperty(hidden = true) 
  private Timestamp created_at;

  @JsonIgnore @ApiModelProperty(hidden = true) 
  private Timestamp updated_at;

  @JsonIgnore @ApiModelProperty(hidden = true) 
  private Timestamp delete_at;

  
  @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1", insertable = false)
  @JsonIgnore @ApiModelProperty(hidden = true) 
  private boolean is_active;

  // Foreign Key id_role
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_role", nullable = false)
  Roles roles;

  // Many to Many relationship with Products adding Favorites table
  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "favorites", joinColumns = @JoinColumn(name = "id_user", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_product", nullable = false))
  private List<Products> products;

  @Column
  private String photoUrl;
  @Column
  private String photoPublicId;

  @Column
  private String customerPaymentId;

  // ----------------- END of Table structure-----------------
  public String getCustomerPaymentId() {
    return this.customerPaymentId;
  }

  public void setCustomerPaymentId(String customerPaymentId) {
    this.customerPaymentId = customerPaymentId;
  }

  public String getphotoPublicId() {
    return this.photoPublicId;
  }

  public void setphotoPublicId(String photoPublicId) {
    this.photoPublicId = photoPublicId;
  }

  public String getphotoUrl() {
    return this.photoUrl;
  }

  public void setphotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public Roles getRole() {
    return this.roles;
  }

  public String getRoleName() {
    return this.roles.getrolename();
  }

  public void setRole(Roles rol) {
    this.roles = rol;
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