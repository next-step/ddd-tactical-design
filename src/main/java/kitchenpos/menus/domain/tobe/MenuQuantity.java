package kitchenpos.menus.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuQuantity {
    private static final String NEGATIVE_NUMBER_NOT_ALLOWED = "수량이 음수일 수 없습니다.";
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    protected MenuQuantity() {
    }

    public MenuQuantity(long quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(Long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(NEGATIVE_NUMBER_NOT_ALLOWED);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuQuantity)) return false;
        MenuQuantity that = (MenuQuantity) o;
        return Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
