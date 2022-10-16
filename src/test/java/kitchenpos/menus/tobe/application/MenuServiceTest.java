package kitchenpos.menus.tobe.application;


import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.ToBeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("메뉴 서비스")
class MenuServiceTest {

    private MenuRepository menuRepository;
    private MenuService menuService;
    private Product product;
    private MenuGroup menuGroup;
    private ProductRepository productRepository;
    private MenuGroupRepository menuGroupRepository;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(menuRepository, productRepository, menuGroupRepository);
        product = productRepository.save(product("후라이드", 16_000L));
        menuGroup = menuGroupRepository.save(menuGroup("메뉴그룹"));
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu("후라이드치킨", 19_000L, true, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
        menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu("후라이드치킨", 19_000L, true, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @Test
    void changeNegativeMenuPrice() {
        final UUID menuId = menuRepository.save(menu("후라이드치킨", 19_000L, true, menuProduct(product, 2L))).getId();
        BigDecimal price = BigDecimal.valueOf(-1);
        assertThatThrownBy(() -> menuService.changePrice(menuId, new ChangePriceRequest(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @Test
    void createEmptyProductMenu() {
        String menuName = "후라이드치킨";
        menuRepository.save(menu(menuName, 19_000L, true, menuProduct(product, 2L))).getId();
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(UUID.randomUUID(), "상품명", BigDecimal.ONE), BigDecimal.ONE));
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(menuGroup.getId(), "메뉴명", BigDecimal.valueOf(10), menuProductRequests)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("해당하는 상품이 업습니다.");
    }

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @Test
    void createMenuProductSize() {
        String menuName = "후라이드치킨";
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        BigDecimal productPrice = BigDecimal.ONE;
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(product.getId(), "상품명", productPrice), BigDecimal.ONE));
        BigDecimal menuPrice = BigDecimal.valueOf(-1);
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(menuGroup.getId(), menuName, menuPrice, menuProductRequests)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 가격은 0원 이하일 수 없습니다.");
    }

    @DisplayName("메뉴명은 null 이나 공백일 수 없습니다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createMenuProductSize(String menuName) {
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(product.getId(), "상품명", BigDecimal.ONE), BigDecimal.ONE));
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(menuGroup.getId(), menuName, BigDecimal.valueOf(3000), menuProductRequests)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴명은 null 이나 공백일 수 없습니다.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void validateSum() {
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(product.getId(), "상품명", BigDecimal.ONE), BigDecimal.ONE));
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(menuGroup.getId(), "메뉴명", BigDecimal.valueOf(3001), menuProductRequests)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없습니다.");
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @Test
    void createMenuNotProduct() {
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(UUID.randomUUID(), "상품명", BigDecimal.valueOf(3002)), BigDecimal.ONE));
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(menuGroup.getId(), "메뉴명", BigDecimal.valueOf(3001), menuProductRequests)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("해당하는 상품이 업습니다.");
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @Test
    void createMenuQuantity() {
        List<MenuProductRequest> menuProductRequests = new ArrayList<>();
        menuProductRequests.add(new MenuProductRequest(new ProductRequest(UUID.randomUUID(), "상품명", BigDecimal.valueOf(3002)), BigDecimal.ONE));
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(UUID.randomUUID(), "메뉴명", BigDecimal.valueOf(3001), menuProductRequests)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("해당하는 메뉴 그룹이 업습니다.");
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changeMenuPrice() {
        final UUID menuId = menuRepository.save(menu("후라이드치킨", 19_000L, true, menuProduct(product, 2L))).getId();
        BigDecimal price = BigDecimal.valueOf(300);
        assertDoesNotThrow(() -> menuService.changePrice(menuId, new ChangePriceRequest(price)));
    }

}
