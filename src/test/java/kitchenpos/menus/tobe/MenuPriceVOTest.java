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

class MenuPriceVOTest {
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

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> createMenuRequest(
                "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final ToBeMenu expected = createMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
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
        if(Objects.isNull(price)){
            throw new IllegalArgumentException();
        }
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
