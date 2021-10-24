package kitchenpos.eatinorders.application.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.TableNumberOfGuests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class TableNumberOfGuestsTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void 인원수_생성(int number) {
        TableNumberOfGuests numberOfGuests = new TableNumberOfGuests(number);

        assertThat(numberOfGuests).isNotNull();
    }

    @Test
    void 인원수_예외_확인() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new TableNumberOfGuests(-1));
    }
}
