package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.infra.DefaultProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductTest {

    private ProfanityValidator profanityValidator;

    @BeforeEach
    void setUp() {
        final var purgomalumClient = new FakePurgomalumClient();
        profanityValidator = new DefaultProfanityValidator(purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final var displayedName = displayedName("후라이드");
        final var productPrice = productPrice(16_000L);

        final Product actual = product(displayedName, productPrice);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getDisplayedName()).isEqualTo(displayedName),
                () -> assertThat(actual.getProductPrice()).isEqualTo(productPrice)
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final var displayedName = displayedName("후라이드");
        final var productPrice = productPrice(16_000L);
        final var product = product(displayedName, productPrice);

        final var expected = productPrice(15_000L);

        product.changePrice(expected);
        final var actual = product.getProductPrice();

        assertThat(actual).isEqualTo(expected);
    }

    private DisplayedName displayedName(String name) {
        return DisplayedName.of(name, profanityValidator);
    }

    private ProductPrice productPrice(long price) {
        return productPrice(BigDecimal.valueOf(price));
    }

    private ProductPrice productPrice(BigDecimal price) {
        return ProductPrice.of(price);
    }

    private Product product(DisplayedName displayedName, ProductPrice price) {
        return Product.of(displayedName, price);
    }

}
