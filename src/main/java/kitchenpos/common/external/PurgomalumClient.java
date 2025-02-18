package kitchenpos.common.external;

@FunctionalInterface
public interface PurgomalumClient {
    boolean containsProfanity(String text);
}
