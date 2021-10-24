package kitchenpos.eatinorders.application.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.TableName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

public class TableNameTest {

    @Test
    void 테이블명_생성() {
        TableName tableName = new TableName("이름");

        assertThat(tableName).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 테이블명_예외_확인(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new TableName(name));
    }
}
