package kitchenpos.products.tobe.domain.vo;

public class EmptyProfanities implements Profanities {

    @Override
    public boolean contains(final String name) {
        return false;
    }
}
