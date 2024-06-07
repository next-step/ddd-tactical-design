package kitchenpos.menus.tobe.application;

import kitchenpos.menus.application.TobeInMemoryMenuGroupRepository;
import kitchenpos.menus.application.TobeInMemoryMenuRepository;
import kitchenpos.menus.fixture.TobeMenuServiceFixture;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.TobeMenu;
import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.tobe.dto.request.MenuCreateRequest;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.TobeInMemoryProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TobeMenuServiceTest {
    private TobeMenuRepository menuRepository;
    private TobeMenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private PurgomalumClient purgomalumClient;
    private TobeMenuService tobeMenuService;

    @BeforeEach
    void setUp() {
        menuRepository = new TobeInMemoryMenuRepository();
        menuGroupRepository = new TobeInMemoryMenuGroupRepository();
        productRepository = new TobeInMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        tobeMenuService = new TobeMenuService(menuRepository, menuGroupRepository, productRepository, purgomalumClient);
    }

    @Test
    @DisplayName("상품을 생성할 수 있다.")
    void create() {
        // given
        TobeMenuGroup menuGroup = TobeMenuServiceFixture.createMenuGroup();
        Product product = TobeMenuServiceFixture.createProduct(10000L, purgomalumClient);
        List<MenuCreateRequest.MenuProductRequest> menuProductRequests = List.of(TobeMenuServiceFixture.createMenuProductRequest(product, 2L));

        menuGroupRepository.save(menuGroup);
        productRepository.save(product);

        MenuCreateRequest request = new MenuCreateRequest("상품명", BigDecimal.valueOf(20000), true, menuGroup.getId(), menuProductRequests);

        // when
        TobeMenu createMenu = tobeMenuService.create(request);
        MenuProducts menuProducts = createMenu.menuProducts();
        // then
        assertNotNull(createMenu.id());
        assertEquals(request.name(), createMenu.name().name());
        assertEquals(request.price(), createMenu.price());
        assertEquals(1, menuProducts.size());
        assertEquals(20000L, menuProducts.calculateTotalPrice().longValue());
        assertFalse(menuProducts.isOverProductPrice(createMenu.price()));
    }

    @Test
    @DisplayName("상품명이 비어있으면 예외가 발생한다.")
    void createWithEmptyName() {
        // given
        MenuCreateRequest request = new MenuCreateRequest("", BigDecimal.valueOf(20000), true, null, null);

        // when & then
        IllegalArgumentException aThrows = Assertions.assertThrows(IllegalArgumentException.class, () -> tobeMenuService.create(request));
        assertEquals("메뉴 이름은 필수값입니다.", aThrows.getMessage());
    }

    @Test
    @DisplayName("상품가격이 비어있으면 예외가 발생한다.")
    void createWithEmptyPrice() {
        // given
        MenuCreateRequest request = new MenuCreateRequest("상품명", null, true, null, null);

        // when & then
        IllegalArgumentException aThrows = Assertions.assertThrows(IllegalArgumentException.class, () -> tobeMenuService.create(request));
        assertEquals("가격은 0 이상이어야 합니다.", aThrows.getMessage());
    }

    @Test
    @DisplayName("등록되지 않은 메뉴그룹을 참조하면 예외가 발생한다.")
    void createWithNotRegisteredMenuGroup() {
        // given
        Product product = TobeMenuServiceFixture.createProduct(10000L, purgomalumClient);
        List<MenuCreateRequest.MenuProductRequest> menuProductRequests = List.of(TobeMenuServiceFixture.createMenuProductRequest(product, 2L));

        MenuCreateRequest request = new MenuCreateRequest("상품명", BigDecimal.valueOf(20000), true, null, menuProductRequests);

        // when & then
        NoSuchElementException aThrows = Assertions.assertThrows(NoSuchElementException.class, () -> tobeMenuService.create(request));
        assertEquals("메뉴 그룹이 존재하지 않습니다.", aThrows.getMessage());
    }

    @Test
    @DisplayName("등록되지 않은 상품을 참조하면 예외가 발생한다.")
    void createWithNotRegisteredProduct() {
        // given
        TobeMenuGroup menuGroup = TobeMenuServiceFixture.createMenuGroup();
        Product product = TobeMenuServiceFixture.createProduct(10000L, purgomalumClient);
        List<MenuCreateRequest.MenuProductRequest> menuProductRequests = List.of(TobeMenuServiceFixture.createMenuProductRequest(product, 2L));

        menuGroupRepository.save(menuGroup);

        MenuCreateRequest request = new MenuCreateRequest("상품명", BigDecimal.valueOf(20000), true, menuGroup.getId(), menuProductRequests);

        // when & then
        NoSuchElementException aThrows = Assertions.assertThrows(NoSuchElementException.class, () -> tobeMenuService.create(request));
        assertEquals("상품이 존재하지 않습니다.", aThrows.getMessage());
    }

    @Test
    @DisplayName("상품의 가격이 메뉴의 가격보다 높으면 예외가 발생한다.")
    void createWithProductPriceHigherThanMenuPrice() {
        // given
        TobeMenuGroup menuGroup = TobeMenuServiceFixture.createMenuGroup();
        Product product = TobeMenuServiceFixture.createProduct(20000L, purgomalumClient);
        List<MenuCreateRequest.MenuProductRequest> menuProductRequests = List.of(TobeMenuServiceFixture.createMenuProductRequest(product, 2L));

        menuGroupRepository.save(menuGroup);
        productRepository.save(product);

        MenuCreateRequest request = new MenuCreateRequest("상품명", BigDecimal.valueOf(100000), true, menuGroup.getId(), menuProductRequests);

        // when & then
        IllegalArgumentException aThrows = Assertions.assertThrows(IllegalArgumentException.class, () -> tobeMenuService.create(request));
        assertEquals("메뉴의 가격은 메뉴에 속한 상품의 가격보다 적거나 같아야합니다.", aThrows.getMessage());
    }

    @Test
    @DisplayName("메뉴 상품이 하나도 등록되지 않으면 예외가 발생한다.")
    void createWithEmptyMenuProducts() {
        // given
        TobeMenuGroup menuGroup = TobeMenuServiceFixture.createMenuGroup();

        menuGroupRepository.save(menuGroup);

        MenuCreateRequest request = new MenuCreateRequest("상품명", BigDecimal.valueOf(20000), true, menuGroup.getId(), Collections.emptyList());

        // when & then
        IllegalArgumentException aThrows = Assertions.assertThrows(IllegalArgumentException.class, () -> tobeMenuService.create(request));
        assertEquals("1개 이상의 메뉴 상품이 필요합니다.", aThrows.getMessage());
    }

}
