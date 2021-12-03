package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Products;
import apex.ingagers.ecommerce.model.SubCategories;
import apex.ingagers.ecommerce.repository.ProductsRepository;
import apex.ingagers.ecommerce.repository.SubCategoriesRepository;


@RestController
public class ProductsController {

    private final ProductsRepository productsRepository;
    private final SubCategoriesRepository subCategoriesRepository;

    ProductsController(ProductsRepository productsRepository, SubCategoriesRepository subCategoriesRepository){
        this.productsRepository = productsRepository;
        this.subCategoriesRepository = subCategoriesRepository;
    }

    @PostMapping("/products") // Map ONLY POST Requests
    Products addProducts (@RequestBody Map<String,Object> values) {
        String sku = String.valueOf(values.get("sku"));
        String name = String.valueOf(values.get("name"));
        String description = String.valueOf(values.get("description"));
        Float price = Float.parseFloat(String.valueOf(values.get("price")));
        int stock = Integer.parseInt(String.valueOf(values.get("stock")));
        String photo_file_name = String.valueOf(values.get("photo_file_name"));
        String subcategorias = String.valueOf(values.get("subcategory"));
        //casteo de string
        
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        
        SubCategories subcategories;
        subcategories = subCategoriesRepository.findByName(subcategorias);

        Products p = new Products();
        p.setSku(sku);
        p.setName(name);
        p.setdescription(description);
        p.setPrice(price);
        p.setdescription(description);
        p.setStock(stock);
        p.setPhoto_file_name(photo_file_name);
        p.setCreated_at(sqlTimestamp);
        p.setUpdated_at(null);
        p.setDelete_at(null);
        p.setSubcategories(subcategories);
        
        return productsRepository.save(p);
  }

  @GetMapping("/products")
  public  List<Products> getAllProducts() {
    // This returns a JSON or XML with the users
    return  productsRepository.findAllProducts();
  }


  @DeleteMapping("/products/{id}")
  public boolean eliminar(@PathVariable("id") Integer id){
    Optional<Products> optionalproducts = productsRepository.findById(id);
		
    if( optionalproducts.isPresent()){
      Products products = optionalproducts.get();
      if(products.getIs_active() == true)
      {
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        products.setIs_active(false);
        products.setDelete_at(sqlTimestamp);
        productsRepository.save(products);
        return true;
      }else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
  }

  @PutMapping("/products/{id}")
  public Products update(@PathVariable("id") Integer id, @RequestBody Map<String,Object> values) {
    
    Optional<Products> optionalproducts = productsRepository.findById(id);
		
    if( optionalproducts.isPresent()){
      Products products = optionalproducts.get();
      products.setSku(String.valueOf(values.get("sku")));
      products.setName(String.valueOf(values.get("name")));
      products.setdescription(String.valueOf(values.get("description")));
      products.setPrice(Float.parseFloat(String.valueOf(values.get("price"))));
      products.setStock( Integer.parseInt(String.valueOf(values.get("stock"))));
      products.setPhoto_file_name(String.valueOf(values.get("photo_file_name")));

      long now = System.currentTimeMillis();
      Timestamp sqlTimestamp = new Timestamp(now);

      products.setUpdated_at(sqlTimestamp);

      SubCategories subcategories;
      subcategories = subCategoriesRepository.findByName(String.valueOf(values.get("subcategory")));
      products.setSubcategories(subcategories);  
      productsRepository.save(products);

      return products;
    }
    return null;
  }

}
