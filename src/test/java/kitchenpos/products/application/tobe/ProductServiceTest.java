package kitchenpos.products.application.tobe;

import kitchenpos.menus.application.tobe.InMemoryMenuRepository;
import kitchenpos.menus.application.tobe.MenuService;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuGroup;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.menus.domain.tobe.MenuRepository;
import kitchenpos.products.domain.tobe.FakeProfanities;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {
    ProductService productService;
    Product product;
    Menu menu;
    MenuRepository menuRepository;


    @BeforeEach
    void setUp() {
        product = Product.createProduct("후라이드", BigDecimal.valueOf(16000), new FakeProfanities());

        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), "세트메뉴");
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), 2)));

        menu = Menu.createMenu("후라이드+후라이드", menuGroup, BigDecimal.valueOf(19000), menuProducts, new FakeProfanities());

        menuRepository = new InMemoryMenuRepository();
        menuRepository.save(menu);

        ProductRepository productRepository = new InMemoryProductRepository();
        productRepository.save(product);

        productService = new ProductService(productRepository, new MenuService(menuRepository));
    }

    @Test
    void changePrice() throws Exception {
        BigDecimal changePrice = BigDecimal.valueOf(10000);
        Product changePricProduct = productService.changePrice(product.getId(), changePrice);

        assertThat(changePricProduct.getPrice().getPriceValue()).isEqualTo(changePrice);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() throws Exception {
        BigDecimal changePrice = BigDecimal.valueOf(5000);

        Product changePricProduct = productService.changePrice(product.getId(), changePrice);

        List<Menu> menus = menuRepository.findAllByProductId(changePricProduct.getId());
        assertThat(product.getPrice().getPriceValue()).isEqualTo(changePrice);

        for (Menu menu : menus) {
            assertThat(menu.isDisplayed()).isFalse();
        }
    }
}
