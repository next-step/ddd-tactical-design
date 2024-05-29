package kitchenpos.domain.support;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Guest {
    @Column(name = "count", nullable = false)
    private final int count;

    public Guest() {
        this(0);
    }

    public Guest(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count는 음수일 수 없습니다.");
        }
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
