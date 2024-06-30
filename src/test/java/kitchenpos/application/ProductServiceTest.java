package kitchenpos.application;

import fixtures.MenuBuilder;
import fixtures.MenuGroupBuilder;
import fixtures.MenuProductBuilder;
import fixtures.ProductBuilder;

import kitchenpos.menus.domain.*;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceTest {


    @Autowired
    private ProductService productService;
    @Autowired
    private MenuGroupRepository menuGroupRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Nested
    @DisplayName("상품이 생성될 때")
    class ProductCreateTest {

        @DisplayName("상품은 이름, 가격을 갖는다")
        @Test
        void createProductTest() {

            // given
            Product product = new ProductBuilder().anProduct().build();

            // when
            Product savedProduct = productService.create(product);

            // then
            assertNotNull(savedProduct.getId());
            assertEquals(product.getName(), savedProduct.getName());
            assertEquals(product.getPrice(), savedProduct.getPrice());
        }

        @DisplayName("이름은 공백을 허용한다")
        @EmptySource
        @ParameterizedTest
        void productNameWithBlankTest(String name) {

            // given
            Product product = new ProductBuilder().with(name, BigDecimal.ONE).build();

            // when
            Product savedProduct = productService.create(product);

            // then
            assertNotNull(savedProduct.getId());
            assertEquals(product.getName(), savedProduct.getName());
            assertEquals(product.getPrice(), savedProduct.getPrice());
        }

        @DisplayName("가격이 음수면 생성이 불가능하다")
        @Test
        void productPriceIsZeroOrPositiveTest() {

            // given
            Product created = new ProductBuilder()
                    .with("후라이드치킨", BigDecimal.valueOf(-1)).build();

            // when
            // then
            assertThatThrownBy(() -> productService.create(created))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }


    @DisplayName("상품의 가격을 변경한다")
    @Test
    void changePriceTest() {

        Product product = new ProductBuilder()
                .with("치킨", BigDecimal.valueOf(10_000))
                .build();

        Product created = productService.create(product);

        // when
        created.setPrice(BigDecimal.valueOf(20_000));

        // then
        Product sut = productService.changePrice(created.getId(), created);
        assertThat(sut.getPrice()).isEqualTo(BigDecimal.valueOf(20_000));
    }

    @DisplayName("변경한 상품 가격보다 메뉴 가격이 크면 메뉴가 숨김처리 된다")
    @Test
    void changeProductPriceTest() {


        Product product = new ProductBuilder()
                .with("치킨", BigDecimal.valueOf(10_000))
                .build();
        Product created = productService.create(product);
        Menu menu = createMenu(created);

        // when
        created.setPrice(BigDecimal.TEN);
        productService.changePrice(created.getId(), created);

        // then
        Menu sut = menuRepository.findById(menu.getId()).get();
        assertThat(sut.isDisplayed()).isFalse();
    }

    private Menu createMenu(Product product) {

        MenuProduct menuProduct = new MenuProductBuilder()
                .withProduct(product)
                .withQuantity(1)
                .build();

        MenuGroup menuGroup = menuGroupRepository.save(new MenuGroupBuilder().withName("한 마리 메뉴").build());
        return menuRepository.save(new MenuBuilder()
                .with("치킨", BigDecimal.valueOf(10_000))
                .withMenuGroup(menuGroup)
                .withMenuProducts(List.of(menuProduct))
                .build());
    }
}
