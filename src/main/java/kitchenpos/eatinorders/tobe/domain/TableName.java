package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class TableName {

    @Column(name = "name", nullable = false)
    private String name;

    protected TableName() { }

    public TableName(String name) {
        validationTableName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validationTableName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
