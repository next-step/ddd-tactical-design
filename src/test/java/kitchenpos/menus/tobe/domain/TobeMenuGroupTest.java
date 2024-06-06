package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

class TobeMenuGroupTest {

    @DisplayName("메뉴 그룹의 이름은 비워 둘 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void case_1(String name) {
        // given

        // when
        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new TobeMenuGroup(UUID.randomUUID(), name));

    }

}