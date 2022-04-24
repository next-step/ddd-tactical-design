package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class ChangeNumberOfGuestsRequest {
    @NotNull(message = "변경하려는 테이블을 선택해 주세요.")
    private UUID id;
    @Positive(message = "착석인원은 0명 이상이어야 합니다.")
    private int numberOfGuests;

    private ChangeNumberOfGuestsRequest(UUID id) {
        this.id = id;
    }

    public ChangeNumberOfGuestsRequest(UUID id, int numberOfGuests) {
        this(id);
        this.numberOfGuests = numberOfGuests;
    }

    public OrderTableId getId() {
        return new OrderTableId(id);
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeNumberOfGuestsRequest that = (ChangeNumberOfGuestsRequest) o;

        if (numberOfGuests != that.numberOfGuests) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + numberOfGuests;
        return result;
    }
}
