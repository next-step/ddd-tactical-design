package kitchenpos.product.tobe.domain;

import kitchenpos.exception.IllegalNameException;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductNameTest {
    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("이름이 같은 경우 동일하다.")
    @ParameterizedTest
    @ValueSource(strings = {"테스트이름"})
    void success(String input) {
        final var name1 = new ProductName(input, purgomalumClient.containsProfanity(input));
        final var name2 = new ProductName(input, purgomalumClient.containsProfanity(input));

        assertThat(name2).isEqualTo(name1);
    }

    @DisplayName("[실패] 상품 이름을 입력하지 않는 경우 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    void nameFailTest(String input) {

        assertThrows(IllegalNameException.class, () -> new ProductName(input, false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"욕설", "욕설 포함된"})
    @DisplayName("[실패] 상품 이름에 욕설이 포함되어있는 경우 등록할 수 없다.")
    void nameFailTest2(final String name) {

        assertThrows(IllegalArgumentException.class, () -> new ProductName(name, purgomalumClient.containsProfanity(name)));
    }
}
