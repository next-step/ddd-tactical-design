package kitchenpos.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Index {

    @Column(name = "index")
    private Long index;

    protected Index () {}

    public Index(final Long index){
        validateIndex(index);
        this.index = index;
    }

    public long valueOf() {
        return index;
    }

    private void validateIndex (Long index){
        if((!Objects.isNull(index)) && index <= 0){
            throw new IndexException("자연수를 입력해주세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index1 = (Index) o;
        return Objects.equals(index, index1.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
