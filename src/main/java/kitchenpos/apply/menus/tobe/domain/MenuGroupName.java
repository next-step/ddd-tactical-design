package kitchenpos.apply.menus.tobe.domain;

import kitchenpos.support.domain.ValueObject;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuGroupName extends ValueObject {
    @Column(name = "name", nullable = false)
    private String value;

    protected MenuGroupName() { }

    public MenuGroupName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
        }
        this.value = name;
    }

    public String value() {
        return value;
    }
}
