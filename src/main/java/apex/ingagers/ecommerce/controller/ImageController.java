package apex.ingagers.ecommerce.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    @Value("${cloudinary.credentials.cloud.name}")
    private String cloud_name;
    @Value("${cloudinary.credentials.api.key}")
    private String api_key;
    @Value("${cloudinary.credentials.api.secret}")
    private String api_secret;
    @Value("${cloudinary.credentials.secure}")
    private boolean secure;

    @DeleteMapping("/image/{id_image}")
    public Map<String, String> deleteImage(@PathVariable("id_image") String id_image,
            @RequestBody Map<String, Object> values) throws IOException {

        String idImage = "Jokr/" + String.valueOf(values.get("folder")) + "/" + id_image;
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

    @PostMapping(value = "/image/{name_folder}" , consumes = { MediaType . MULTIPART_FORM_DATA_VALUE }) // Map ONLY POST Requests
    public Map<String, String> addImage(@PathVariable("name_folder") String name_folder,
            @RequestPart MultipartFile file) throws IOException {

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
                ObjectUtils.asMap("folder", "Jokr/" + name_folder + "/"));

        String photoUrl = String.valueOf(uploadResult.get("url"));
        String photoPublicId = String.valueOf(uploadResult.get("public_id"));

        String[] parts = photoPublicId.split("/");
        String photoId = parts[2];

        map.put("id", photoId);
        map.put("url", photoUrl);

        return map;
    }

}
