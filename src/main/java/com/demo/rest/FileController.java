package com.demo.rest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class FileController {

    private final String BASE_DIRECTORY = "./uploads/";

    @GetMapping("/{folder}/{filename:.+}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String folder,
            @PathVariable String filename) {

        try {
            Path filePath = Paths.get(BASE_DIRECTORY + folder).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // or detect dynamically if needed
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}