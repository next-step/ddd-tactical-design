package kitchenpos.common.infra.external;

import kitchenpos.common.application.PurgomalumClient;

public class FakePurgomalumClient implements PurgomalumClient {

    private boolean isProfanity = false;

    @Override
    public boolean containsProfanity(String text) {
        return isProfanity;
    }

    public void setProfanity(boolean isProfanity) {
        this.isProfanity = isProfanity;
    }
}
