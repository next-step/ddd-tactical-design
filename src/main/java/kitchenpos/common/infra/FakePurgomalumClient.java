package kitchenpos.common.infra;

public class FakePurgomalumClient implements PurgomalumClient {
    private final boolean isContainsProfanity;

    public FakePurgomalumClient(boolean containsProfanity) {
        this.isContainsProfanity = containsProfanity;
    }

    @Override
    public boolean containsProfanity(String text) {
        return isContainsProfanity;
    }
}
