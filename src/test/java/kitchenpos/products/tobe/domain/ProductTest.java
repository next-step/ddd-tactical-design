package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.infra.DefaultProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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
