package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private final ProfanityClient profanityClient = new FakePurgomalumClient();

    @Test
    @DisplayName("이름과 가격을 입력하여 상품을 만들 수 있다.")
    void product() {
        Product product = new Product(new DisplayedName("상품이름", profanityClient), new Price(1000L));
        Price price = new Price(1000L);
        DisplayedName displayedName = new DisplayedName("상품이름", profanityClient);

        assertThat(product.getId()).isNotNull();
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDisplayedName()).isEqualTo(displayedName);
    }

    @Test
    @DisplayName("이름이 비어있으면 상품을 만들 수 없다.")
    void product_empty_name() {
        assertThatThrownBy(() -> new Product(new DisplayedName("", profanityClient), new Price(1000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @DisplayName("상품의 이름이 올바르지 않으면 상품을 만들 수 없다.")
    void name_profanity(String name) {
        assertThatThrownBy(() -> new Product(new DisplayedName(name, profanityClient), new Price(1000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("가격이 0보다 작으면 상품을 만들 수 없다.")
    void product_positive_price() {
        assertThatThrownBy(() -> new Product(new DisplayedName("", profanityClient), new Price(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}