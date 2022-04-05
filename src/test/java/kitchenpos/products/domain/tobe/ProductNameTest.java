package kitchenpos.products.domain.tobe;

import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNameTest {

    @DisplayName("Product는 ProductName을 항상 가지고 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"강정치킨", "순살치킨"})
    void name(String name) {
        final ProductName productName = new ProductName(name, new StubBanWordFilter(false));

        assertThat(productName.getName()).isEqualTo(name);
    }

    @DisplayName("ProddctName에는 ban word가 포함될 수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void ban_words(String name) {

        new ProductName(name, new StubBanWordFilter(true));
    }
}
