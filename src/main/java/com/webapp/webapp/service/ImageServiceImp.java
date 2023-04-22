package com.webapp.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.webapp.data.models.Image;
import com.webapp.webapp.data.repository.ImageRepository;

@Service
public class ImageServiceImp implements ImageService {
    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image createImage(int productId, String fileName) {
        Image image = new Image();
        image.setProduct_id(productId);
        image.setFile_name(fileName);
        imageRepository.save(image);
        return image;

    }

    @Override
    public void deleteImage(int productId, int imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new UnsupportedOperationException("Unimplemented method 'deleteImage'");
        } else {
            imageRepository.deleteById(imageId);
        }

    }

    @Override
    public Optional<Image> getImage(int productId, int imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        return image;
        // throw new UnsupportedOperationException("Unimplemented method 'getImage'");
    }

}
