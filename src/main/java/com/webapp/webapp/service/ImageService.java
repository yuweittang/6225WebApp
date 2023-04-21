package com.webapp.webapp.service;

import java.util.Optional;

import com.amazonaws.Request;
import com.webapp.webapp.data.models.Image;
import com.webapp.webapp.data.payloads.request.ImageRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface ImageService {
    Image createImage(int productId,int imageId,String fileName);

    Optional<Image> getImage(int productId,int imageId);

    void deleteImage(int productId, int imageId);
}
