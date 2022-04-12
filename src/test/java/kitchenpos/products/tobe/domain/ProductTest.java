package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.port.out.PurgomalumClient;

class ProductTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        //given
        //when
        //then
        assertThatCode(() -> createProduct("치킨", BigDecimal.valueOf(19_000))).doesNotThrowAnyException();
    }

    @DisplayName("상품 이름이 비어 있으면 상품을 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void can_not_be_null_productName(final String productName) {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProduct(productName, BigDecimal.valueOf(19_000)));
    }

    @DisplayName("상품 이름에 비속어가 있으면 상품을 등록할 수 없다.")
    @Test
    void can_not_be_contain_purgomalum_productName() {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProduct("비속어", BigDecimal.valueOf(19_000)));
    }

    @DisplayName("상품 가격이 비어 있으면 상품을 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    void can_not_be_null_productPrice(final BigDecimal price) {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProduct("치킨", price));
    }

    @DisplayName("상품 가격이 0원 보다 작은면 상품을 등록할 수 없다.")
    @Test
    void can_not_be_less_than_zero_productPrice() {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProduct("치킨", BigDecimal.valueOf(-1)));
    }

    private Product createProduct(final String name, final BigDecimal price) {
        return new Product(name, price, purgomalumClient);
    }

}
