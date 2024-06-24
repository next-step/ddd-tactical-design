package kitchenpos.products.tobe;

public record Name(
        String value,
        boolean containProfanity
) {

    public Name {
        if (value == null) {
            throw new IllegalArgumentException("상품명은 null이 될 수 없습니다.");
        }
    }

}
