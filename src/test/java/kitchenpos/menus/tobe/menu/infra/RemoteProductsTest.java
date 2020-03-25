package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.products.tobe.ProductFixtures;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RemoteProductsTest {

    @Mock
    ProductService productService;

    @InjectMocks
    RemoteProducts remoteProducts;

    @DisplayName("(Menu -> Product) Product 를 조회할 수 있다.")
    @Test
    void getMenuProductsByProductIdsAndQuantities() {
        // given
        final List<Long> productIds = Arrays.asList(1L, 2L);

        given(productService.findAllById(productIds)).willReturn(Arrays.asList(
                ProductFixtures.productFriedChicken(),
                ProductFixtures.productSeasonedChicken()
        ));

        // when
        final List<Product> products = remoteProducts.getProductsByProductIds(productIds);

        // then
        assertThat(products.get(0).getId()).isEqualTo(ProductFixtures.productFriedChicken().getId());
        assertThat(products.get(0).getName()).isEqualTo(ProductFixtures.productFriedChicken().getName());
        assertThat(products.get(0).getPrice()).isEqualTo(ProductFixtures.productFriedChicken().getPrice());
        assertThat(products.get(1).getId()).isEqualTo(ProductFixtures.productSeasonedChicken().getId());
        assertThat(products.get(1).getName()).isEqualTo(ProductFixtures.productSeasonedChicken().getName());
        assertThat(products.get(1).getPrice()).isEqualTo(ProductFixtures.productSeasonedChicken().getPrice());
    }

    @DisplayName("(Menu -> Product) Product 를 조회 시, productId가 중복될 수 없다.")
    @Test
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenProductDuplicated() {
        // given
        final List<Long> productIds = Arrays.asList(1L, 1L);

        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getProductsByProductIds(productIds);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("(Menu -> Product) Product 를 조회 시, productId 가 입력되어야한다.")
    @ParameterizedTest
    @MethodSource(value = "provideInvalidProductIdsAndQuantities")
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenValueEmpty(final List<Long> productIds) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getProductsByProductIds(productIds);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidProductIdsAndQuantities() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new ArrayList<>(), null),
                Arguments.of(null, new ArrayList<>())
        );
    }

    @DisplayName("(Menu -> Product) Product 를 조회 시, productId와 quantities의 길이가 같아야한다.")
    @Test
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenLengthNotSame() {
        // given
        final List<Long> productIds = Arrays.asList(1L, 1L);

        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getProductsByProductIds(productIds);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
