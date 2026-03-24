package com.tscore.service;

import com.tscore.exception.NotFoundException;
import com.tscore.model.Category;
import com.tscore.model.Product;
import com.tscore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private Product laptop;
    private Product book;
    private Category electronics;
    private Category books;

    @BeforeEach
    void setUp() {
        electronics = new Category();
        electronics.id = 1L;
        electronics.name = "Electronics";

        books = new Category();
        books.id = 2L;
        books.name = "Books";

        laptop = new Product();
        laptop.id = 1L;
        laptop.name = "Laptop Pro";
        laptop.description = "15-inch laptop";
        laptop.price = new BigDecimal("1499.99");
        laptop.imageUrl = "https://example.com/laptop.jpg";
        laptop.category = electronics;

        book = new Product();
        book.id = 2L;
        book.name = "Clean Code";
        book.description = "Agile craftsmanship";
        book.price = new BigDecimal("35.00");
        book.imageUrl = null;
        book.category = books;
    }

    @Test
    void findAll_returnsMappedDTOs() {
        when(productRepository.listAll()).thenReturn(List.of(laptop, book));

        var result = productService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting("name").containsExactly("Laptop Pro", "Clean Code");
        assertThat(result.get(0).categoryName()).isEqualTo("Electronics");
        assertThat(result.get(0).imageUrl()).isEqualTo("https://example.com/laptop.jpg");
    }

    @Test
    void findAll_productWithNullCategory_returnsNullCategoryName() {
        laptop.category = null;
        when(productRepository.listAll()).thenReturn(List.of(laptop));

        var result = productService.findAll();

        assertThat(result.get(0).categoryName()).isNull();
    }

    @Test
    void findById_existingId_returnsDTO() {
        when(productRepository.findByIdOptional(1L)).thenReturn(Optional.of(laptop));

        var result = productService.findById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Laptop Pro");
        assertThat(result.price()).isEqualByComparingTo("1499.99");
        assertThat(result.categoryName()).isEqualTo("Electronics");
    }

    @Test
    void findById_nonExistingId_throwsNotFoundException() {
        when(productRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void findByCategory_returnsFilteredDTOs() {
        when(productRepository.findByCategory(1L)).thenReturn(List.of(laptop));

        var result = productService.findByCategory(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Laptop Pro");
    }

    @Test
    void findByCategory_noProducts_returnsEmptyList() {
        when(productRepository.findByCategory(99L)).thenReturn(List.of());

        var result = productService.findByCategory(99L);

        assertThat(result).isEmpty();
    }
}
