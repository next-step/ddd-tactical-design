package kitchenpos.products.tobe.exception;

public class DisplayedNameNullExeption extends IllegalArgumentException {

    private static final String DISPLAYED_NAME_SHOULD_NOT_BE_NULL = "DisplayedName은 null이 될 수 없습니다";

    public DisplayedNameNullExeption() {
        super(DISPLAYED_NAME_SHOULD_NOT_BE_NULL);
    }

    public DisplayedNameNullExeption(final String s) {
        super(s);
    }

}
