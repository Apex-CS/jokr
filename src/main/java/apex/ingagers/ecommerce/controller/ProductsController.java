package apex.ingagers.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import apex.ingagers.ecommerce.model.Products;
import apex.ingagers.ecommerce.model.SubCategories;
import apex.ingagers.ecommerce.repository.ProductsRepository;
import apex.ingagers.ecommerce.repository.SubCategoriesRepository;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1")
public class ProductsController {

  private final ProductsRepository productsRepository;
  private final SubCategoriesRepository subCategoriesRepository;

  ProductsController(ProductsRepository productsRepository, SubCategoriesRepository subCategoriesRepository) {
    this.productsRepository = productsRepository;
    this.subCategoriesRepository = subCategoriesRepository;
  }

  @PostMapping("/products") // Map ONLY POST Requests
  Products addProducts(@RequestParam("json") String jsonString, @RequestPart("file") MultipartFile file)
      throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    // convert JSON string to Map
    Map<String, Object> values = mapper.readValue(jsonString, Map.class);

    String sku = String.valueOf(values.get("sku"));
    String name = String.valueOf(values.get("name"));
    String description = String.valueOf(values.get("description"));
    Float price = Float.parseFloat(String.valueOf(values.get("price")));
    Integer stock = Integer.parseInt(String.valueOf(values.get("stock")));
    String subcategorias = String.valueOf(values.get("subcategory"));

    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    SubCategories subcategories;
    subcategories = subCategoriesRepository.findByName(subcategorias);

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "dpakhjsmh",
        "api_key", "679976426528739",
        "api_secret", "a4vooY53qGsobBvJAU4i4Jf5__A",
        "secure", true));
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

    String photoUrl = String.valueOf(uploadResult.get("url"));
    String photoPublicId = String.valueOf(uploadResult.get("publicId"));

    Products p = new Products();
    p.setSku(sku);
    p.setName(name);
    p.setdescription(description);
    p.setPrice(price);
    p.setdescription(description);
    p.setStock(stock);
    p.setPhotoUrl(photoUrl);
    p.setPhotoPublicId(photoPublicId);
    p.setCreated_at(sqlTimestamp);
    p.setUpdated_at(null);
    p.setDelete_at(null);
    p.setSubcategories(subcategories);

    return productsRepository.save(p);
  }

  @GetMapping("/products")
  public List<Products> getAllProducts() {
    // This returns a JSON or XML with the Users
    return productsRepository.findAllProducts();
  }

  @GetMapping("/products/{id}")
  public Optional<Products> getProductsbyId(@PathVariable("id") Integer id) {
    return productsRepository.findProductsById(id);
  }

  @DeleteMapping("/products/{id}")
  public boolean eliminar(@PathVariable("id") Integer id) {
    Optional<Products> optionalproducts = productsRepository.findById(id);

    if (optionalproducts.isPresent()) {
      Products products = optionalproducts.get();
      if (products.getIs_active() == true) {
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        products.setIs_active(false);
        products.setDelete_at(sqlTimestamp);
        productsRepository.save(products);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @PutMapping("/products/{id}")
  public Products update(@PathVariable("id") Integer id, @RequestBody Map<String, Object> values) {

    Optional<Products> optionalproducts = productsRepository.findById(id);

    if (optionalproducts.isPresent()) {
      Products products = optionalproducts.get();
      products.setSku(String.valueOf(values.get("sku")));
      products.setName(String.valueOf(values.get("name")));
      products.setdescription(String.valueOf(values.get("description")));
      products.setPrice(Float.parseFloat(String.valueOf(values.get("price"))));
      products.setStock(Integer.parseInt(String.valueOf(values.get("stock"))));
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
