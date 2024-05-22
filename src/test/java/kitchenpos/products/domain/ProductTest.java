package kitchenpos.products.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.ProductNameValidationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

@DisplayName("상품 도메인 테스트")
public class ProductTest {
    private ProductNameValidationService productNameValidationService;

    @BeforeEach
    void setUp() {
        FakePurgomalumClient fakePurgomalumClient = new FakePurgomalumClient();
        productNameValidationService = new ProductNameValidationService(fakePurgomalumClient);
    }

    @Test
    @DisplayName("후라이드 상품을 생성한다.")
    void create() {
        Product product = new Product(
                UUID.randomUUID(), "후라이드", BigDecimal.valueOf(20000), productNameValidationService
        );

        Assertions.assertThat(product.getId()).isNotNull();
    }

    @Test
    @DisplayName("상품에는 비속어가 포함될 수 없다.")
    void create_exception_profanity() {
        Assertions.assertThatThrownBy(
                () -> new Product(UUID.randomUUID(), "비속어", BigDecimal.valueOf(20000), productNameValidationService)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @NullSource
    @ValueSource(strings = "-1000")
    @ParameterizedTest
    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    void create_exception_price(BigDecimal price) {
        Assertions.assertThatThrownBy(
                () -> new Product(UUID.randomUUID(), "후라이드", price, productNameValidationService)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 가격을 변경한다.")
    void changePrice() {
        Price 기존_금액 = new Price(BigDecimal.valueOf(20000));
        BigDecimal 변경_금액 = BigDecimal.valueOf(30000);
        Product product = new Product(
                UUID.randomUUID(), "후라이드", 기존_금액, productNameValidationService
        );

        product.changePrice(변경_금액);

        Assertions.assertThat(product.isSamePrice(변경_금액)).isEqualTo(true);
    }

    @Test
    @DisplayName("상품의 가격이 0원 미만이면 가격을 변경할 수 없다")
    void changePrice_exception() {
        Price 기존_금액 = new Price(BigDecimal.valueOf(20000));
        BigDecimal 변경_금액 = BigDecimal.valueOf(-1);
        Product product = new Product(
                UUID.randomUUID(), "후라이드", 기존_금액, productNameValidationService
        );

        Assertions.assertThatThrownBy(
                () -> product.changePrice(변경_금액)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
