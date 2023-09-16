package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.application.MenuProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.ILLEGAL_QUANTITY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("메뉴상품목록 테스트")
class NewMenuProductsTest {

    @DisplayName("메뉴상품목록 생성 성공")
    @Test
    void create() {
        MenuProducts menuProducts = MenuProducts.create(List.of(MenuProductCreateRequest.create(UUID.randomUUID(), 1L)));
        assertThat(menuProducts).isEqualTo(MenuProducts.create(List.of(MenuProductCreateRequest.create(UUID.randomUUID(), 1L))));
    }

    @DisplayName("수량이 0보다 작은 메뉴상품이 있으면 예외를 반환한다.")
    @Test
    void quantity() {
        List<MenuProductCreateRequest> requests = List.of(
                MenuProductCreateRequest.create(UUID.randomUUID(), -2L),
                MenuProductCreateRequest.create(UUID.randomUUID(), 1L),
                MenuProductCreateRequest.create(UUID.randomUUID(), 1L)
        );

        assertThatThrownBy(() -> MenuProducts.create(requests))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ILLEGAL_QUANTITY);
    }

    @DisplayName("메뉴상품의 상품ID를 반환한다.")
    @Test
    void getMenuProductIds() {
        List<UUID> uuids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        List<MenuProductCreateRequest> requests = List.of(
                MenuProductCreateRequest.create(uuids.get(0), 1L),
                MenuProductCreateRequest.create(uuids.get(1), 5L),
                MenuProductCreateRequest.create(uuids.get(2), 10L)
        );
        MenuProducts menuProducts = MenuProducts.create(requests);

        List<UUID> result = menuProducts.getMenuProductIds();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0)).isEqualTo(uuids.get(0));
        assertThat(result.get(1)).isEqualTo(uuids.get(1));
        assertThat(result.get(2)).isEqualTo(uuids.get(2));
    }

}
