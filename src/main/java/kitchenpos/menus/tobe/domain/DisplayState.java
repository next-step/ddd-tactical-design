package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DisplayState {

    @Column(name = "displayed", nullable = false)
    private final boolean displayed;

    public static DisplayState from(boolean displayed) {
        return new DisplayState(displayed);
    }

    public static DisplayState show() {
        return new DisplayState(true);
    }

    public static DisplayState hide() {
        return new DisplayState(false);
    }

    protected DisplayState() {
        this(true);
    }

    private DisplayState(boolean displayed) {
        this.displayed = displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayState that = (DisplayState) o;
        return displayed == that.displayed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayed);
    }
}
