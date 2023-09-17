package kitchenpos.menus.tobe.domain;

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
        MenuProducts menuProducts = MenuProducts.create(Map.of(UUID.randomUUID(), 1L));
        assertThat(menuProducts).isEqualTo(MenuProducts.create(Map.of(UUID.randomUUID(), 1L)));
    }

    @DisplayName("수량이 0보다 작은 메뉴상품이 있으면 예외를 반환한다.")
    @Test
    void quantity() {
        Map<UUID, Long> productQuantityMap = Map.of(
                UUID.randomUUID(), -2L,
                UUID.randomUUID(), 1L,
                UUID.randomUUID(), 1L
        );

        assertThatThrownBy(() -> MenuProducts.create(productQuantityMap))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ILLEGAL_QUANTITY);
    }

    @DisplayName("메뉴상품의 상품ID를 반환한다.")
    @Test
    void getMenuProductIds() {
        List<UUID> uuids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        Map<UUID, Long> productQuantityMap = Map.of(
                uuids.get(0), 1L,
                uuids.get(1), 5L,
                uuids.get(2), 10L
        );

        MenuProducts menuProducts = MenuProducts.create(productQuantityMap);

        List<UUID> result = menuProducts.getMenuProductIds();

        assertThat(result.size()).isEqualTo(3);
    }

}
