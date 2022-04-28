package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.common.exception.InvalidNameException;
import kitchenpos.util.StringUtils;

@Embeddable
public class OrderTableName {

    @Column(name = "name", nullable = false)
    private String name;

    protected OrderTableName() { }

    private OrderTableName(String name) {
        this.name = name;
    }

    public static OrderTableName create(String name) {
        if (StringUtils.isBlank(name)) {
            throw new InvalidNameException("주문 메뉴명은 비워둘 수 없습니다.");
        }

        return new OrderTableName(name);
    }

    public String value() {
        return name;
    }
}
