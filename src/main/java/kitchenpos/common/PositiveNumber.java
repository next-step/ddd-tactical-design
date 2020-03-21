package kitchenpos.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PositiveNumber {

    @Column(name = "number")
    private long number;

    protected PositiveNumber () {}

    public PositiveNumber(final long number){
        validatePositiveNumber();
        this.number = number;
    }

    public long valueOf (){
        return number;
    }

    private void validatePositiveNumber (){
        if(number <= 0){
            throw new PositiveNumberException("자연수만 입력 할 수 있습니다.");
        }
    }
}
