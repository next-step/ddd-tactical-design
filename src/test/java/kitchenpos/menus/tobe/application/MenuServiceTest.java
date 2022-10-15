package kitchenpos.menus.tobe.application;

import kitchenpos.ToBeFixtures;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.dto.menu.ChangePriceRequest;
import kitchenpos.menus.tobe.dto.menu.MenuCreateRequest;
import kitchenpos.menus.tobe.dto.menu.MenuProductRequest;
import kitchenpos.menus.tobe.dto.menu.ProductRequest;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.ToBeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 서비스")
class MenuServiceTest {

    private MenuRepository menuRepository;
    private MenuService menuService;
    private Product product;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(menuRepository, productRepository);
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
        menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @Test
    void changeNegativeMenuPrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        BigDecimal price = BigDecimal.valueOf(-1);
        assertThatThrownBy(() -> menuService.changePrice(menuId, new ChangePriceRequest(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @Test
    void createEmptyProductMenu() {
        menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(UUID.randomUUID())));
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(BigDecimal.valueOf(10), menuProductRequests)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("해당하는 상품이 업습니다.");
    }

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @Test
    void createMenuProductSize() {
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(product.getId())));
        BigDecimal price = BigDecimal.valueOf(-1);
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(price, menuProductRequests)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 가격은 0원 이하일 수 없습니다.");
    }

}
