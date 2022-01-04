package apex.ingagers.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity // This tells Hibernate to make a table out of this class
public class OrderProduct {
   /* 
   * Primary key is composed of both orders and products with "MapsId"
   * and set into OrderProductId using "EmbeddedId" 
   */
   @EmbeddedId
   private OrderProductId id = new OrderProductId();

   @ManyToOne
   @MapsId("id_order")
   private Orders orders;

   @ManyToOne
   @MapsId("id_product")
   private Products products;

   @Column(nullable = false)
   private int quantity;
   @Column(nullable = false)
   private float price;
   // * This column saves the original name of the product at the time of purchase (in case the name is changed later)
   @Column(nullable = false)
   private String name;
   // End of table structure

   public Orders getOrders() {
      return this.orders;
   }

   public void setOrders(Orders orders) {
      this.orders = orders;
   }

   public Products getProducts() {
      return this.products;
   }

   public void setProducts(Products products) {
      this.products = products;
   }

   public int getQuantity() {
      return this.quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public float getPrice() {
      return this.price;
   }

   public void setPrice(float price) {
      this.price = price;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
   
   @Embeddable
   public static class OrderProductId implements Serializable {

      private int id_order;
      private int id_product;

      public OrderProductId() {

      }

      public OrderProductId(int id_order, int id_product) {
         this.id_order = id_order;
         this.id_product = id_product;
      }

      public int getId_order() {
         return this.id_order;
      }

      public void setId_order(int id_order) {
         this.id_order = id_order;
      }

      public int getId_product() {
         return this.id_product;
      }

      public void setId_product(int id_product) {
         this.id_product = id_product;
      }
   }
}
