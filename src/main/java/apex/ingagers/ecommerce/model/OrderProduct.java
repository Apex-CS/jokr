package apex.ingagers.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class OrderProduct {
     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private Integer id;
    // private String rolename;
    // @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    // private int is_active;
    // private Timestamp created_at;
    // private Timestamp updated_at;

   //TODO: Checar cómo implementar relacion muchos a muchos con atributos extra
}
