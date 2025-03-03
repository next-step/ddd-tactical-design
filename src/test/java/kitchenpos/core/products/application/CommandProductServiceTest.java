package kitchenpos.core.products.application;

import kitchenpos.config.UnitTest;
import kitchenpos.core.shared.event.ProductPriceChangedEvent;
import kitchenpos.fixture.ProductFixtures;
import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.products.tobe.domain.*;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.core.shared.identifier.ProductId;
import kitchenpos.core.shared.value.Money;
import kitchenpos.core.menus.application.InMemoryMenuRepository;
import kitchenpos.core.menus.domain.Menu;
import kitchenpos.core.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;

import static kitchenpos.fixture.Fixtures.menu;
import static kitchenpos.fixture.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTest
@DisplayName("[Product] CommandProductService 테스트")
class CommandProductServiceTest {
    private TobeProductRepository productRepository;
    private MenuRepository menuRepository;
    private CommandProductService sut;


    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        sut = new CommandProductService(productRepository, menuRepository);
    }

    @DisplayName("성공: 상품을 등록할 수 있다.")
    @Test
    void create() {
        CreateProductRequest expected = ProductFixtures.createProductRequest("후라이드", 16_000L);
        final Product actual = sut.addProduct(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.name()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.price())
        );
    }

    @Disabled("ProductPrice 생성 시에 이미 검증")
    @DisplayName("실패: 상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(longs = -1000)
    @ParameterizedTest
    void create(final Long price) {
        assertThrows(IllegalArgumentException.class, () -> sut.addProduct(ProductFixtures.createProductRequest("후라이드", price)));
    }

    @Disabled("ProductName 생성 시에 이미 검증")
    @DisplayName("실패: 상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> sut.addProduct(ProductFixtures.createProductRequest(name, 16_000L)))
            .isInstanceOf(InvalidProductNameException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final ProductId productId = productRepository.save(ProductFixtures.product("후라이드", 16_000L)).getId();
        final ProductPrice expected = ProductPrice.of(Money.wons(15_000L));
        final Product actual = sut.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected);
    }

    @Disabled("ProductPrice 생성 시에 이미 검증")
    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(longs = -1000)
    @ParameterizedTest
    void changePrice(final long price) {
        final ProductId productId = productRepository.save(ProductFixtures.product("후라이드", 16_000L)).getId();
        assertThatThrownBy(() -> sut.changePrice(productId, ProductPrice.of(Money.wons(price))))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Disabled("Product Event 발행으로 변경")
    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(ProductFixtures.product("후라이드", 16_000L));
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        sut.changePrice(product.getId(), ProductPrice.of(Money.wons(8_000L)));
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
    }

    @DisplayName("성공: 상품의 가격이 변경될 때 ProductPriceChangedEvent가 발생한다.")
    @Test
    void changePrice2() {
        final Product product = productRepository.save(ProductFixtures.product("후라이드", 16_000L));
        sut.changePrice(product.getId(), ProductPrice.of(Money.wons(8_000L)));
        // then: domainEvents를 확인
        Collection<Object> events = product.domainEvents();

        assertThat(events).hasSize(1);
        Object event = events.iterator().next();
        assertThat(event).isInstanceOf(ProductPriceChangedEvent.class);
    }
}
