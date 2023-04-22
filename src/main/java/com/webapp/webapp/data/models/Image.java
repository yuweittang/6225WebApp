package com.webapp.webapp.data.models;

import java.time.LocalDateTime;

import javax.persistence.*;
// import javax.print.attribute.standard.DateTimeAtCreation;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
   private int image_id;
   private int product_id;
   private String file_name;
   @CreatedDate
   private LocalDateTime date_created;
   private String s3_bucket_path;
    



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

    /**
     * @return LocalDateTime return the date_created
     */
    public LocalDateTime getDate_created() {
        return date_created;
    }

    /**
     * @param date_created the date_created to set
     */
    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    /**
     * @return String return the s3_bucket_path
     */
    public String getS3_bucket_path() {
        return s3_bucket_path;
    }

    /**
     * @param s3_bucket_path the s3_bucket_path to set
     */
    public void setS3_bucket_path(String s3_bucket_path) {
        this.s3_bucket_path = s3_bucket_path;
    }



    /**
     * @return String return the file_name
     */
    public String getFile_name() {
        return file_name;
    }

    /**
     * @param file_name the file_name to set
     */
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

}
