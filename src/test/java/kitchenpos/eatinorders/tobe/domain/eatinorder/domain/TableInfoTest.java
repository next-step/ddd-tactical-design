package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class TableInfoTest {

    @DisplayName("정상적으로 생성되는 것 테스트.")
    @ParameterizedTest
    @CsvSource(value = {"1,false", "0,true"})
    void create(final int numberOfGuests, final boolean empty) {
        TableInfo result = new TableInfo(numberOfGuests, empty, null);

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(numberOfGuests),
                () -> assertThat(result.isEmpty()).isEqualTo(empty)
        );
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 입력할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"-1,false", "3,true"})
    void createFail(final int numberOfGuests, final boolean empty) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new TableInfo(numberOfGuests, empty, null));
    }

    @DisplayName("빈 테이블로 바꿀 수 있다.")
    @Test
    void changeEmpty() {
        TableInfo result = new TableInfo(0, true, null);
        result.changeEmpty();

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(0),
                () -> assertThat(result.isEmpty()).isEqualTo(true)
        );
    }

    @DisplayName("단체 지정이 되어 있으면 상태를 바꿀 수 없다.")
    @Test
    void changeEmptyFail() {
        TableInfo sample = new TableInfo(0, true, 1L);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sample.changeEmpty());
    }

    @DisplayName("단체지정 되는 것 테스트.")
    @Test
    void group() {
        TableInfo sample = new TableInfo(0, true, null);

        TableInfo result = sample.group(1L);

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(0),
                () -> assertThat(result.isEmpty()).isFalse(),
                () -> assertThat(result.getTableGroupId()).isEqualTo(1L)
        );
    }

    @DisplayName("단체해제 되는 것 테스트.")
    @Test
    void ungroup() {
        TableInfo sample = new TableInfo(0, true, 2L);

        TableInfo result = sample.ungroup();

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(0),
                () -> assertThat(result.isEmpty()).isFalse(),
                () -> assertThat(result.getTableGroupId()).isEqualTo(null)
        );
    }
}
