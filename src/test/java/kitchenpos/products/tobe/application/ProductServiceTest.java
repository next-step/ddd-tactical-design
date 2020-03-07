package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @DisplayName("새로운 제품을 생성할 수 있다.")
    @Test
    void add() {
        // given
        String name = "제품1";
        Long price = 1000L;

        given(productRepository.save(any(Product.class))).willReturn(new Product(name, price));

        // when
        Product result = productService.add(name, price);

        // then
//        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getPrice()).isEqualTo(price);
    }

    @DisplayName("제품 생성 시, 제품 가격은 0원 이상이다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = { -1L, -1000L, -1000000000L })
    void addOnlyWhenPriceIsPositive(Long invalidPrice) {
        // given
        String name = "제품1";

        // when
        // then
        assertThatThrownBy(() -> {
            productService.add(name, invalidPrice);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("제품 생성 시, 제품명이 입력되어야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "   " })
    void addOnlyWhenNameEntered(String invalidName) {
        // given
        Long price = 1000L;

        // when
        // then
        assertThatThrownBy(() -> {
            productService.add(invalidName, price);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("제품 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Product product1 = new Product("삼선짬뽕", 8500L);
        final Product product2 = new Product("고추짬뽕", 9000L);
        final Product product3 = new Product("삼선간짜장", 8000L);
        final List<Product> products = Arrays.asList(product1, product2, product3);

        given(productRepository.findAll()).willReturn(products);

        // when
        final List<Product> result = productService.list();

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(products);
    }
}
