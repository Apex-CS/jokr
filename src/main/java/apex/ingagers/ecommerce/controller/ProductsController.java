package apex.ingagers.ecommerce.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

import apex.ingagers.ecommerce.model.Products;
import apex.ingagers.ecommerce.model.SubCategories;
import apex.ingagers.ecommerce.repository.ProductsRepository;
import apex.ingagers.ecommerce.repository.SubCategoriesRepository;
import io.swagger.annotations.ApiOperation;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;


@RestController
@RequestMapping("/api/v1")
public class ProductsController {

  @Value("${cloudinary.credentials.cloud.name}")
  private String cloud_name;
  @Value("${cloudinary.credentials.api.key}")
  private String api_key;
  @Value("${cloudinary.credentials.api.secret}")
  private String api_secret;
  @Value("${cloudinary.credentials.secure}")
  private boolean secure;

  private final ProductsRepository productsRepository;
  private final SubCategoriesRepository subCategoriesRepository;

  ProductsController(ProductsRepository productsRepository, SubCategoriesRepository subCategoriesRepository) {
    this.productsRepository = productsRepository;
    this.subCategoriesRepository = subCategoriesRepository;
  }

  @PreAuthorize("hasAuthority ('Admin')")
  @PostMapping(value = "/products/image", consumes  = { MediaType . MULTIPART_FORM_DATA_VALUE }) // Map ONLY POST Requests
  public Map<String, String> addImageProducts(@RequestPart MultipartFile file) throws IOException {

    HashMap<String, String> map = new HashMap<>();
    if (file == null || file.isEmpty()) {
      map.put("id", "");
      map.put("url", "");
      return map;
    }

    // File Validations
    if (file == null || file.isEmpty()) {
      // If the file(image) is empty
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Please upload an image");
    }

    List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");
    String fileContentType = file.getContentType();

    if (!contentTypes.contains(fileContentType)) {
      // the is not correct extension
      throw new ResponseStatusException(
          HttpStatus.NOT_ACCEPTABLE, "Please upload an image with the correct extension(JPG,JPEG,PNG)");
    }

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
      "cloud_name", cloud_name, // "ddlqf2qer",
      "api_key", api_key, // "941731261856649",
      "api_secret", api_secret, // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
      "secure", secure));

    Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
        ObjectUtils.asMap("folder", "Jokr/productsPhoto/"));

    String photoUrl = String.valueOf(uploadResult.get("url"));
    String photoPublicId = String.valueOf(uploadResult.get("public_id"));

    String[] parts = photoPublicId.split("/");
    String photoId = parts[2];

    map.put("id", photoId);
    map.put("url", photoUrl);

    return map;
  }

  @PreAuthorize("hasAuthority ('Admin')")
  @DeleteMapping("/products/image/{id_image}")
  public Map<String, String> deleteImage(@PathVariable("id_image") String id_image) throws IOException {

    String idImage = "Jokr/productsPhoto/" + id_image;
    HashMap<String, String> map = new HashMap<>();

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
      "cloud_name", cloud_name, // "ddlqf2qer",
      "api_key", api_key, // "941731261856649",
      "api_secret", api_secret, // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
      "secure", secure));


    cloudinary.uploader().destroy(idImage, ObjectUtils.asMap("overwrite", "true", "public_id", idImage));

    map.put("ID", String.valueOf(idImage));
    map.put("RESPONSE", String.valueOf(idImage));

    return map;

  }

  @PreAuthorize("hasAuthority ('Admin')")
  @PostMapping("/products") // Map ONLY POST Requests
  HttpStatus addProducts(@RequestBody Products product) {

    String subcategorias = product.getSubcategoriesName();

    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    SubCategories subcategories;
    subcategories = subCategoriesRepository.findByName(subcategorias);

    Products p = new Products();
    p = product;
    p.setCreated_at(sqlTimestamp);
    p.setSubcategories(subcategories);

    if (productsRepository.save(p) != null) {
      return HttpStatus.OK;
    } else {
      return HttpStatus.BAD_REQUEST;
    }
  }

  @GetMapping("/products")
  public List<Products> getAllProducts() {
    // This returns a JSON or XML with the Users
    return productsRepository.findAllProducts();
  }

 
  @GetMapping("/products/{id}")
  public List<Products> getProductbyId(@PathVariable("id") Integer id) {
    return productsRepository.findProductsById(id);
  }

  @PreAuthorize("hasAuthority ('Admin')")
  @DeleteMapping("/products/{id}")
  public boolean deleteProduct(@PathVariable("id") Integer id) {
    List<Products> optionalproducts = productsRepository.findProductsById(id);

    if (!optionalproducts.isEmpty()) {
      Products products = optionalproducts.get(0);
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

  @PreAuthorize("hasAuthority ('Admin')")
  @PutMapping("/products/{id}")
  public Products updateProduct(@PathVariable("id") Integer id, @RequestBody Products product) {

    List<Products> optionalProducts = productsRepository.findProductsById(id);

    if (!optionalProducts.isEmpty()) {
      Products products = optionalProducts.get(0);

      long now = System.currentTimeMillis();
      Timestamp sqlTimestamp = new Timestamp(now);
      SubCategories subcategories = subCategoriesRepository.findByName(product.getSubcategoriesName());

      products.setdescription(product.getdescription());
      products.setName(product.getName());
      products.setPhotoPublicId(product.getPhotoPublicId());
      products.setPhotoUrl(product.getPhotoUrl());
      products.setPrice(product.getPrice());
      products.setSku(product.getSku());
      products.setStock(product.getStock());
      products.setUpdated_at(sqlTimestamp);
      products.setSubcategories(subcategories);

      return productsRepository.save(products);
    }
    return null;
  }
}