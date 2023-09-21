package kitchenpos.menus.domain.tobe;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DisplayedNameTest {
    private final PurgomalumClient profanities = new FakeProfanities();
    @Test
    @DisplayName("비속어로 이름을 생성할 수 없다. ")
    void createDisplayNameSuccess() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->
                        new DisplayedName("욕설", profanities));
    }

    @Test
    @DisplayName("비속어가 아닌 내용으로 이름을 생성할 수 있다. ")
    void createDisplayNameFail() {
        assertThatNoException()
                .isThrownBy(() ->
                        new DisplayedName("치킨", profanities));
    }
}