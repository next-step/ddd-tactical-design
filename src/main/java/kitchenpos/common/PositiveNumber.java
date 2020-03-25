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
        validatePositiveNumber();
        this.number = number;
    }

    public long valueOf (){
        return number;
    }

    private void validatePositiveNumber (){
        if(Objects.isNull(number) && number <= 0){
            throw new PositiveNumberException("자연수를 입력해주세요.");
        }
    }
}
