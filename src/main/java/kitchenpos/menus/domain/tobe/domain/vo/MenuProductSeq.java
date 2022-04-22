package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MenuProductSeq extends ValueObject<MenuProductSeq> implements Serializable {
    @Column(name = "seq")
    private Long seq;

    public MenuProductSeq(Long seq) {
        this.seq = seq;
    }

    protected MenuProductSeq() {
    }

    public Long getValue() {
        return seq;
    }

}
