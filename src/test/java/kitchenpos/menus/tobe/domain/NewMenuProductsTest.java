package kitchenpos.menus.tobe.domain;

import kitchenpos.NewFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.ILLEGAL_QUANTITY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("메뉴상품목록 테스트")
class NewMenuProductsTest {

    @DisplayName("메뉴상품목록 생성 성공")
    @Test
    void create() {
        NewProduct newProduct = NewFixtures.newProduct(1_000L);
        MenuProducts menuProducts = MenuProducts.create(List.of(NewMenuProduct.create(newProduct, 1L)));
        assertThat(menuProducts).isEqualTo(MenuProducts.create(List.of(NewMenuProduct.create(newProduct, 1L))));
    }

    @DisplayName("메뉴상품의 상품ID를 반환한다.")
    @Test
    void getMenuProductIds() {
        List<NewProduct> products = List.of(
                NewFixtures.newProduct(1_000L),
                NewFixtures.newProduct(2_000L),
                NewFixtures.newProduct(3_000L)
        );
        List<NewMenuProduct> newMenuProducts = List.of(
                NewMenuProduct.create(products.get(0), 1L),
                NewMenuProduct.create(products.get(1), 5L),
                NewMenuProduct.create(products.get(2), 10L)
        );

        MenuProducts menuProducts = MenuProducts.create(newMenuProducts);

        List<UUID> result = menuProducts.getMenuProductIds();

        assertThat(result.size()).isEqualTo(3);
    }

}
