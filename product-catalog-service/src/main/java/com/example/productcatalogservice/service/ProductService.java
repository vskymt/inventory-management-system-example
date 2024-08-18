package com.example.productcatalogservice.service;

import com.example.productcatalogservice.model.Product;
import com.example.productcatalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    private static final String TOPIC = "product-topic";

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        kafkaTemplate.send(TOPIC, savedProduct);
        return savedProduct;
    }

    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            Product updatedProduct = productRepository.save(product);
            kafkaTemplate.send(TOPIC, updatedProduct);
            return updatedProduct;
        } else {
            return null;
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}