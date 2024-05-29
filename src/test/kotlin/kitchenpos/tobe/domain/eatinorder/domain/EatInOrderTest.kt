package kitchenpos.tobe.domain.eatinorder.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import kitchenpos.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.tobe.eatinorder.domain.entity.EatInOrder
import tobe.domain.EatInOrderFixtures.createEatInOrder
import tobe.domain.OrderLineItemV2Fixtures.createOrderLineItemV2
import tobe.domain.OrderTableV2Fixtures.createOrderTableV2
import java.time.LocalDateTime

class EatInOrderTest : DescribeSpec() {
    init {
        describe("EatInOrder 클래스의") {
            describe("from 메소드는") {
                context("적절한 요청이 주어졌을때") {
                    it("매장 내 식사 주문 정보를 반환한다") {
                        val eatInOrder =
                            EatInOrder.from(
                                orderDateTime = LocalDateTime.now(),
                                orderLineItems = listOf(createOrderLineItemV2()),
                                orderTable = createOrderTableV2(),
                            )

                        eatInOrder shouldNotBe null
                    }
                }
            }

            describe("accept 메소드는") {
                context("대기 상태가 아니라면") {
                    val eatInOrder =
                        createEatInOrder().also {
                            it.status = EatInOrderStatus.SERVED
                        }
                    it("예외를 던진다") {
                        shouldThrow<IllegalArgumentException> { eatInOrder.accept() }
                    }
                }
            }

            describe("serve 메소드는") {
                context("접수됨 상태가 아니라면") {
                    val eatInOrder =
                        createEatInOrder().also {
                            it.status = EatInOrderStatus.SERVED
                        }
                    it("예외를 던진다") {
                        val exception = shouldThrow<IllegalArgumentException> { eatInOrder.serve() }
                        exception.message?.contains("접수됨 상태의 주문만 제공할 수 있습니다.")
                    }
                }
            }

            describe("complete 메소드는") {
                context("제공됨 상태가 아니라면") {
                    val eatInOrder =
                        createEatInOrder().also {
                            it.status = EatInOrderStatus.WAITING
                        }
                    it("예외를 던진다") {
                        val exception = shouldThrow<IllegalArgumentException> { eatInOrder.complete() }
                        exception.message?.contains("제공됨 상태의 주문만 수락할 수 있습니다.")
                    }
                }
            }
        }
    }
}
