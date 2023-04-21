package com.webapp.webapp.data.repository;
import com.webapp.webapp.data.models.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Integer> {
        Optional<Image> findByID(int image_id);
    }

