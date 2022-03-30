package kitchenpos.global.infrastructure.external;

public interface BannedWordCheckClient {
    boolean containsProfanity(String text);
}
