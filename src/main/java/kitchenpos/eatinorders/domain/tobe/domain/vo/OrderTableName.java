package kitchenpos.eatinorders.domain.tobe.domain.vo;

import kitchenpos.support.infra.profanity.Profanity;
import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName extends ValueObject<OrderTableName> {

    @Column(name = "name")
    private String name;

    public OrderTableName(String name, Profanity profanity) {
        if (Objects.isNull(name) || "".equals(name)) {
            throw new IllegalArgumentException("테이블 이름은 빈 값이 올 수 없습니다.");
        }
        if (profanity.containsProfanity(name)) {
            throw new IllegalArgumentException("테이블 이름에는 욕설이 포함될 수 없습니다.");
        }
        this.name = name;
    }

    protected OrderTableName() {

    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderTableName that = (OrderTableName) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
