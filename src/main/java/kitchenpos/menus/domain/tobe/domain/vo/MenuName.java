package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.infra.Profanity;
import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuName extends ValueObject<MenuName> {

    @Column(name = "name")
    private String name;

    public MenuName(String name, Profanity profanity) {
        if(profanity.containsProfanity(name)) {
            throw new IllegalArgumentException("메뉴 이름에는 욕설이 포함될 수 없습니다.");
        }
        this.name = name;
    }

    protected MenuName() {

    }

    public String getValue() {
        return name;
    }
}
