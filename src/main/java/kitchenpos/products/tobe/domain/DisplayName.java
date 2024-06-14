package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class DisplayName {

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    protected DisplayName() {
    }

    public DisplayName(String name, DisplayNamePolicy displayNamePolicy) {
        if (displayNamePolicy.checkPolicy(name)) {
            throw new IllegalArgumentException("상품 이름에는 비속어가 포함될 수 없습니다.");
        }
        this.name = validate(name);

    }

    private String validate(String name) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("상품명은 필수로 입력해야 합니다.");
        }
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
