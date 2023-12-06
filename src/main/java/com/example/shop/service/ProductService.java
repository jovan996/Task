package com.example.shop.service;

import com.example.shop.exception.DuplicateProductException;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {

        var optionalProduct = productRepository.findByName(product.getName());
        if(optionalProduct.isPresent()){
            throw new DuplicateProductException("Product with given name " + product.getName() + " already exists");
        }

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
