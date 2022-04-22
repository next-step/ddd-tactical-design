package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.vo.Id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class MenuGroupId extends Id {
    @Column(name = "id")
    private UUID id;

    public MenuGroupId(UUID id) {
        this.id = id;
    }

    protected MenuGroupId() {
    }

    public UUID getValue() {
        return id;
    }
}
