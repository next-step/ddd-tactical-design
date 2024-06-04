package kitchenpos.products.domain.tobe;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.fake.FakePurgomalumClient;
import kitchenpos.products.domain.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Product")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TobeProductTest {

    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Test
    void 가격이_null인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new TobeProduct("후라이드", purgomalumClient, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 가격이_마이너스인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(
                () -> new TobeProduct("후라이드", purgomalumClient, BigDecimal.valueOf(-20_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_null인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new TobeProduct(null, null, BigDecimal.valueOf(20_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 단어"})
    void 이름에_비속어가들어가는_Product를_생성하면_예외를_던진다(final String name) {
        assertThatThrownBy(
                () -> new TobeProduct(name, purgomalumClient, BigDecimal.valueOf(20_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}