package com.webapp.webapp.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.webapp.data.models.Image;
import com.webapp.webapp.data.repository.ImageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImp implements ImageService{
    @Autowired
    ImageStore imageStore;
    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image createImage(int product_id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s", System.getenv(null), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            imageStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Image image = new Image();
         image.setFile_name(fileName);
         image.setProduct_id(product_id);
         image.setS3_bucket_path(path);
        imageRepository.save(image);
        return image;
    }

    @Override
    public Optional<Image> getImage(int image_id) {
       Optional<Image> image=imageRepository.findById(image_id);
       return image;
    }

    @Override
    public void deleteImage(int image_id) {
        Optional<Image> image=imageRepository.findById(image_id);
        if(image.isEmpty()){
            throw new IllegalStateException("The image does not exist");
        }
        String path=image.get().getS3_bucket_path();
        imageStore.delete("bucket_name",path);
    }
    
}
