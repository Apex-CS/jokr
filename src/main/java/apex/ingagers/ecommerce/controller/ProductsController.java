package apex.ingagers.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
  HttpStatus addProducts(@RequestParam("json") String jsonString, @RequestPart("file") MultipartFile file)
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

    if(file==null|| file.isEmpty()){
      //If the file(image) is empty
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Please upload an image");
    }

    List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");
    String fileContentType = file.getContentType();

   if(!contentTypes.contains(fileContentType)) {
        // the is not correct extension
        throw new ResponseStatusException(
          HttpStatus.NOT_ACCEPTABLE, "Please upload an image with the correct extension(JPG,JPEG,PNG)");
    }

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
      "cloud_name", "ddlqf2qer",              //"dpakhjsmh",
      "api_key", "941731261856649",                  //"679976426528739",
      "api_secret", "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",              //"a4vooY53qGsobBvJAU4i4Jf5__A",
      "secure", true));
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(),ObjectUtils.asMap( "folder", "Jokr/productsPhoto/"));

    String photoUrl = String.valueOf(uploadResult.get("url"));
    String photoPublicId = String.valueOf(uploadResult.get("public_id"));

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

    if(productsRepository.save(p) != null){
      return HttpStatus.OK;
    }
   else{
    return HttpStatus.BAD_REQUEST;
   }
  }

  @GetMapping("/products")
  public List<Products> getAllProducts() {
    // This returns a JSON or XML with the Users
    return productsRepository.findAllProducts();
  }

  @GetMapping("/products/{id}")
  public Optional<Products> getProductbyId(@PathVariable("id") Integer id) {
    return productsRepository.findProductsById(id);
  }

  @DeleteMapping("/products/{id}")
  public boolean deleteProduct(@PathVariable("id") Integer id) {
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
  public Products updateProduct(@PathVariable("id") Integer id,@RequestParam("json") String jsonString, @RequestPart("file") MultipartFile file) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    // convert JSON string to Map
    Map<String, Object> values = mapper.readValue(jsonString, Map.class);


    Optional<Products> optionalProducts = productsRepository.findById(id);

    if (optionalProducts.isPresent()) {
      Products products = optionalProducts.get();
      products.setSku(String.valueOf(values.get("sku")));
      products.setName(String.valueOf(values.get("name")));
      products.setdescription(String.valueOf(values.get("description")));
      products.setPrice(Float.parseFloat(String.valueOf(values.get("price"))));
      products.setStock(Integer.parseInt(String.valueOf(values.get("stock"))));

      long now = System.currentTimeMillis();
      Timestamp sqlTimestamp = new Timestamp(now);

      products.setUpdated_at(sqlTimestamp);

      SubCategories subcategories;
      subcategories = subCategoriesRepository.findByName(String.valueOf(values.get("subcategory")));
      products.setSubcategories(subcategories);
      productsRepository.save(products);

      if(file!=null){
        List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");
          String fileContentType = file.getContentType();
      
         if(!contentTypes.contains(fileContentType)) {
              // the is not correct extension
              throw new ResponseStatusException(
                HttpStatus.NOT_ACCEPTABLE, "Please upload an image with the correct extension(JPG,JPEG,PNG)");
          }
          Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "ddlqf2qer",              //"dpakhjsmh",
            "api_key", "941731261856649",                  //"679976426528739",
            "api_secret", "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",              //"a4vooY53qGsobBvJAU4i4Jf5__A",
            "secure", true));
            
              Products product =  optionalProducts.get();
              String photoPublicId =  product.getPhotoPublicId();
              Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap( "overwrite","true","public_id",photoPublicId));
        }

      return products;
    }
    return null;
  }
}
