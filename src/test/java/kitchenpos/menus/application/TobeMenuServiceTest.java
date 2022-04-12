package kitchenpos.menus.application;

import kitchenpos.support.exception.NamingRuleViolationException;
import kitchenpos.support.exception.PricingRuleViolationException;
import kitchenpos.support.policy.FakeFailNamingRule;
import kitchenpos.support.policy.FakeFailPricingRule;
import kitchenpos.support.policy.FakeSuccessNamingRule;
import kitchenpos.support.policy.FakeSuccessPricingRule;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import kitchenpos.menus.dto.MenuDisplayRequest;
import kitchenpos.menus.dto.MenuHideRequest;
import kitchenpos.menus.dto.MenuPriceChangeRequest;
import kitchenpos.menus.dto.MenuRegisterRequest;
import kitchenpos.products.domain.tobe.domain.InMemoryTobeProductRepository;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.TobeProductRepository;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeMenuServiceTest {
    private TobeMenuRepository menuRepository;
    private TobeMenuGroupRepository menuGroupRepository;
    private TobeProductRepository productRepository;
    private TobeMenuService menuService;
    private MenuGroupId menuGroupId;
    private List<TobeMenuProduct> menuProducts;

    private static List<Arguments> menuProducts() {
        TobeProduct product = tobeProduct("굿굿치킨", 16_000L);
        TobeMenuProduct invalid = new TobeMenuProduct.Builder().product(product).quantity(2L).productId(new ProductId(INVALID_ID)).build();
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList())
                //TODO: ID Validation Check
                //Arguments.of(Arrays.asList(invalid))
        );
    }

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryTobeMenuRepository();
        menuGroupRepository = new InMemoryTobeMenuGroupRepository();
        productRepository = new InMemoryTobeProductRepository();
        menuService = new TobeMenuService(menuRepository, menuGroupRepository, productRepository);
        menuGroupId = menuGroupRepository.save(tobeMenuGroup("맛난치킨그룹")).getId();
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        //given
        final MenuRegisterRequest request = new MenuRegisterRequest(
                "맛난치킨", new FakeSuccessNamingRule(),
                BigDecimal.valueOf(15_000L), new FakeSuccessPricingRule(),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 16_000L, 1),
                true
        );

        //when
        final TobeMenu 등록된메뉴 = menuService.create(request);

        //then
        assertThat(request).isNotNull();
        assertAll(
                () -> assertThat(등록된메뉴.getId()).isNotNull(),
                () -> assertThat(등록된메뉴.getName().getValue()).isEqualTo(request.getName()),
                () -> assertThat(등록된메뉴.getPrice().getValue()).isEqualTo(request.getPrice()),
                () -> assertThat(등록된메뉴.getMenuGroup().getId()).isEqualTo(request.getMenuGroupId()),
                () -> assertThat(등록된메뉴.getDisplayed().getValue()).isEqualTo(request.isDisplayed()),
                () -> assertThat(등록된메뉴.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<TobeMenuProduct> menuProducts) {
        final MenuRegisterRequest request = new MenuRegisterRequest(
                "맛난치킨", new FakeSuccessNamingRule(),
                BigDecimal.valueOf(15_000L), new FakeSuccessPricingRule(),
                menuGroupId,
                menuProducts,
                true
        );
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        //given&&when&&then
        assertThatThrownBy(() -> {
            final MenuRegisterRequest request = new MenuRegisterRequest(
                    "맛난치킨", new FakeSuccessNamingRule(),
                    BigDecimal.valueOf(15_000L), new FakeSuccessPricingRule(),
                    menuGroupId,
                    tobeMenuProducts("맛나치킨", 16_000L, -1),
                    true
            );
            menuService.create(request);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("등록하려는 메뉴의 가격이 정책에 위반되는 경우 등록할 수 없다")
    @Test
    void create_fail_pricing_rule_violation() {
        final MenuRegisterRequest request = new MenuRegisterRequest(
                "맛난치킨", new FakeSuccessNamingRule(),
                BigDecimal.valueOf(15_000L), new FakeFailPricingRule(),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 16_000L, 1),
                true
        );
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(PricingRuleViolationException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final MenuGroupId menuGroupId) {
        final MenuRegisterRequest request = new MenuRegisterRequest(
                "맛난치킨", new FakeSuccessNamingRule(),
                BigDecimal.valueOf(15_000L), new FakeSuccessPricingRule(),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 16_000L, 1),
                true
        );
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @Test
    void create_fail_naming_rule_violation() {
        final MenuRegisterRequest request = new MenuRegisterRequest(
                "맛난치킨", new FakeFailNamingRule(),
                BigDecimal.valueOf(15_000L), new FakeSuccessPricingRule(),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 16_000L, 1),
                true
        );
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(NamingRuleViolationException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        //given
        final TobeMenu menu = menuRepository.save(menu(14_000L, new FakeSuccessPricingRule(), true, tobeMenuProducts("맛나치킨", 16_000L, 1)));
        final MenuPriceChangeRequest request = new MenuPriceChangeRequest(menu.getId(), BigDecimal.valueOf(15_000L), new FakeSuccessPricingRule());

        //when
        final TobeMenu 변경된메뉴 = menuService.changePrice(request);

        //then
        assertThat(변경된메뉴.getPrice().getValue()).isEqualTo(request.getPrice());
    }

    @DisplayName("변경하려는 가격이 메뉴 정책에 위반된다면 변경할 수 없다")
    @Test
    void changePrice_fail_pricing_rule_violation() {
        //given
        final TobeMenu menu = menuRepository.save(menu(14_000L, new FakeSuccessPricingRule(), true, tobeMenuProducts("맛나치킨", 16_000L, 1)));
        final MenuPriceChangeRequest 가격변경요청 = new MenuPriceChangeRequest(menu.getId(), BigDecimal.valueOf(15_000L), new FakeFailPricingRule());

        //when&&then
        assertThatThrownBy(() -> menuService.changePrice(가격변경요청))
                .isInstanceOf(PricingRuleViolationException.class);
    }


    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        //given
        final TobeMenu menu = menuRepository.save(menu(14_000L, new FakeSuccessPricingRule(), true, tobeMenuProducts("맛나치킨", 16_000L, 1)));
        final MenuDisplayRequest request = new MenuDisplayRequest(menu.getId(), new FakeSuccessPricingRule());

        //when
        final TobeMenu 노출된상품 = menuService.display(request);

        //then
        assertThat(노출된상품.getDisplayed().getValue()).isTrue();
    }

    @DisplayName("메뉴의 가격이 정책에 위반되는 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        //given
        final TobeMenu menu = menuRepository.save(menu(14_000L, new FakeSuccessPricingRule(), true, tobeMenuProducts("맛나치킨", 16_000L, 1)));
        final MenuDisplayRequest request = new MenuDisplayRequest(menu.getId(), new FakeFailPricingRule());

        //when&&then
        assertThatThrownBy(() -> menuService.display(request))
                .isInstanceOf(PricingRuleViolationException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        //given
        final TobeMenu menu = menuRepository.save(menu(14_000L, new FakeSuccessPricingRule(), true, tobeMenuProducts("맛나치킨", 16_000L, 1)));
        final MenuHideRequest request = new MenuHideRequest(menu.getId());

        //when
        final TobeMenu 숨겨진상품 = menuService.hide(request);

        //then
        assertThat(숨겨진상품.getDisplayed().getValue()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        menuRepository.save(menu(14_000L, new FakeSuccessPricingRule(), true, tobeMenuProducts("맛나치킨", 16_000L, 1)));

        //when&&then
        assertThat(menuService.findAll()).hasSize(1);
    }
}
