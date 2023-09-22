package kitchenpos.products.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.service.MenuFixture;
import kitchenpos.menus.service.MenuGroupFixture;
import kitchenpos.menus.service.MenuProductFixture;
import kitchenpos.products.application.dto.ChangePriceRequest;
import kitchenpos.products.application.dto.CreateProductRequest;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    private Product 강정치킨;
    private Menu 오늘의치킨;

    @BeforeEach
    void init() {
        강정치킨 = ProductFixture.Data.강정치킨();
        productRepository.save(강정치킨);

        MenuGroup 추천메뉴 = MenuGroupFixture.builder().build();
        menuGroupRepository.save(추천메뉴);

        오늘의치킨 = MenuFixture.builder()
                .name("오늘의 치킨")
                .price(1000L)
                .menuGroup(추천메뉴)
                .menuProduct(
                        MenuProductFixture.builder(강정치킨).build()
                )
                .displayed(true)
                .build();
        menuRepository.save(오늘의치킨);
    }

    @Test
    void 상품_생성_실패__가격이_null() {
        CreateProductRequest product = ProductRequestFixture.builder()
                .price(null)
                .buildCreateRequest();

        assertThatThrownBy(() -> productService.create(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_생성_실패__가격이_음수() {
        CreateProductRequest product = ProductRequestFixture.builder()
                .price(-1L)
                .buildCreateRequest();

        assertThatThrownBy(() -> productService.create(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_생성_실패__이름이_null() {
        CreateProductRequest product = ProductRequestFixture.builder()
                .name(null)
                .buildCreateRequest();

        assertThatThrownBy(() -> productService.create(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_생성_실패__이름에_욕설_포함() {
        CreateProductRequest product = ProductRequestFixture.builder()
                .name("fuck")
                .buildCreateRequest();

        assertThatThrownBy(() -> productService.create(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_가격_변경_실패__가격이_null() {
        UUID productId = 강정치킨.getId();
        ChangePriceRequest request = ProductRequestFixture.builder()
                .price(null)
                .buildChangePriceRequest();

        assertThatThrownBy(() -> productService.changePrice(productId, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_가격_변경_실패__가격이_음수() {
        UUID productId = 강정치킨.getId();
        ChangePriceRequest request = ProductRequestFixture.builder()
                .price(-1L)
                .buildChangePriceRequest();

        assertThatThrownBy(() -> productService.changePrice(productId, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_가격_변경_실패__상품이_존재하지_않음() {
        UUID productId = UUID.randomUUID();
        ChangePriceRequest request = ProductRequestFixture.builder()
                .price(0L)
                .buildChangePriceRequest();

        assertThatThrownBy(() -> productService.changePrice(productId, request))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 상품_가격_변경_성공__가격변경으로_인해_기존_메뉴의_가격이_메뉴상품의_가격총합보다_커져서_메뉴가_숨겨짐() {
        UUID productId = 강정치킨.getId();
        ChangePriceRequest request = ProductRequestFixture.builder()
                .price(999L)
                .buildChangePriceRequest();

        assertDoesNotThrow(() -> productService.changePrice(productId, request));
        Menu menuOfProduct = menuRepository.findById(오늘의치킨.getId()).get();
        assertThat(menuOfProduct.isDisplayed()).isFalse();
    }

    @Test
    void 상품_가격_변경_성공() {
        UUID productId = 강정치킨.getId();
        ChangePriceRequest request = ProductRequestFixture.builder()
                .price(1001L)
                .buildChangePriceRequest();

        assertDoesNotThrow(() -> productService.changePrice(productId, request));
        Menu menuOfProduct = menuRepository.findById(오늘의치킨.getId()).get();
        assertThat(menuOfProduct.isDisplayed()).isTrue();
    }
}
