package kitchenpos.menus.tobe.domain.menu;

import java.util.Objects;

public final class MenuProductId {

    private final long seq;

    public MenuProductId(long seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProductId)) {
            return false;
        }
        MenuProductId that = (MenuProductId) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return "MenuProductId{" +
            "seq=" + seq +
            '}';
    }
}
