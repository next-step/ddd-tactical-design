package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.Profanities;
import kitchenpos.products.tobe.exception.WrongDisplayedNameException;
import kitchenpos.products.tobe.exception.WrongPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakePurgomalumClient();
    }

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void create() {
        //given
        String name = "후라이드";
        BigDecimal price = BigDecimal.valueOf(16_000L);

        //when
        Product product = new Product(profanities, name, price);

        //then
        assertAll(
                () -> assertThat(product).isNotNull(),
                () -> assertThat(product.getDisplayedName().getName()).isEqualTo(name),
                () -> assertThat(product.getPrice().getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("상품의 이름이 올바르지 않으면 생성할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create_fail_empty_or_purgomalum_name(String name) {
        //given
        BigDecimal price = BigDecimal.valueOf(16_000L);

        //when & then
        assertThatExceptionOfType(WrongDisplayedNameException.class)
                .isThrownBy(() -> new Product(profanities, name, price));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 생성할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create_fail_empty_or_negative_price(BigDecimal price) {
        //given
        String name = "후라이드";

        //when & then
        assertThatExceptionOfType(WrongPriceException.class)
                .isThrownBy(() -> new Product(profanities, name, price));
    }
}
