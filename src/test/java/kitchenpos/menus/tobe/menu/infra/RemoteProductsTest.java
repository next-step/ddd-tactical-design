package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.domain.MenuProduct;
import kitchenpos.products.tobe.ProductFixtures;
import kitchenpos.products.tobe.application.ProductService;
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
    
    @DisplayName("(Menu -> Product) MenuProduct 를 조회할 수 있다.")
    @Test
    void getMenuProductsByProductIdsAndQuantities() {
        // given
        final List<Long> productIds = Arrays.asList(1L, 2L);
        final List<Long> quantities = Arrays.asList(1L, 2L);

        given(productService.findAllById(productIds)).willReturn(Arrays.asList(
                ProductFixtures.productFriedChicken(),
                ProductFixtures.productSeasonedChicken()
        ));

        // when
        final List<MenuProduct> menuProducts = remoteProducts.getMenuProductsByProductIdsAndQuantities(productIds, quantities);

        // then
        assertThat(menuProducts.get(0).getProductId()).isEqualTo(ProductFixtures.productFriedChicken().getId());
        assertThat(menuProducts.get(0).getPrice()).isEqualTo(ProductFixtures.productFriedChicken().getPrice());
        assertThat(menuProducts.get(0).getQuantity()).isEqualTo(quantities.get(0));
        assertThat(menuProducts.get(1).getProductId()).isEqualTo(ProductFixtures.productSeasonedChicken().getId());
        assertThat(menuProducts.get(1).getPrice()).isEqualTo(ProductFixtures.productSeasonedChicken().getPrice());
        assertThat(menuProducts.get(1).getQuantity()).isEqualTo(quantities.get(1));
    }

    @DisplayName("(Menu -> Product) MenuProduct 를 조회 시, productId가 중복될 수 없다.")
    @Test
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenProductDuplicated() {
        // given
        final List<Long> productIds = Arrays.asList(1L, 1L);
        final List<Long> quantities = Arrays.asList(1L, 2L);

        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getMenuProductsByProductIdsAndQuantities(productIds, quantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("(Menu -> Product) MenuProduct 를 조회 시, productId와 quantities가 입력되어야한다.")
    @ParameterizedTest
    @MethodSource(value = "provideInvalidProductIdsAndQuantities")
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenValueEmpty(final List<Long> productIds, final List<Long> quantities) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getMenuProductsByProductIdsAndQuantities(productIds, quantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidProductIdsAndQuantities() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new ArrayList<>(), null),
                Arguments.of(null, new ArrayList<>())
        );
    }

    @DisplayName("(Menu -> Product) MenuProduct 를 조회 시, productId와 quantities의 길이가 같아야한다.")
    @Test
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenLengthNotSame() {
        // given
        final List<Long> productIds = Arrays.asList(1L, 1L);
        final List<Long> quantities = Arrays.asList(1L, 2L, 2L);

        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getMenuProductsByProductIdsAndQuantities(productIds, quantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
