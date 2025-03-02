package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;

public class EatInOrderLineItemSeq {
    private final static EatInOrderLineItemSeq UNINITIALIZED_SEQ = new EatInOrderLineItemSeq(null);
    private final Long seq;

    private EatInOrderLineItemSeq(final Long seq) {
        this.seq = seq;
    }

    public static EatInOrderLineItemSeq of(final Long seq) {
        if (seq == null) {
            return UNINITIALIZED_SEQ;
        }
        return new EatInOrderLineItemSeq(seq);
    }

    public Long value() {
        return seq;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItemSeq that = (EatInOrderLineItemSeq) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seq);
    }
}
