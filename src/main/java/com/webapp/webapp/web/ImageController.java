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
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@AllArgsConstructor
@CrossOrigin("*")
public class ImageController {
    @Autowired
    ImageService service;


    @PostMapping("/{product_id}/image")
    public ResponseEntity<Image> saveTodo(@PathVariable("product_id") int product_id,
    @RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(service.createImage(product_id, file), HttpStatus.OK);
    }

    @GetMapping(value = "/{product_id}/image/{image_id}")
    public HttpStatus deleteImage(@PathVariable("image_id") int image_id) {
         service.deleteImage(image_id);
         return HttpStatus.OK;
    }
}

