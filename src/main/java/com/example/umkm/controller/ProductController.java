package com.example.umkm.controller;

import com.example.umkm.entity.Product;
import com.example.umkm.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Product request) {
        Product create = productService.create(request);
        Map<String, Object> response = new HashMap<>();
        if (create != null) {
            response.put("message", "Product created successfully");
            response.put("data", create);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Failed to create product");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Product> products = productService.getAll();
        Map<String, Object> response = new HashMap<>();
        if (!products.isEmpty()) {
            response.put("message", "Products retrieved successfully");
            response.put("data", products);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "No products found");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product product = productService.getById(id);
            response.put("message", "Product retrieved successfully");
            response.put("data", product);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.put("message", "Product not found");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Product request) {
        Product update = productService.update(id, request);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
    @GetMapping("/expiring")
    public ResponseEntity<List<Product>> getExpiringProducts(@RequestParam("thresholdDate") String thresholdDate) {
        try {
            LocalDate date = LocalDate.parse(thresholdDate);
            List<Product> products = productService.getExpiringProducts(date);
            return ResponseEntity.ok(products);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam("name") String name) {
        return productService.search(name);
    }

    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts(@RequestParam Integer stockThreshold) {
        return productService.getLowStockProducts(stockThreshold);
    }
}
