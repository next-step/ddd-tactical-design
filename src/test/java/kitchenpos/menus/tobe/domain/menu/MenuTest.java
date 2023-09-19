package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuTest {

    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        product = productRepository.save(product());
    }

    @DisplayName("Menu를 생성할 수 있다.")
    @Test
    void create() {
        Menu menu = menu(30000L, true, productRepository, menuProductMaterial(product.getId()));
        assertAll(
                () -> assertThat(menu.getId()).isNotNull(),
                () -> assertThat(menu.getDisplayedName()).isEqualTo(MenuDisplayedName.from("후라이드+후라이드", menuDisplayedNamePolicy())),
                () -> assertThat(menu.getPrice()).isEqualTo(MenuPrice.from(BigDecimal.valueOf(30000L))),
                () -> assertThat(menu.isDisplayed()).isTrue(),
                () -> assertThat(menu.getMenuGroup().getName()).isEqualTo(menuGroup().getName()),
                () -> assertThat(menu.getMenuProducts().getMenuProducts()).extracting("productId").containsExactly(product.getId())
        );
    }

    @DisplayName("Menu를 생성할 때 Menu의 가격이 Menu Product의 총 가격보다 크다면 예외를 던진다.")
    @Test
    void createThrowException() {
        Product product = product("후라이드", 15000L);
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId(), 2L);
        assertThatThrownBy(() -> menu(30001L, true, productRepository, menuProductMaterial))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("Menu Product의 가격을 변경할 수 있다.")
    @Test
    void changeMenuProductPrice() {
        Product product = product("후라이드", 16000L);
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId(), 2L);
        Menu menu = menu(30000L, productRepository, menuProductMaterial);
        menu.changeMenuProductPrice(product.getId(), BigDecimal.valueOf(15000L));
        assertThat(menu.getMenuProducts().getMenuProducts().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(15000L));
    }

    @DisplayName("Menu Product의 가격을 변경한 후 Menu의 가격이 Menu Product의 총 가격보다 커지면 Menu를 숨겨진 메뉴로 변경한다.")
    @Test
    void changeMenuProductPriceWhenMenuPriceIsLessThanMenuProductsTotalAmount() {
        Product product = product("후라이드", 16000L);
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId(), 2L);
        Menu menu = menu(30001L, true, productRepository, menuProductMaterial);
        menu.changeMenuProductPrice(product.getId(), BigDecimal.valueOf(15000L));
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("Menu의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        Product product = product();
        productRepository.save(product);
        Menu menu = menu(30000L, true, productRepository, menuProductMaterial(product.getId()));
        menu.changePrice(MenuPrice.from(BigDecimal.valueOf(20000L)));
        assertThat(menu.getPrice()).isEqualTo(MenuPrice.from(BigDecimal.valueOf(20000L)));
    }

    @DisplayName("Menu의 가격을 변경할 때 Menu의 가격이 Menu Product의 총 가격보다 커진다면 예외를 던진다.")
    @Test
    void changePriceThrowException() {
        Product product = product("후라이드", 16000L);
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId(), 2L);
        Menu menu = menu(30001L, true, productRepository, menuProductMaterial);
        assertThatThrownBy(() -> menu.changePrice(MenuPrice.from(BigDecimal.valueOf(32001L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Menu를 display 상태로 변경할 수 있다.")
    @Test
    void display() {
        Product product = product("후라이드", 16000L);
        productRepository.save(product);
        Menu menu = menu(30000L, false, productRepository, menuProductMaterial(product.getId()));
        menu.display();
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("Menu를 display 상태로 변경할 때 Menu의 가격이 Menu Product의 총 가격보다 크다면 예외를 던진다.")
    @Test
    void displayThrowException() {
        Product product = product("후라이드", 16000L);
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId(), 2L);
        Menu menu = menu(30001L, false, productRepository, menuProductMaterial);
        menu.changeMenuProductPrice(product.getId(), BigDecimal.valueOf(15000L));
        assertThatThrownBy(menu::display)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("Menu를 숨김 상태로 변경할 수 있다.")
    @Test
    void hide() {
        Product product = product("후라이드", 16000L);
        productRepository.save(product);
        Menu menu = menu(30000L, true, productRepository, menuProductMaterial(product.getId()));
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("Menu의 가격을 Menu Product의 총 가격과 Menu Product의 총 수량을 곱한 값으로 계산할 수 있다.")
    @Test
    void priceMultiplyByQuantity() {
        Product product = product("후라이드", 16000L);
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId(), 2L);
        Menu menu = menu(30000L, true, productRepository, menuProductMaterial);
        assertThat(menu.priceMultiplyByQuantity(2L)).isEqualTo(BigDecimal.valueOf(60000L));
    }
}
