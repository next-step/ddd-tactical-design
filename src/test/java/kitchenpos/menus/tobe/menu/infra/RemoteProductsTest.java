package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.application.dto.ProductQuantityDto;
import kitchenpos.menus.tobe.menu.domain.MenuProduct;
import kitchenpos.products.tobe.ProductFixtures;
import kitchenpos.products.tobe.application.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        final List<ProductQuantityDto> productQuantityDtos = Arrays.asList(
                new ProductQuantityDto(ProductFixtures.productFriedChicken().getId(), 1L),
                new ProductQuantityDto(ProductFixtures.productSeasonedChicken().getId(), 2L)
        );

        final List<Long> productIds = Arrays.asList(
                ProductFixtures.productFriedChicken().getId(),
                ProductFixtures.productSeasonedChicken().getId()
        );

        given(productService.findAllById(productIds)).willReturn(Arrays.asList(
                ProductFixtures.productFriedChicken(),
                ProductFixtures.productSeasonedChicken()
        ));

        // when
        final List<MenuProduct> menuProducts = remoteProducts.getMenuProductsByProductIdsAndQuantities(productQuantityDtos);

        // then
        assertThat(menuProducts.get(0).getProductId()).isEqualTo(ProductFixtures.productFriedChicken().getId());
        assertThat(menuProducts.get(0).getPrice()).isEqualTo(ProductFixtures.productFriedChicken().getPrice());
        assertThat(menuProducts.get(0).getQuantity()).isEqualTo(1L);
        assertThat(menuProducts.get(1).getProductId()).isEqualTo(ProductFixtures.productSeasonedChicken().getId());
        assertThat(menuProducts.get(1).getPrice()).isEqualTo(ProductFixtures.productSeasonedChicken().getPrice());
        assertThat(menuProducts.get(1).getQuantity()).isEqualTo(2L);
    }

    /*
    @DisplayName("(Menu -> Product) Product 를 조회 시, productId가 중복될 수 없다.")
    @Test
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenProductDuplicated() {
        // given
        final List<ProductQuantityDto> productQuantityDtos = Arrays.asList(
                new ProductQuantityDto(1L, 1L),
                new ProductQuantityDto(1L, 2L)
        );

        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getMenuProductsByProductIdsAndQuantities(productQuantityDtos);
        }).isInstanceOf(IllegalArgumentException.class);
    }
     */

    /*
    @DisplayName("(Menu -> Product) Product 를 조회 시, productId 가 입력되어야한다.")
    @ParameterizedTest
    @MethodSource(value = "provideInvalidProductIdsAndQuantities")
    void getMenuProductsByProductIdsAndQuantitiesFailsWhenValueEmpty(final List<ProductQuantityDto> productQuantityDtos) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            remoteProducts.getMenuProductsByProductIdsAndQuantities(productQuantityDtos);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidProductIdsAndQuantities() {
        return Stream.of(
                null,
                Arrays.asList(
                        new ProductQuantityDto(null, 1L),
                        new ProductQuantityDto(1L, 1L)
                )
        );
    }
     */
}
