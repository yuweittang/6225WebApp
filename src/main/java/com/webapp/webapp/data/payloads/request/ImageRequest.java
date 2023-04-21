package com.webapp.webapp.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ImageRequest {
    @NotBlank
    @NotNull
     private int image_id;
     @NotBlank
     @NotNull
     private int product_id;

    /**
     * @return int return the image_id
     */
    public int getImage_id() {
        return image_id;
    }

    /**
     * @param image_id the image_id to set
     */
    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    /**
     * @return int return the product_id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

}
