package apex.ingagers.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
public class OrderStatusHistory {
   /* 
   * Primary key is composed of both orders and products with "MapsId"
   * and set into OrderStatusHistoryId using "EmbeddedId" 
   */
   @EmbeddedId
   private OrderStatusHistoryId id = new OrderStatusHistoryId();

   @ManyToOne
   @MapsId("id_order")
   private Orders orders;

   @ManyToOne
   @MapsId("id_status")
   private OrderStatuses status;

   @Column(nullable = false)
   private Timestamp created_at;
   private Timestamp updated_at;
   // End of table structure

   public Orders getOrders() {
      return this.orders;
   }

   public void setOrders(Orders orders) {
      this.orders = orders;
   }  

   public OrderStatuses getStatus() {
      return this.status;
   }

   public void setStatus(OrderStatuses status) {
      this.status = status;
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


   @Embeddable
   public static class OrderStatusHistoryId implements Serializable {

      private int id_order;
      private int id_status;

      public OrderStatusHistoryId() {

      }

      public OrderStatusHistoryId(int id_order, int id_status) {
         this.id_order = id_order;
         this.id_status = id_status;
      }

      public int getId_order() {
         return this.id_order;
      }

      public void setId_order(int id_order) {
         this.id_order = id_order;
      }

      public int getId_status() {
         return this.id_status;
      }

      public void setId_status(int id_status) {
         this.id_status = id_status;
      }
   }
}
