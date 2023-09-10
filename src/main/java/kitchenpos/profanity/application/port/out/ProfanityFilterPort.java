package kitchenpos.profanity.application.port.out;

public interface ProfanityFilterPort {
    boolean containsProfanity(String text);
}
