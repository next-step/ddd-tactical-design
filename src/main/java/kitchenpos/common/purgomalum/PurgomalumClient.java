package kitchenpos.common.purgomalum;

@FunctionalInterface
public interface PurgomalumClient {
    boolean containsProfanity(String text);
}
