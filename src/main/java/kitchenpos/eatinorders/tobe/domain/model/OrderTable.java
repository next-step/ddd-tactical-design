package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.service.Validator;

import java.util.UUID;

public class OrderTable {

    private final UUID id;

    private final DisplayedName displayedName;

    private NumberOfGuests numberOfGuests;

    private boolean isEmpty;

    public OrderTable(final UUID id, final DisplayedName displayedName, final NumberOfGuests numberOfGuests) {
        this.id = id;
        this.displayedName = displayedName;
        this.numberOfGuests = numberOfGuests;
        this.isEmpty = true;
    }

    public void sit() {
        isEmpty = false;
    }

    public void clear(final Validator<OrderTable> orderTableClearValidator) {
        orderTableClearValidator.validate(this);

        numberOfGuests = new NumberOfGuests(0L);
        isEmpty = true;
    }

    public void changeNumberOfGuests(final NumberOfGuests numberOfGuests) {
        if (isEmpty) {
            throw new IllegalStateException("빈 테이블의 방문한 손님 수는 0에서 변경할 수 없습니다.");
        }

        this.numberOfGuests = numberOfGuests;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return displayedName.value();
    }

    public long getNumberOfGuests() {
        return numberOfGuests.value();
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
