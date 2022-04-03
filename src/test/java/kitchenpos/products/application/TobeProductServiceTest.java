package kitchenpos.products.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.tobe.domain.InMemoryTobeProductRepository;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.TobeProductRepository;
import kitchenpos.products.domain.tobe.policy.FakeSuccessProductNamingRule;
import kitchenpos.products.domain.tobe.policy.FakeSuccessProductPricingRule;
import kitchenpos.products.dto.ProductRegisterRequest;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeProductServiceTest {
    private TobeProductRepository productRepository;
    private MenuRepository menuRepository;
    private TobeProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryTobeProductRepository();
        menuRepository = new InMemoryMenuRepository();
        productService = new TobeProductService(productRepository, menuRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        //given
        final ProductRegisterRequest 상품등록요청 =
                new ProductRegisterRequest("후라이드", new FakeSuccessProductNamingRule(), BigDecimal.valueOf(16_000L), new FakeSuccessProductPricingRule());
        //when
        final TobeProduct 상품 = productService.create(상품등록요청);

        //then
        assertThat(상품).isNotNull();
        assertAll(
            () -> assertThat(상품.getId()).isNotNull(),
            () -> assertThat(상품.getName().getValue()).isEqualTo(상품등록요청.getName()),
            () -> assertThat(상품.getPrice().getValue()).isEqualTo(상품등록요청.getPrice())
        );
    }


}
