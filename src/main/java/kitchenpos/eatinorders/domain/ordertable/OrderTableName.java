package kitchenpos.eatinorders.domain.ordertable;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import kitchenpos.common.domain.ProfanityValidator;

@Embeddable
public class OrderTableName {
  private String name;

  protected OrderTableName() {
  }

  private OrderTableName(String name) {
    validate(name);
    this.name = name;
  }

  public static OrderTableName of(String name){
    return new OrderTableName(name);
  }

  public String getName() {
    return name;
  }

  private void validate(String name){
    if(Objects.isNull(name)){
      throw new IllegalArgumentException("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.");
    }

    if (name.isEmpty()){
      throw new IllegalArgumentException("주문 테이블의 이름은 비워 둘 수 없다.");
    }
  }


}

