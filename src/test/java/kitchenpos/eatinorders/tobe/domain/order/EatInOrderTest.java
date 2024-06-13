package kitchenpos.eatinorders.tobe.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTest {

  @DisplayName("주문을 수락할 수 있다.")
  @Test
  void accept() {
    EatInOrder eatInOrder = EatInOrderFixture.create(EatInOrderStatus.WAITING);
    eatInOrder.accept();
    assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
  }

  @DisplayName("주문 수락시 주문의 상태는 대기중이어야한다.")
  @Test
  void acceptFailBecauseStatusNotWaiting() {
    EatInOrder eatInOrder = EatInOrderFixture.create(EatInOrderStatus.ACCEPTED);
    assertThatIllegalStateException()
        .isThrownBy(eatInOrder::accept);
  }

  @DisplayName("주문을 준비완료할 수 있다.")
  @Test
  void serve() {
    EatInOrder eatInOrder = EatInOrderFixture.create(EatInOrderStatus.ACCEPTED);
    eatInOrder.serve();
    assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
  }

  @DisplayName("주문 준비완료 시 주문의 상태는 수락이어야한다.")
  @Test
  void serveFailBecauseStatusNotAccepted() {
    EatInOrder eatInOrder = EatInOrderFixture.create(EatInOrderStatus.WAITING);
    assertThatIllegalStateException()
        .isThrownBy(eatInOrder::serve);
  }

  @DisplayName("주문을 완료할 수 있다.")
  @Test
  void complete() {
    EatInOrder eatInOrder = EatInOrderFixture.create(EatInOrderStatus.SERVED);
    eatInOrder.complete();
    assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
  }

  @DisplayName("주문 완료 시 주문의 상태는 준비완료여야한다.")
  @Test
  void completeFailBecauseStatusNotServed() {
    EatInOrder eatInOrder = EatInOrderFixture.create(EatInOrderStatus.ACCEPTED);
    assertThatIllegalStateException()
        .isThrownBy(eatInOrder::complete);
  }
}
