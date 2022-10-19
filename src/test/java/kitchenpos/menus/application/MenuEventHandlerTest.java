package kitchenpos.menus.application;

import static kitchenpos.menus.MenuFixtures.menuGroup;
import static kitchenpos.products.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.*;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.ui.request.ProductChangePriceRequest;
import kitchenpos.profanity.infra.FakeProfanityCheckClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MenuEventHandlerTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @DisplayName("상품 가격이 변경되었다는 이벤트가 발행되었을 때, 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        // given
        Product product = productRepository.save(product("햄버거", 5_000));
        MenuGroup menuGroup = menuGroupRepository.save(menuGroup("버거그룹"));
        Menu menu = menuRepository.save(createMenu(product, menuGroup));

        // when
        productService.changePrice(
            product.getId(),
            new ProductChangePriceRequest(BigDecimal.valueOf(4_500))
        );

        // then
        Menu found = findMenuById(menu.getId());
        assertThat(found.isDisplayed()).isFalse();
    }

    private static Menu createMenu(Product product, MenuGroup menuGroup) {
        return new Menu(
            new MenuName("햄버거 메뉴", new FakeProfanityCheckClient()),
            new MenuPrice(BigDecimal.valueOf(5_000)),
            menuGroup,
            new MenuProducts(List.of(new MenuProduct(
                product.getId(),
                new MenuProductPrice(BigDecimal.valueOf(5_000)),
                new MenuProductQuantity(1)
            )))
        );
    }

    private Menu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 메뉴를 찾을 수 없습니다."));
    }
}
