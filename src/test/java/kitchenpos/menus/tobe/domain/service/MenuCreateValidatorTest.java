package kitchenpos.menus.tobe.domain.service;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;
import kitchenpos.menus.tobe.domain.repository.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.InMemoryProductRepository;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.menus.tobe.domain.fixture.MenuFixture.MENU_WITH_MENU_GROUP_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuCreateValidatorTest {

    private MenuGroupRepository menuGroupRepository;

    private ProductRepository productRepository;

    private MenuCreateValidator menuCreateValidator;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        menuCreateValidator = new MenuCreateValidator(menuGroupRepository, productRepository);
    }

    @DisplayName("메뉴 생성 시, 속하는 메뉴 그룹이 없으면 NoSuchElementException을 던진다.")
    @Test
    void createMenuWithoutMenuGroupId() {
        final ThrowableAssert.ThrowingCallable when = () -> MENU_WITH_MENU_GROUP_ID(null, menuCreateValidator);

        assertThatThrownBy(when).isInstanceOf(NoSuchElementException.class)
                .hasMessage("메뉴는 메뉴 그룹에 속해야 합니다");
    }

    @DisplayName("메뉴 생성 시, 메뉴 상품 목록에 1 개 이상의 메뉴 상품이 없으면 IllegalArgumentException을 던진다.")
    @Test
    void createMenuWithoutMenuProduct() {
        MenuGroup menuGroup = menuGroupRepository.save(new MenuGroup(UUID.randomUUID(), new DisplayedName("추천메뉴")));

        final ThrowableAssert.ThrowingCallable when = () -> new Menu(
                UUID.randomUUID(),
                new DisplayedName("후라이드치킨"),
                new Price(BigDecimal.valueOf(16_000L)),
                new MenuProducts(Collections.emptyList()),
                menuGroup.getId(),
                true,
                menuCreateValidator
        );

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1 개 이상의 메뉴 상품을 가져야 합니다");
    }

    @DisplayName("메뉴 생성 시, 메뉴 상품에 대응하는 상품이 없으면 IllegalArgumentException을 던진다.")
    @Test
    void createMenuWithMenuProductWithoutProduct() {
        final MenuGroup menuGroup = menuGroupRepository.save(new MenuGroup(UUID.randomUUID(), new DisplayedName("추천메뉴")));
        final MenuProduct menuProduct = new MenuProduct(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                new Quantity(1L)
        );

        final ThrowableAssert.ThrowingCallable when = () -> new Menu(
                UUID.randomUUID(),
                new DisplayedName("후라이드치킨"),
                new Price(BigDecimal.valueOf(16_000L)),
                new MenuProducts(Collections.singletonList(menuProduct)),
                menuGroup.getId(),
                true,
                menuCreateValidator
        );

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 상품은 대응하는 상품이 있어야 합니다");
    }

    @DisplayName("메뉴 생성 시, 숨겨진 메뉴가 아니라면, 메뉴의 가격이 메뉴 상품들의 총 금액 보다 크면 IllegalArgumentException을 던진다.")
    @Test
    void createMenuWithIllegalPrice() {
        final MenuGroup menuGroup = menuGroupRepository.save(new MenuGroup(UUID.randomUUID(), new DisplayedName("추천메뉴")));
        final Price productPrice = new Price(BigDecimal.valueOf(16_000L));
        final Product product = productRepository.save(new Product(UUID.randomUUID(), new DisplayedName("후라이드"), productPrice));
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), product.getId(), productPrice, new Quantity(1L));

        final ThrowableAssert.ThrowingCallable when = () -> new Menu(
                UUID.randomUUID(),
                new DisplayedName("후라이드치킨"),
                new Price(BigDecimal.valueOf(20_000L)),
                new MenuProducts(Collections.singletonList(menuProduct)),
                menuGroup.getId(),
                true,
                menuCreateValidator
        );

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 메뉴 상품들의 총 금액 보다 적거나 같아야 합니다");
    }
}
