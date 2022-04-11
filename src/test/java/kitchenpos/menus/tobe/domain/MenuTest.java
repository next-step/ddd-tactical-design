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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static kitchenpos.Fixtures.tobe_product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {
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

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, true, Collections.singletonMap(product.getId(), Quantity.of(1)));
        final Menu menu = menuRepository.save(MenuFactory.createMenu(menuCreationRequest));
        final Price price = Price.of(BigDecimal.valueOf(18_000L));

        //when
        Menu changedMenu = menu.changePrice(price);

        //then
        assertThat(changedMenu.getPrice()).isEqualTo(price);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @Test
    void changePrice_when_invalid_price() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, true, Collections.singletonMap(product.getId(), Quantity.of(1)));
        final Menu menu = menuRepository.save(MenuFactory.createMenu(menuCreationRequest));
        final Price price = null;

        //when
        //then
        assertThatThrownBy(
                () -> menu.changePrice(price)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePrice_when_price_bigger_than_products_sum() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, true, Collections.singletonMap(product.getId(), Quantity.of(2)));
        final Menu menu = menuRepository.save(MenuFactory.createMenu(menuCreationRequest));
        final Price price = Price.of(BigDecimal.valueOf(42_000L));

        //when
        //then
        assertThatThrownBy(
                () -> menu.changePrice(price)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, false, Collections.singletonMap(product.getId(), Quantity.of(2)));
        final Menu menu = menuRepository.save(MenuFactory.createMenu(menuCreationRequest));

        //when
        Menu actual = menu.display();

        //then
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void display_when_price_bigger_than_products_sum() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, false, Collections.singletonMap(this.product.getId(), Quantity.of(1)));
        final Menu menu = menuRepository.save(MenuFactory.createMenu(menuCreationRequest));

        Product changedProduct = product.changePrice(Price.of(BigDecimal.valueOf(18_000L)));
        menu.changeProduct(changedProduct);

        //when
        //then
        assertThatThrownBy(
                () -> menu.display()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        //given
        MenuCreationRequest menuCreationRequest = new MenuCreationRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, false, Collections.singletonMap(this.product.getId(), Quantity.of(1)));
        final Menu menu = menuRepository.save(MenuFactory.createMenu(menuCreationRequest));

        //when
        Menu actual = menu.hide();

        //then
        assertThat(actual.isDisplayed()).isFalse();
    }
}
