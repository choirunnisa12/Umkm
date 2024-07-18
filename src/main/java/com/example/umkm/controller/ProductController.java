package com.example.umkm.controller;

import com.example.umkm.entity.Product;
import com.example.umkm.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product request) {
        Product create = productService.create(request);
        if (create != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(create);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create product");
        }
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return productService.getById(id);
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
    public List<Product> getExpiringProducts(@RequestParam("thresholdDate") String thresholdDate) {
        LocalDate date = LocalDate.parse(thresholdDate);
        return productService.getExpiringProducts(date);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam("name") String name) {
        return productService.search(name);
    }

    @GetMapping("/lowstock")
    public List<Product> findByStockLessThan(@RequestParam("stockThreshold") Integer stockThreshold) {
        return productService.getLowStockProducts(stockThreshold);
    }
}
