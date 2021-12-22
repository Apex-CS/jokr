package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.persistence.GeneratedValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;

@Entity
public class DummyPayments {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
	@JsonIgnore
    private Timestamp created_at;
	@JsonIgnore
    private Timestamp updated_at;
	@JsonIgnore
    private Timestamp delete_at; 

  //TODO: Delete these lines when orders and orderproducts are ready and modify the dependant code to work with the order relation.

  @Column(nullable = false)
	private String status;
    @Column(nullable = false)
    private Integer amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private Users user;

    @Column(nullable = false)
	private String currency;


    //TODO: Discomment these lines when orders and orderproducts are ready.
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "id_order) 
    // private Orders order;
	@Transient
	String cardNumber;
	@Transient
	String expMonth;
	@Transient
	String expYear;
	@Transient
	String securityCode;


    //----------------- END of Table structure-----------------
	
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}


	public String getExpYear() {
		return this.expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}


	public String getExpMonth() {
		return this.expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
    
    public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
