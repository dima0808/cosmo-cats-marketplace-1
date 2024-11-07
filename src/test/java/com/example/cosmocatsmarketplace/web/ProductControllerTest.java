package com.example.cosmocatsmarketplace.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @MockBean
  private ProductMapper productMapper;

  private ObjectMapper objectMapper;
  private Product product;
  private ProductDto productDto;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();

    product = Product.builder()
        .id(1L)
        .name("Test Product")
        .description("Test Description")
        .price(100)
        .categories(List.of(CategoryType.ELECTRONICS))
        .build();

    productDto = ProductDto.builder()
        .name("Test Product")
        .description("Test Description")
        .price(100)
        .categories(List.of("Electronics"))
        .build();
  }

  @Test
  void testGetAllProducts() throws Exception {
    List<Product> products = List.of(product);
    List<ProductDto> productDtos = List.of(productDto);

    when(productService.getAllProducts()).thenReturn(products);
    when(productMapper.toProductDto(any(Product.class))).thenReturn(productDtos.get(0));

    mockMvc.perform(get("/api/v1/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testGetProductById() throws Exception {
    when(productService.getProductById(1L)).thenReturn(product);
    when(productMapper.toProductDto(product)).thenReturn(productDto);

    mockMvc.perform(get("/api/v1/products/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test Product"))
        .andExpect(jsonPath("$.description").value("Test Description"))
        .andExpect(jsonPath("$.price").value(100))
        .andExpect(jsonPath("$.categories[0]").value("Electronics"));
  }

  @Test
  void testCreateProduct() throws Exception {
    when(productMapper.toProduct(any(ProductDto.class))).thenReturn(product);
    when(productService.createProduct(product)).thenReturn(product);
    when(productMapper.toProductDto(product)).thenReturn(productDto);
    when(productMapper.toCategoriesString(List.of(CategoryType.values())))
        .then(invocation -> List.of("Electronics"));

    String productDtoJson = objectMapper.writeValueAsString(productDto);
    System.out.println("ProductDto JSON: " + productDtoJson);

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productDtoJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Test Product"))
        .andExpect(jsonPath("$.description").value("Test Description"))
        .andExpect(jsonPath("$.price").value(100))
        .andExpect(jsonPath("$.categories[0]").value("Electronics"));
  }

  @Test
  void testCreateProductWithInvalidPrice() throws Exception {
    when(productMapper.toCategoriesString(List.of(CategoryType.values())))
        .then(invocation -> List.of("Electronics"));

    ProductDto invalidProductDto = ProductDto.builder()
        .name("Test Product")
        .description("Test Description")
        .price(-1)
        .categories(List.of("Electronics"))
        .build();

    String productDtoJson = objectMapper.writeValueAsString(invalidProductDto);

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Price cannot be 0 or less"));
  }

  @Test
  void testCreateProductWithoutName() throws Exception {
    when(productMapper.toCategoriesString(List.of(CategoryType.values())))
        .then(invocation -> List.of("Electronics"));

    ProductDto invalidProductDto = ProductDto.builder()
        .description("Test Description")
        .price(100)
        .categories(List.of("Electronics"))
        .build();

    String productDtoJson = objectMapper.writeValueAsString(invalidProductDto);

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Name is mandatory"));
  }

  @Test
  void testCreateProductWithoutDescription() throws Exception {
    when(productMapper.toCategoriesString(List.of(CategoryType.values())))
        .then(invocation -> List.of("Electronics"));

    ProductDto invalidProductDto = ProductDto.builder()
        .name("Test Product")
        .price(100)
        .categories(List.of("Electronics"))
        .build();

    String productDtoJson = objectMapper.writeValueAsString(invalidProductDto);

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Description is mandatory"));
  }

  @Test
  void testCreateProductWithEmptyCategories() throws Exception {
    ProductDto invalidProductDto = ProductDto.builder()
        .name("Test Product")
        .description("Test Description")
        .price(100)
        .categories(List.of())
        .build();

    String productDtoJson = objectMapper.writeValueAsString(invalidProductDto);

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Categories cannot be empty"));
  }

  @Test
  void testCreateProductWithInvalidCategory() throws Exception {
    when(productMapper.toCategoriesString(List.of(CategoryType.values())))
        .then(invocation -> List.of("Electronics"));

    ProductDto invalidProductDto = ProductDto.builder()
        .name("Test Product")
        .description("Test Description")
        .price(100)
        .categories(List.of("Paskhalko"))
        .build();

    String productDtoJson = objectMapper.writeValueAsString(invalidProductDto);

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid Category"));
  }

  @Test
  void testUpdateProduct() throws Exception {
    when(productMapper.toProduct(any(ProductDto.class))).thenReturn(product);
    when(productService.updateProduct(product)).thenReturn(product);
    when(productMapper.toProductDto(product)).thenReturn(productDto);

    mockMvc.perform(put("/api/v1/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test Product"))
        .andExpect(jsonPath("$.description").value("Test Description"))
        .andExpect(jsonPath("$.price").value(100))
        .andExpect(jsonPath("$.categories[0]").value("Electronics"));
  }

  @Test
  void testDeleteProduct() throws Exception {
    doNothing().when(productService).deleteProductById(1L);

    mockMvc.perform(delete("/api/v1/products/1"))
        .andExpect(status().isNoContent());
  }
}
