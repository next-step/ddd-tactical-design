package kitchenpos.menus.ui.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static kitchenpos.Fixtures.product;
import static kitchenpos.menus.application.MenuServiceTest.createMenuProductRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductRequestsTest {

    @DisplayName("메뉴 상품목록을 생성한다.")
    @Test
    void createMenuProductRequests() {
        MenuProductRequests menuProductRequests = new MenuProductRequests(Arrays.asList(createMenuProductRequest(product().getId(), 1L)));

        assertThat(menuProductRequests).isNotNull();
        assertAll(
                () -> assertThat(menuProductRequests.getMenuProductRequests()).isNotNull(),
                () -> assertThat(menuProductRequests.getMenuProductRequests()).hasSize(1)
        );
    }

    @DisplayName("메뉴 상품목록은 null 또는 빈 목록이 될수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProductRequest> menuProducts) {
        assertThatThrownBy(
                () -> new MenuProductRequests(menuProducts)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList())
        );
    }
}
