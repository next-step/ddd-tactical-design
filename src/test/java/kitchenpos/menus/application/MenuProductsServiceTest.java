package kitchenpos.menus.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.fake.InMemoryProductRepository;
import kitchenpos.fixture.MenuProductFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.menus.domain.tobe.menu.MenuPrice;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.ui.dto.MenuProductCreateRequests;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.tobe.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("MenuProductsService")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuProductsServiceTest {

    private ProductRepository productRepository = new InMemoryProductRepository();

    private MenuProductsService menuProductsService;

    @BeforeEach
    void setUp() {
        menuProductsService = new MenuProductsService(productRepository);
    }

    @Test
    void 메뉴상품들을_생성한다() {
        MenuProductCreateRequests createRequests = MenuProductFixture.createRequests(
                createFriedProduct(), 2);

        MenuProducts actual = menuProductsService.create(createRequests,
                new MenuPrice(BigDecimal.valueOf(30_000L)));

        assertThat(actual.getMenuProducts()).isNotEmpty();
    }

    @Test
    void 메뉴상품들_생성시_메뉴상품들이_null일_경우_예외를_던진다() {
        assertThatThrownBy(() -> menuProductsService.create(new MenuProductCreateRequests(null),
                new MenuPrice(BigDecimal.valueOf(30_000L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들_생성시_메뉴상품들이_비어있을_경우_예외를_던진다() {
        assertThatThrownBy(() -> menuProductsService.create(new MenuProductCreateRequests(List.of()),
                new MenuPrice(BigDecimal.valueOf(30_000L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들_생성시_메뉴가격이_메뉴상품들의_가격합보다_클_경우_예외를_던진다() {
        MenuProductCreateRequests createRequests = MenuProductFixture.createRequests(
                createFriedProduct(), 2);

        assertThatThrownBy(() -> menuProductsService.create(createRequests,
                new MenuPrice(BigDecimal.valueOf(50_000L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Product createFriedProduct() {
        return productRepository.save(ProductFixture.createFired());
    }
}