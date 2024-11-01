package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final List<Product> products = loadProducts();

  @Override
  public List<Product> getAllProducts() {
    return products;
  }

  @Override
  public Product getProductById(Long id) {
    return products.stream()
        .filter(product -> product.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Override
  public Product createProduct(Product product) {
    product.setId((long) (products.size() + 1));
    products.add(product);
    return product;
  }

  @Override
  public Product updateProduct(Product product) {
    Product existingProduct = getProductById(product.getId());
    products.remove(existingProduct);
    products.add(product);
    return product;
  }

  @Override
  public void deleteProductById(Long id) {
    getProductById(id);
    products.removeIf(product -> product.getId().equals(id));
  }

  private List<Product> loadProducts() {
    List<Product> products = new ArrayList<>();
    products.add(Product.builder()
        .id(1L)
        .name("CosmoCats T-Shirt")
        .description("A T-shirt with the CosmoCats logo")
        .price(20)
        .categories(List.of(CategoryType.CLOTHES))
        .build());
    products.add(Product.builder()
        .id(2L)
        .name("CosmoCats Mug")
        .description("A mug with the CosmoCats logo")
        .price(10)
        .categories(List.of(CategoryType.ACCESSORIES))
        .build());
    products.add(Product.builder()
        .id(3L)
        .name("CosmoCats Sticker")
        .description("A sticker with the CosmoCats logo")
        .price(5)
        .categories(List.of(CategoryType.ACCESSORIES))
        .build());
    return products;
  }
}
