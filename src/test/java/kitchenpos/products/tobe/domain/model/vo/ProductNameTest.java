package kitchenpos.products.tobe.domain.model.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import kitchenpos.global.domain.ProfanityCheckClient;
import kitchenpos.products.application.FakeProfanityCheckClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    private final ProfanityCheckClient profanityCheckClient = new FakeProfanityCheckClient();

    @ParameterizedTest
    @ValueSource(strings = {"비속어가 포함된 이름", "욕설이 포함된 이름"})
    void 상품이름에는_비속어가_포함될_수_없다(String name) {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ProductName(name, profanityCheckClient));
    }

    @Test
    void 상품이름은_동등성을_보장한다() {
        // given
        var name1 =new ProductName("a", profanityCheckClient);
        var name2 =new ProductName("a", profanityCheckClient);
        var name3 =new ProductName("b", profanityCheckClient);

        // when & then
        assertThat(name1).isEqualTo(name2);
        assertThat(name1).isNotEqualTo(name3);
    }
}