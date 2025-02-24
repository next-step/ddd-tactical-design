package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.tobe.Profanities;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import kitchenpos.product.tobe.fake.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

    private ProductRepository productRepository;
    private MenuValidator menuValidator;
    private final Profanities profanities = text -> text.equals("바보"); // 테스트용 비속어 설정

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuValidator = new MenuPriceValidator(productRepository);
        // 상품 생성 및 저장
        Product product1 = new Product( "상품1", 5000L, profanities);
        Product product2 = new Product("상품2", 8000L, profanities);
        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    @DisplayName("메뉴를 생성할 수 있다")
    void createMenu() {
        // given
        String menuName = "맛있는 메뉴";
        Long menuPrice = 10000L;
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, UUID.randomUUID()),
                new MenuProduct(1L, 1, 5000L, UUID.randomUUID()));

        boolean displayed = true;

        // when
        Menu menu = Menu.of(menuName, menuPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator);

        // then
        assertThat(menu).isNotNull();
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getMenuPrice()).isEqualTo(menuPrice);
        assertThat(menu.isDisplayed()).isEqualTo(displayed);
        assertThat(menu.getMenuProducts()).hasSize(2);
    }

}