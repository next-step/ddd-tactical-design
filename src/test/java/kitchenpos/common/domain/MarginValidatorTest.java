package kitchenpos.common.domain;

import static kitchenpos.test.TestFixtureFactory.createMenu;
import static kitchenpos.test.TestFixtureFactory.createMenuGroup;
import static kitchenpos.test.TestFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import kitchenpos.test.FakeMenuRepository;
import kitchenpos.test.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarginValidatorTest {

    private MarginValidator marginValidator;
    private MenuRepository menuRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        menuRepository = new FakeMenuRepository(new HashMap<>());
        productRepository = new FakeProductRepository(new HashMap<>());
        marginValidator = new MarginValidator(menuRepository);
    }

    @Test
    @DisplayName("상품이 속한 메뉴들의 마진을 확인하고, 마진이 남지 않으면 게시를 하지 않는다.")
    void check_margin_by_product() {
        // given
        Product product = createProduct(BigDecimal.valueOf(2000));
        Menu soup = createMenu(createMenuGroup(), product, 5);
        Menu cake = createMenu(createMenuGroup(), product, 4);

        HashMap<UUID, Menu> storage = new HashMap<>();
        storage.put(UUID.randomUUID(), soup);
        storage.put(UUID.randomUUID(), cake);
        menuRepository = new FakeMenuRepository(storage);

        // when
        MarginValidator marginValidator = new MarginValidator(menuRepository);
        marginValidator.checkMargin(product);

        // then
        assertThat(soup.isDisplayed()).isEqualTo(false);
        assertThat(cake.isDisplayed()).isEqualTo(true);
    }
}
