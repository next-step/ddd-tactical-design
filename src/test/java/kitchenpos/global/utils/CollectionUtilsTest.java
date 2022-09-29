package kitchenpos.global.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CollectionUtilsTest {

    @DisplayName("빈 컬렉션 검증")
    @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
    @NullAndEmptySource
    void isEmpty(List<String> actual) {
        assertThat(CollectionUtils.isEmpty(actual))
                .isTrue();
    }
}
