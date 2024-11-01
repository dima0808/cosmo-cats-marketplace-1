package com.example.cosmocatsmarketplace.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import com.example.cosmocatsmarketplace.service.impl.ProductServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

  private ProductServiceImpl productService;

  @BeforeEach
  void setUp() {
    productService = new ProductServiceImpl();
  }

  @Test
  void testGetAllProducts() {
    List<Product> products = productService.getAllProducts();
    assertEquals(3, products.size());
  }

  @Test
  void testGetProductById() {
    Product product = productService.getProductById(1L);
    assertNotNull(product);
    assertEquals(1L, product.getId());
  }

  @Test
  void testGetProductById_NotFound() {
    assertThrows(ProductNotFoundException.class, () -> productService.getProductById(99L));
  }

  @Test
  void testCreateProduct() {
    Product newProduct = Product.builder().name("New Product").build();
    Product createdProduct = productService.createProduct(newProduct);
    assertNotNull(createdProduct);
    assertEquals(4L, createdProduct.getId());
  }

  @Test
  void testUpdateProduct() {
    Product updatedProduct = Product.builder().id(1L).name("Updated Product").build();
    Product result = productService.updateProduct(updatedProduct);
    assertNotNull(result);
    assertEquals("Updated Product", result.getName());
    assertEquals(1L, result.getId());
  }

  @Test
  void testDeleteProductById() {
    productService.deleteProductById(1L);
    assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
  }
}
