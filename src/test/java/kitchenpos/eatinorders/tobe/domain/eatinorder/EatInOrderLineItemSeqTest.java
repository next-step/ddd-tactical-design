package kitchenpos.eatinorders.tobe.domain.eatinorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class EatInOrderLineItemSeqTest {

    @DisplayName("매장 식사 주문 항목 번호를 생성할 수 있다.")
    @Test
    void create() {
        // given
        Long seq = 1L;

        // when
        EatInOrderLineItemSeq eatInOrderLineItemSeq = new EatInOrderLineItemSeq(seq);

        // then
        assertThat(eatInOrderLineItemSeq.getValue()).isEqualTo(seq);
    }

    @DisplayName("번호 없이 매장 식사 주문 항목 번호를 생성 시, 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    void createWithNull(Long seq) {
        // when then
        assertThatThrownBy(() -> new EatInOrderLineItemSeq(seq))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(EatInOrderLineItemSeq.ERROR_MESSAGE_VALUE_NULL);
    }
}
