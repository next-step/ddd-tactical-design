package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityFilteredNameFactory;
import kitchenpos.common.domain.Quantity;
import kitchenpos.menus.tobe.dto.MenuCreationRequest;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static kitchenpos.Fixtures.tobe_product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MenuFactoryTest {
    private ProfanityFilteredNameFactory profanityFilteredNameFactory;
    private MenuRepository menuRepository;
    private ProductRepository productRepository;
    private MenuGroupRepository menuGroupRepository;
    private MenuFactory menuFactory;
    private Product product;
    private UUID menuGroupId;

    @BeforeEach
    void setUp() {
        profanityFilteredNameFactory = new ProfanityFilteredNameFactory(new FakePurgomalumClient());
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        menuFactory = new MenuFactory(productRepository, menuGroupRepository);
        product =  productRepository.save(tobe_product("후라이드", 19_000L));
        menuGroupId = menuGroupRepository.save(MenuFactory.createMenuGroup("아무메뉴")).getId();
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, true, Collections.singletonMap(product.getId(), Quantity.of(1)));

        //when
        final Menu actual = MenuFactory.createMenu(menuCreationRequest);

        //then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("후라이드+후라이드"),
                () -> assertThat(actual.getPrice()).isEqualTo(Price.of(BigDecimal.valueOf(19_000L))),
                () -> assertThat(actual.getMenuGroup().getName()).isEqualTo("아무메뉴"),
                () -> assertThat(actual.isDisplayed()).isEqualTo(true),
                () -> assertThat(actual.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @Test
    void create_invalid_when_product_not_present() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, true, Collections.singletonMap(null, Quantity.of(1)));

        //when
        //then
        assertThatThrownBy(
                () -> MenuFactory.createMenu(menuCreationRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void create_invalid_when_expensive_menu() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(42_000L), menuGroupId, true, Collections.singletonMap(product.getId(), Quantity.of(2)));

        //when
        //then
        assertThatThrownBy(
                () -> MenuFactory.createMenu(menuCreationRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create_invalid_when_contains_profanity(final String name) {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest(name, BigDecimal.valueOf(19_000L), menuGroupId, true, Collections.singletonMap(product.getId(), Quantity.of(1)));

        //when
        //then
        assertThatThrownBy(
                () -> MenuFactory.createMenu(menuCreationRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
