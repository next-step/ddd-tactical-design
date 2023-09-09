package kitchenpos.menus.tobe;

import kitchenpos.menus.application.InMemoryMenuGroupRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.application.ToBeMenuService;
import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuGroupRepository;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ToBeProduct;
import kitchenpos.products.tobe.domain.ToBeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductQuantityVOTest {
    private ToBeMenuRepository toBeMenuRepository;
    private ToBeMenuGroupRepository menuGroupRepository;
    private ToBeProductRepository toBeProductRepository;
    private PurgomalumClient purgomalumClient;
    private ToBeMenuService menuService;
    private UUID menuGroupId;
    private ToBeProduct product;

    @BeforeEach
    void setUp() {
        toBeMenuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        toBeProductRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        menuService = new ToBeMenuService(toBeMenuRepository, menuGroupRepository, toBeProductRepository, purgomalumClient);
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        product = toBeProductRepository.save(new ToBeProduct("후라이드", BigDecimal.valueOf(16_000L),purgomalumClient));
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        assertThatThrownBy(() -> createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), -1L))).isInstanceOf(IllegalArgumentException.class);
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final ToBeMenuProduct... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final ToBeMenuProduct... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<ToBeMenuProduct> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<ToBeMenuProduct> menuProducts
    ) {
        final ToBeMenu menu = new ToBeMenu(name,price.longValue(),menuGroupId,displayed,purgomalumClient,menuProducts);
        return menu;
    }

    private static ToBeMenuProduct createMenuProductRequest(final UUID productId, final long quantity) {
        final ToBeMenuProduct menuProduct = new ToBeMenuProduct(productId, quantity);
        return menuProduct;
    }

    private ToBeMenu changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ToBeMenu changePriceRequest(final BigDecimal price) {
        final ToBeMenu menu = new ToBeMenu();
        menu.changePrice(price);
        return menu;
    }
}
