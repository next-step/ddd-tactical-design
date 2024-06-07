package kitchenpos.products.tobe.domain.vo;

public class Price {
    final int value;

    public Price(int value) {
        this.validate(value);
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    private void validate(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Price should be over zero, not negative");
        }
    }

}
