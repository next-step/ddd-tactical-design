package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.ToBeFixtures;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.FakeProfanities;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.application.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuRegistrationValidatorTest {

    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private MenuRegistrationValidator validator;

    private Price menuPrice;
    private MenuGroupId menuGroupId;
    private MenuProducts menuProducts;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        validator = new MenuRegistrationValidator(menuGroupRepository, productRepository);

        menuPrice = new Price(BigDecimal.valueOf(32_000L));
        menuGroupId = menuGroupRepository.save(ToBeFixtures.menuGroup()).getId();
        final Product product = productRepository.save(
            ToBeFixtures.product("후라이드치킨", 16000)
        );
        menuProducts = new MenuProducts(
            Arrays.asList(
                ToBeFixtures.menuProduct(
                    product.getId(),
                    new Price(BigDecimal.valueOf(16_000L)),
                    2
                )
            )
        );
    }

    @DisplayName("메뉴 등록을 검증할 수 있다.")
    @Test
    void 검증() {
        assertDoesNotThrow(
            () -> new Menu(
                new MenuId(UUID.randomUUID()),
                new DisplayedName(
                    "랜덤메뉴",
                    new FakeProfanities()
                ),
                menuPrice,
                menuGroupId,
                new Displayed(true),
                menuProducts,
                validator
            )
        );
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void 메뉴가격검증() {
        final Price invalidMenuPrice = new Price(BigDecimal.valueOf(1_000_000L));

        assertThatThrownBy(
            () -> createMenu(invalidMenuPrice)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가 특정 메뉴 그룹에 속하는지 검증할 수 있다.")
    @Test
    void 메뉴그룹검증() {
        final MenuGroupId invalidMenuGroupId = new MenuGroupId(UUID.randomUUID());

        assertThatThrownBy(
            () -> createMenu(invalidMenuGroupId)
        ).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴에 등록 요청된 상품이 존재하는지 검증할 수 있다.")
    @Test
    void 상품검증() {
        final MenuProducts invalidMenuProducts = ToBeFixtures.menuProducts();

        assertThatThrownBy(
            () -> createMenu(invalidMenuProducts)
        ).isInstanceOf(NoSuchElementException.class);
    }

    private void createMenu(
        final Price price,
        final MenuGroupId menuGroupId,
        final MenuProducts menuProducts
    ) {
        new Menu(
            new MenuId(UUID.randomUUID()),
            new DisplayedName(
                "랜덤메뉴",
                new FakeProfanities()
            ),
            price,
            menuGroupId,
            new Displayed(true),
            menuProducts,
            validator
        );
    }

    private void createMenu(final Price menuPrice) {
        createMenu(menuPrice, menuGroupId, menuProducts);
    }

    private void createMenu(final MenuGroupId menuGroupId) {
        createMenu(menuPrice, menuGroupId, menuProducts);
    }

    private void createMenu(final MenuProducts menuProducts) {
        createMenu(menuPrice, menuGroupId, menuProducts);
    }
}
