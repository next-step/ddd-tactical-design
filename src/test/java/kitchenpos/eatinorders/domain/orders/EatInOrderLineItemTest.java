package kitchenpos.eatinorders.domain.orders;

import kitchenpos.eatinorders.infrastructure.MenuClientImpl;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderLineItemTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private MenuClient menuClient;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuClient = new MenuClientImpl(menuRepository);
    }

    @DisplayName("EatInOrderLineItem를 생성할 수 있다.")
    @Test
    void create() {
        // from 정적 팩터리 메소드를 사용해서 생성하는 테스트
        final Menu menu = menuRepository.save(menu(productRepository));
        final OrderedMenus orderedMenus = menuClient.getOrderedMenuByMenuIds(List.of(menu.getId()));
        final EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.from(
                new EatInOrderLineItemMaterial(menu.getId(), 2L),
                orderedMenus,
                menuClient
        );
        assertAll(
                () -> assertThat(eatInOrderLineItem).isNotNull(),
                () -> assertThat(eatInOrderLineItem.getMenuId()).isEqualTo(menu.getId()),
                () -> assertThat(eatInOrderLineItem.getQuantity()).isEqualTo(2L),
                () -> assertThat(eatInOrderLineItem.getPrice()).isEqualTo(menu.getPriceValue())
        );
    }

    @DisplayName("EatInOrderLineItem 생성 시 EatInOrderLineItemMaterial이 비어있으면 예외를 던진다.")
    @Test
    void createWithNotExistsMenu() {
        assertThatThrownBy(() -> EatInOrderLineItem.from(
                new EatInOrderLineItemMaterial(UUID.randomUUID(), 2L),
                new OrderedMenus(Collections.emptyList()),
                menuClient
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("EatInOrderLineItem 생성 시 Menu가 Not Displayed 상태면 예외를 던진다.")
    @Test
    void createWithNotDisplayedMenu() {
        final Menu menu = menuRepository.save(menu(productRepository));
        menu.hide();
        OrderedMenus orderedMenus = menuClient.getOrderedMenuByMenuIds(List.of(menu.getId()));
        assertThatThrownBy(() -> EatInOrderLineItem.from(
                new EatInOrderLineItemMaterial(menu.getId(), 2L),
                orderedMenus,
                menuClient
        )).isInstanceOf(IllegalStateException.class);
    }
}
