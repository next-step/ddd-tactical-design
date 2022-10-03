package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class DisplayState {

    private final boolean displayed;

    public static DisplayState show() {
        return new DisplayState(true);
    }

    public static DisplayState hide() {
        return new DisplayState(false);
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
