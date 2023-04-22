package com.webapp.webapp.web;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.webapp.webapp.data.models.Image;
import com.webapp.webapp.service.ImageService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
@RestController
@RequestMapping("/v1/product")
public class ImageController {
  
    private String accessKey="";
    private String secretKey="";
    // Set up S3 bucket name and region
    // private final String bucketName = System.getenv("S3_BUCKET_NAME");
    private final String region = "us-west-2";
    // // Set up S3 client
    // private AmazonS3 s3 = AmazonS3ClientBuilder.standard()
    // .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
    // .withRegion(region)
    // .build();
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    .withRegion("us-west-2")
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();


    @Autowired
    ImageService imageService;

    @GetMapping("/{product_id}/image/{image_id}")
    public ResponseEntity<Image> getImage(@PathVariable("product_id") int productId,@PathVariable("image_id") int imageId) {
        Optional<Image> image=imageService.getImage(productId, imageId);
        if(image.isEmpty()) {

        }
        return new ResponseEntity<>(image.get(),HttpStatus.OK);
    }

    @PostMapping("/{product_id}/image")
    public ResponseEntity<Image> handleImageUpload(@RequestParam("image") MultipartFile image,Model model, @PathVariable int productId) {
    Image imageEntity=new Image();

        // Generate pre-signed URL for image upload
        String key = "images/" + image.getOriginalFilename();
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest("my-bucket-0511d18f3331fbc3", key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + 3600000));
        URL preSignedUrl = s3.generatePresignedUrl(generatePresignedUrlRequest);

        // // Add pre-signed URL and key to model
        model.addAttribute("preSignedUrl", preSignedUrl.toString());
        model.addAttribute("key", key);
        
        imageEntity.setImage_id(productId);
        imageEntity.setS3_bucket_path("https://s3-"+region+".amazonaws.com/"+"my-bucket-0511d18f3331fbc3"+"/"+key);
        

    return new ResponseEntity<>(imageEntity, HttpStatus.OK);
}

    @DeleteMapping("/{product_id}/image/{image_id}")
    public ResponseEntity<Image> deleteImage(@PathVariable("product_id") int productId,@PathVariable("image_id") int imageId) {
        Optional<Image> image=imageService.getImage(productId, imageId);
        if(image.isEmpty()){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String path=image.get().getS3_bucket_path();
        String key = path.substring(path.lastIndexOf('/') + 1);
        imageService.deleteImage(productId,imageId);
        s3.deleteObject("my-bucket-0511d18f3331fbc3", key );
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
