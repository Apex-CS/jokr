package apex.ingagers.ecommerce.model;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class OrderProduct {
    // @Id
    // @GeneratedValue(strategy=GenerationType.AUTO)
    // private Integer id;
    // private String role_name;
    // @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    // private int is_active;
    // private Timestamp created_at;
    // private Timestamp updated_at;

   //TODO: Checar cómo implementar relacion muchos a muchos con atributos extra
}
