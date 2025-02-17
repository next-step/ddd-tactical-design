package kitchenpos.products.infra;

@FunctionalInterface
public interface PurgomalumClient {
    boolean containsProfanity(String text);
}
