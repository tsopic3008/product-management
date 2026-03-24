package com.tscore.service;

import com.tscore.exception.NotFoundException;
import com.tscore.model.Category;
import com.tscore.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

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
    }

    @Test
    void findAll_returnsAllCategories() {
        when(categoryRepository.listAll()).thenReturn(List.of(electronics, books));

        var result = categoryService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting("name").containsExactly("Electronics", "Books");
    }

    @Test
    void findAll_whenEmpty_returnsEmptyList() {
        when(categoryRepository.listAll()).thenReturn(List.of());

        var result = categoryService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void findById_existingId_returnsCategory() {
        when(categoryRepository.findByIdOptional(1L)).thenReturn(Optional.of(electronics));

        var result = categoryService.findById(1L);

        assertThat(result.id).isEqualTo(1L);
        assertThat(result.name).isEqualTo("Electronics");
    }

    @Test
    void findById_nonExistingId_throwsNotFoundException() {
        when(categoryRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_persistsAndReturnsCategory() {
        var newCategory = new Category();
        newCategory.name = "Sports";
        doNothing().when(categoryRepository).persist(any(Category.class));

        var result = categoryService.create(newCategory);

        verify(categoryRepository).persist(newCategory);
        assertThat(result.name).isEqualTo("Sports");
    }

    @Test
    void update_existingId_updatesName() {
        when(categoryRepository.findByIdOptional(1L)).thenReturn(Optional.of(electronics));

        var update = new Category();
        update.name = "Consumer Electronics";

        var result = categoryService.update(1L, update);

        assertThat(result.name).isEqualTo("Consumer Electronics");
    }

    @Test
    void update_nonExistingId_throwsNotFoundException() {
        when(categoryRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        var update = new Category();
        update.name = "New Name";

        assertThatThrownBy(() -> categoryService.update(99L, update))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_existingId_deletesCategory() {
        when(categoryRepository.deleteById(1L)).thenReturn(true);

        categoryService.delete(1L);

        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void delete_nonExistingId_throwsNotFoundException() {
        when(categoryRepository.deleteById(99L)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.delete(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }
}
