package kitchenpos.products.todo.application;

import kitchenpos.products.todo.domain.JpaProductRepository;
import kitchenpos.products.todo.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.products.todo.Fixtures.friedChicken;
import static kitchenpos.products.todo.Fixtures.seasonedChicken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private JpaProductRepository repository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(repository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final Product expected = friedChicken();
        given(repository.save(expected)).willReturn(expected);

        // when
        final Product actual = productService.create(expected);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        // given
        final Product expected = friedChicken();

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> expected.setPrice(price));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Product friedChicken = repository.save(friedChicken());
        final Product seasonedChicken = repository.save(seasonedChicken());
        given(repository.findAll()).willReturn(Arrays.asList(friedChicken, seasonedChicken));

        // when
        final List<Product> actual = productService.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken, seasonedChicken));
    }

    private void assertProduct(final Product expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}