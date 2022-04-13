package kitchenpos.products.application;

import kitchenpos.menus.application.InMemoryTobeMenuRepository;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.products.domain.tobe.domain.InMemoryTobeProductRepository;
import kitchenpos.products.domain.tobe.domain.ProductPriceChangeService;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.TobeProductRepository;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.products.dto.*;
import kitchenpos.support.infra.FakePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeProductServiceTest {
    private TobeProductRepository productRepository;
    private TobeMenuRepository menuRepository;
    private TobeProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryTobeProductRepository();
        menuRepository = new InMemoryTobeMenuRepository();
        productService = new TobeProductService(productRepository, menuRepository, new FakePurgomalumClient(), new ProductPriceChangeService(productRepository, menuRepository));
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create_success() {
        //given
        final ProductRegisterRequest 상품등록요청 = new ProductRegisterRequest("후라이드", BigDecimal.valueOf(16_000L));
        //when
        final ProductRegisterResponse 상품 = productService.create(상품등록요청);

        //then
        assertThat(상품).isNotNull();
        assertAll(
                () -> assertThat(상품).isNotNull(),
                () -> assertThat(상품.getProductName()).isEqualTo(상품등록요청.getName()),
                () -> assertThat(상품.getPrice()).isEqualTo(상품등록요청.getPrice())
        );
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ValueSource(strings = {"욕설", "비속어"})
    @ParameterizedTest
    void create_fail_naming_rule_violation(final String name) {
        //given
        final ProductRegisterRequest 상품등록요청 = new ProductRegisterRequest(name, BigDecimal.valueOf(16_000L));
        //when&&then
        assertThatThrownBy(() -> productService.create(상품등록요청))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"-1"})
    @NullSource
    @ParameterizedTest
    void create_fail_pricing_rule_violation(final BigDecimal price) {
        //given
        final ProductRegisterRequest 상품등록요청 = new ProductRegisterRequest("후라이드", price);
        //when&&then
        assertThatThrownBy(() -> productService.create(상품등록요청))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice_success() {
        //given
        final TobeProduct 상품 = productRepository.save(tobeProduct("후라이드", BigDecimal.valueOf(16_000L)));
        final ProductPriceChangeRequest 가격변경요청 = new ProductPriceChangeRequest(상품.getId(), BigDecimal.valueOf(15_000L));

        //when
        final ProductPriceChangeResponse 가격변경상품 = productService.changePrice(가격변경요청);

        //then
        assertThat(가격변경상품).isNotNull();
        assertAll(
                () -> assertThat(가격변경상품.getPrice()).isEqualTo(가격변경요청.getPrice()),
                () -> assertThat(가격변경상품.getProductId()).isEqualTo(상품.getId()),
                () -> assertThat(가격변경상품.getProductName()).isEqualTo(상품.getName().getValue())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = {"-1"})
    @NullSource
    @ParameterizedTest
    void changePrice_fail(final BigDecimal price) {
        //given
        final TobeProduct 상품 = productRepository.save(tobeProduct("후라이드", BigDecimal.valueOf(16_000L)));
        final ProductPriceChangeRequest 가격변경요청 = new ProductPriceChangeRequest(상품.getId(), price);

        //when&&then
        assertThatThrownBy(() -> productService.changePrice(가격변경요청))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        //given
        final TobeProduct product = productRepository.save(tobeProduct("맛나치킨", 16_000));
        final TobeMenu menu = menuRepository.save(menu(14_000L, true, new ArrayList<TobeMenuProduct>(Arrays.asList(tobeMenuProduct(product, 1)))));
        final ProductId productId = menu.getMenuProducts().get(0).getProductId();
        final ProductPriceChangeRequest 가격변경요청 = new ProductPriceChangeRequest(productId, BigDecimal.valueOf(13_000L));

        //when
        ProductPriceChangeResponse 변경된상품 = productService.changePrice(가격변경요청);

        //then
        assertThat(변경된상품).isNotNull();
        assertAll(
                () -> assertThat(변경된상품.getPrice()).isEqualTo(가격변경요청.getPrice()),
                () -> assertThat(변경된상품.getProductId()).isEqualTo(productId),
                () -> assertThat(menu.getDisplayed().isDisplayed()).isFalse()
        );


    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        productRepository.save(tobeProduct("후라이드", BigDecimal.valueOf(16_000L)));
        productRepository.save(tobeProduct("양념치킨", BigDecimal.valueOf(16_000L)));

        //when
        final List<ProductDto> 상품목록 = productService.findAll();

        //then
        assertThat(상품목록).hasSize(2);
    }
}
