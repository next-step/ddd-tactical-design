package kitchenpos.eatinorders.dto;

import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotBlank;

public class OrderTableRegisterRequest extends DTO {
    @NotBlank(message = "테이블의 이름은 빈 값이 올 수 없습니다")
    private final String name;

    public OrderTableRegisterRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTableRegisterRequest that = (OrderTableRegisterRequest) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
