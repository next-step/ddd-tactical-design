package kitchenpos.order.tobe.eatinorder.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.Fixtures;
import kitchenpos.application.InMemoryMenuRepository;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLineItemDto;
import kitchenpos.order.tobe.eatinorder.infra.MenuClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class EatInOrderLineItemsTest {

    @DisplayName("매장주문 항목 컬렉션을 생성한다")
    @Test
    void testInit() {
        // given
        List<EatInOrderLineItem> eatInOrderLineItems = List.of(
            Fixtures.eatInOrderLineItem(),
            Fixtures.eatInOrderLineItem()
        );

        // when // then
        assertDoesNotThrow(() -> new EatInOrderLineItems(eatInOrderLineItems));
    }

    @DisplayName("null 값으로는 매장주문 항목 컬렉션을 생성할 수 없다.")
    @Test
    void testInitIfNullValue() {
        // given
        List<EatInOrderLineItem> eatInOrderLineItems = null;

        // when // then
        assertThatThrownBy(() -> new EatInOrderLineItems(eatInOrderLineItems))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 컬렉션으로는 매장주문 항목 컬렉션을 생성할 수 없다.")
    @Test
    void testInitIfEmptyCollection() {
        // given
        List<EatInOrderLineItem> eatInOrderLineItems = Collections.emptyList();

        // when // then
        assertThatThrownBy(() -> new EatInOrderLineItems(eatInOrderLineItems))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가")
    @Nested
    class WithMenuClientTest {

        private MenuClient menuClient;
        private MenuRepository menuRepository;

        @BeforeEach
        void setUp() {
            this.menuRepository = new InMemoryMenuRepository();
            this.menuClient = new MenuClientImpl(menuRepository);
        }

        @DisplayName("null 또는 빈 값으로는 매장 주문 항목 컬렉션을 생성할 수 없다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testFrom(List<EatInOrderLineItemDto> dtos) {
            // when // then
            assertThatThrownBy(() -> EatInOrderLineItems.from(dtos, menuClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("존재하면 매장 주문 항목 컬렉션을 생성할 수 있다.")
        @Test
        void testFromIfContainExistMenu() {
            // given
            Menu menu = menuRepository.save(Fixtures.menu());
            var dtos = List.of(
                new EatInOrderLineItemDto(menu.getId(), menu.getPrice(), 3)
            );

            // when // then
            assertDoesNotThrow(() -> EatInOrderLineItems.from(dtos, menuClient));
        }

        @DisplayName("존재하지 않으면 매장 주문 항목 컬렉션을 생성할 수 없다.")
        @Test
        void testFromIfContainNotExistMenu() {
            // given
            var dtos = List.of(
                new EatInOrderLineItemDto(UUID.randomUUID(), BigDecimal.valueOf(10_000L), 3)
            );

            // when // then
            assertThatThrownBy(() -> EatInOrderLineItems.from(dtos, menuClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }
}
