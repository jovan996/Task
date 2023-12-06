package com.example.shop.service;

import com.example.shop.exception.DuplicateProductException;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setup(){
        product = Product.builder()
                .id(1L)
                .name("School bag")
                .description("Great bag")
                .price(120)
                .build();
    }

    @Test
    public void givenProductWhenSaveProductThenReturnProduct() {

        when(productRepository.findByName(product.getName()))
                .thenReturn(Optional.empty());

        when(productRepository.save(any())).thenReturn(product);

        var savedProduct = productService.saveProduct(product);

        assertThat(savedProduct.getName()).isEqualTo(product.getName());
        assertThat(savedProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void givenExistingProductWhenSaveProductThenThrowsException() {

        when(productRepository.findByName(product.getName()))
                .thenReturn(Optional.of(product));

        assertThrows(DuplicateProductException.class, () -> {
            productService.saveProduct(product);
        });
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void givenProductsListWhenGetAllProductsThenReturnProductsList(){

        var newProduct = Product.builder()
                .id(2L)
                .name("Pencil")
                .description("Yellow pencil")
                .price(13)
                .build();

        when(productRepository.findAll()).thenReturn(List.of(product, newProduct));

        var productsList = productService.getAllProducts();

        assertThat(productsList).isNotEmpty();
        assertThat(productsList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyProductsListWhenGetAllProductsThenReturnEmptyProductsList(){

        when(productRepository.findAll()).thenReturn(List.of());

        var productsList = productService.getAllProducts();

        assertThat(productsList).isEmpty();
    }
}
