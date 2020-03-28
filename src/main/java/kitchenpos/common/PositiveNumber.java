package kitchenpos.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class PositiveNumber {

    @Column(name = "number")
    private Long number;

    protected PositiveNumber () {}

    public PositiveNumber(final Long number){
        validatePositiveNumber(number);
        this.number = number;
    }

    public long valueOf (){
        return number;
    }

    private void validatePositiveNumber (Long number){
        if(Objects.isNull(number) && number <= 0){
            throw new PositiveNumberException("자연수를 입력해주세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiveNumber that = (PositiveNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
