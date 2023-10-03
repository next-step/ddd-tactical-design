package kitchenpos.deliveryorders.domain;

import kitchenpos.deliveryorders.application.InMemoryMenuClient;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.Fixtures.displayedMenu;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryOrderLineItemTest {

    private MenuRepository menuRepository;
    private MenuClient menuClient;
    private ProductRepository productRepository;

    Product product;
    Menu displayedMenu;
    Menu hidedMenu;
    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuClient = new InMemoryMenuClient(menuRepository);
        productRepository = new InMemoryProductRepository();
        product = productRepository.save(product());
        displayedMenu = menuRepository.save(displayedMenu(1L, menuProduct(product, 1L)));
        hidedMenu = menuRepository.save(menu(1L, menuProduct(product, 1L)));
    }

    @DisplayName("주문 항목의 메뉴는 필수로 존재하는 메뉴여야 한다.")
    @Test
    void menu_of_order_line_items_must_be_exist() {

        assertThatThrownBy(() -> DeliveryOrderLineItem.of(UUID.randomUUID(), new DeliveryOrderLineItemQuantity(1L), new DeliveryOrderLineItemPrice(BigDecimal.TEN), menuClient))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("메뉴가 존재하지 않습니다.");
    }

    @DisplayName("주문 항목의 메뉴는 노출 상태여야 한다.")
    @Test
    void menu_of_order_line_items_must_be_is_displayed() {
        assertThatThrownBy(() -> DeliveryOrderLineItem.of(hidedMenu.getId(), new DeliveryOrderLineItemQuantity(1L), new DeliveryOrderLineItemPrice(BigDecimal.TEN), menuClient))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 항목의 메뉴의 가격은 메뉴 가격과 같아야 한다.")
    @Test
    void menu_price_and_price_of_order_line_items_must_be_same() {
        assertThatThrownBy(() -> DeliveryOrderLineItem.of(displayedMenu.getId(), new DeliveryOrderLineItemQuantity(1L), new DeliveryOrderLineItemPrice(BigDecimal.TEN), menuClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

}