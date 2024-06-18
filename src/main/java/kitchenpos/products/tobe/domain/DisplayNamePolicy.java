package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.infra.Profanities;

@DomainService
public class DisplayNamePolicy {
    private final Profanities profanities;

    public DisplayNamePolicy(Profanities profanities) {
        this.profanities = profanities;
    }

    public void validate(final String name) {
        if(this.checkPolicy(name)){
            throw new IllegalArgumentException("상품 이름에 비속어가 포함될 수 없습니다.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("상품명은 필수로 입력해야 합니다.");
        }
    }

    private boolean checkPolicy(final String name) {
        return profanities.containsProfanity(name);
    }



}

