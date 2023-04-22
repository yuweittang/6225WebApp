package com.webapp.webapp.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.Request;
import com.webapp.webapp.data.models.Image;
import com.webapp.webapp.data.payloads.request.ImageRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
@Component
@Service
public interface ImageService {
    Image createImage(int product_id,MultipartFile file);
    Optional<Image> getImage(int image_id);
    void deleteImage(int image_id);

}
