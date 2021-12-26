package apex.ingagers.ecommerce.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
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

  private final ProductsRepository productsRepository;
  private final SubCategoriesRepository subCategoriesRepository;

  ProductsController(ProductsRepository productsRepository, SubCategoriesRepository subCategoriesRepository) {
    this.productsRepository = productsRepository;
    this.subCategoriesRepository = subCategoriesRepository;
  }

  @PostMapping("/products/image") // Map ONLY POST Requests
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
        "cloud_name", "dpakhjsmh", // ddlqf2qer
        "api_key", "679976426528739", // "941731261856649",
        "api_secret", "a4vooY53qGsobBvJAU4i4Jf5__A", // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
        "secure", true));
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

  @DeleteMapping("/products/image/{id_image}")
  public Map<String, String> deleteImage(@PathVariable("id_image") String id_image) throws IOException {

    String idImage = "Jokr/productsPhoto/" + id_image;
    HashMap<String, String> map = new HashMap<>();

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "dpakhjsmh", // "ddlqf2qer",
        "api_key", "679976426528739", // "941731261856649",
        "api_secret", "a4vooY53qGsobBvJAU4i4Jf5__A", // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
        "secure", true));

    cloudinary.uploader().destroy(idImage, ObjectUtils.asMap("overwrite", "true", "public_id", idImage));

    map.put("ID", String.valueOf(idImage));
    map.put("RESPONSE", String.valueOf(idImage));

    return map;

  }

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

  @ApiOperation(value = "Finds Contacts by id", notes = "")
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

  @PutMapping("/products/{id_Product}")
  public Products updateProduct(@PathVariable("idProduct") Integer idProduct, @RequestBody Products product)
      throws IOException {

    Optional<Products> optionalProducts = productsRepository.findById(idProduct);

    if (optionalProducts.isPresent()) {
      Products products = optionalProducts.get();
      products.setSku(product.getSku());
      products.setName(product.getName());
      products.setdescription(product.getdescription());
      products.setPrice(product.getPrice());
      products.setStock(product.getStock());

      long now = System.currentTimeMillis();
      Timestamp sqlTimestamp = new Timestamp(now);

      products.setUpdated_at(sqlTimestamp);

      SubCategories subcategories;
      subcategories = subCategoriesRepository.findByName(product.getSubcategoriesName());
      products.setSubcategories(subcategories);

      productsRepository.save(products);

      return products;
    }
    return null;
  }
}