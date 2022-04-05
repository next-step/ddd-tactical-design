package kitchenpos.support;

import kitchenpos.products.domain.tobe.BanWordFilter;

public class StubBanWordFilter implements BanWordFilter {
    private boolean isBanWord;

    public StubBanWordFilter(boolean isBanWord) {
        this.isBanWord = isBanWord;
    }

    @Override
    public boolean containsProfanity(String text) {
        return isBanWord;
    }
}
