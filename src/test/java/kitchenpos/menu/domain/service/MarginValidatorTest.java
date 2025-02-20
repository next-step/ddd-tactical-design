package kitchenpos.menu.domain.service;

import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.infra.persistence.FakeMenuRepository;
import kitchenpos.product.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static kitchenpos.TestFixtureFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class MarginValidatorTest {

    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        menuRepository = new FakeMenuRepository(new HashMap<>());
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

    @Test
    @DisplayName("메뉴의 마진을 검증하고, 마진 여부를 알려준다.")
    void check_margin_by_menu() {
        Product product = createProduct(BigDecimal.valueOf(2000));
        Menu soup = createMenu(createMenuGroup(), product, 5);

        HashMap<UUID, Menu> storage = new HashMap<>();
        storage.put(UUID.randomUUID(), soup);
        menuRepository = new FakeMenuRepository(storage);

        // when
        MarginValidator marginValidator = new MarginValidator(menuRepository);
        boolean result = marginValidator.checkMargin(soup);

        // then
        assertThat(soup.isDisplayed()).isEqualTo(false);
        assertThat(result).isEqualTo(false);
    }
}
