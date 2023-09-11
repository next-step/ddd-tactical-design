package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NameTest {

    private FakePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("이름생성")
    @Test
    void 이름생성() {
        // when
        Name name = new Name("치킨", purgomalumClient);
        // that
        assertThat(name.getName()).isEqualTo("치킨");
    }

    @DisplayName("이름에는 비속어가 포함될 수 없다.")
    @Test
    void 이름생성_욕설포함() {
        // when then
        assertThatThrownBy(() -> new Name("욕설", purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
