package kitchenpos.products.domain;

public interface ProfanityValidator {

    boolean containsProfanity(String text);

    void validate(String text);
}
