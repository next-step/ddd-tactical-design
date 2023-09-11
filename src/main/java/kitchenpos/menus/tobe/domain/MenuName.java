package kitchenpos.menus.tobe.domain;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.tobe.domain.PurgomalumClient;

@Embeddable
public class MenuName {

    @Column(name = "name", nullable = false)
    private String name;

    public MenuName(String name, PurgomalumClient purgomalumClient) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("메뉴명은 필수입니다");
        }
        checkNameIsProfanity(name, purgomalumClient);

        this.name = name;
    }

    protected MenuName() {
    }

    private void checkNameIsProfanity(String name, PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("메뉴명은 비속어가 될수 없습니다");
        }
    }


    public String getName() {
        return name;
    }
}
