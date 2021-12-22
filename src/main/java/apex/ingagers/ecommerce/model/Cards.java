package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.persistence.GeneratedValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;

@Entity
public class Cards {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    private Timestamp Time_now;
    private String ccStripeId;

    public String getCcStripeId() {
        return this.ccStripeId;
    }

    public void setCcStripeId(String ccStripeId) {
        this.ccStripeId = ccStripeId;
    }

    public Timestamp getTime_now() {
        return this.Time_now;
    }

    public void setTime_now(Timestamp Time_now) {
        this.Time_now = Time_now;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
