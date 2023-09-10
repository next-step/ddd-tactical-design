package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private PurgomalumClient purgomalumClient;
    private DisplayedNamePolicy displayedNamePolicy;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
        displayedNamePolicy = new DisplayedNamePolicy(purgomalumClient);
    }

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void create() {
        final Product product = new Product(
                UUID.randomUUID(),
                ProductDisplayedName.from("후라이드", displayedNamePolicy),
                ProductPrice.from(BigDecimal.valueOf(16_000L)));
        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getDisplayedName()).isEqualTo(ProductDisplayedName.from("후라이드", displayedNamePolicy)),
                () -> assertThat(product.getPrice()).isEqualTo(ProductPrice.from(BigDecimal.valueOf(16_000L)))
        );
    }

    @DisplayName("상품에 임의의 ID를 부여할 수 있다.")
    @Test
    void giveId() {
        final Product product = new Product(
                ProductDisplayedName.from("후라이드", displayedNamePolicy),
                ProductPrice.from(BigDecimal.valueOf(16_000L)));
        final Product result = product.giveId();
        assertThat(result.getId()).isNotNull();
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final Product product = new Product(
                UUID.randomUUID(),
                ProductDisplayedName.from("후라이드", displayedNamePolicy),
                ProductPrice.from(BigDecimal.valueOf(16_000L)));
        final ProductPrice newPrice = ProductPrice.from(BigDecimal.valueOf(17_000L));
        product.changePrice(newPrice);
        assertThat(newPrice).isEqualTo(product.getPrice());
    }
}
