package kitchenpos.menus.tobe.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Count {
    private static final int MINIMUM_COUNT = 0;

    @Column(name = "count", nullable = false)
    private int count;

    protected Count() {
    }

    public Count(int count) {
        validate(count);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    private void validate(int count) {
        if (count < MINIMUM_COUNT) {
            throw new IllegalArgumentException("수량은 " + MINIMUM_COUNT + "개 이상이어야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Count count1 = (Count) o;
        return getCount() == count1.getCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCount());
    }
}
