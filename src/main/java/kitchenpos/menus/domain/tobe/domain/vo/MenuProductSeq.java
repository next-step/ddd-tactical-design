package kitchenpos.menus.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MenuProductSeq implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProductSeq productId = (MenuProductSeq) o;

        return seq != null ? seq.equals(productId.seq) : productId.seq == null;
    }

    @Override
    public int hashCode() {
        return seq != null ? seq.hashCode() : 0;
    }
}
