package kitchenpos.menu.domain.service;

import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static kitchenpos.TestFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.menu.domain.model.MenuSummary;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuQueryRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.domain.repository.MenuSummaryRepository;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import kitchenpos.product.domain.repository.ProductSummaryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MenuQueryModelSynchronizerTest {

    @Autowired
    private MenuSummaryRepository menuSummaryRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuQueryRepository menuQueryRepository;

    @Autowired
    private ProductSummaryRepository productSummaryRepository;

    @Test
    @DisplayName("이벤트를 받으면 메뉴 쿼리 모델을 업데이트 한다.")
    void update_model() throws InterruptedException {
        // given
        MenuQueryModelSynchronizer menuQueryModelSynchronizer = new MenuQueryModelSynchronizer(menuSummaryRepository,
                productSummaryRepository);

        // when
        Menu menu = createAndSaveMenu(true);

        Thread.sleep(100);

        // then
        List<MenuSummary> result = menuQueryRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(menu.getId());
        assertThat(result.getFirst().getMenuName()).isEqualTo("real 김치찌개");
    }

    private MenuGroup createAndSaveMenuGroup() {
        return menuGroupRepository.save(createMenuGroup());
    }

    private Product createAndSaveProduct() {
        return productRepository.save(createProduct("김치", 5000));
    }

    private Menu createAndSaveMenu(boolean displayed) {
        MenuGroup menuGroup = createAndSaveMenuGroup();
        Product product = createAndSaveProduct();
        MenuProduct menuProduct = new MenuProduct(1, product, product.getId());
        Menu menu = new Menu("real 김치찌개", BigDecimal.valueOf(8000), displayed, List.of(menuProduct), menuGroup,
                menuGroup.getId());
        return menuRepository.save(menu);
    }
}
