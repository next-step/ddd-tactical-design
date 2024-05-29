package kitchenpos.tobe.domain.eatinorder.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import tobe.domain.OrderTableV2Fixtures.createOrderTableV2
import java.util.*

class OrderTableTest() : DescribeSpec() {
    init {
        describe("OrderTable 클래스의") {
            describe("from 메소드는") {
                it("OrderTable 객체를 생성한다.") {
                    val id = UUID.randomUUID()
                    val name = "테이블1"
                    val numberOfGuests = 4
                    val occupied = false

                    val orderTable = OrderTableV2.from(id, name, numberOfGuests, occupied)

                    orderTable.id shouldBe id
                    orderTable.name shouldBe name
                    orderTable.numberOfGuests shouldBe numberOfGuests
                    orderTable.occupied shouldBe occupied
                }
            }

            describe("clear 메소드는") {
                it("주문 테이블의 속성을 초기화한다.") {
                    val orderTable = createOrderTableV2()

                    orderTable.clear()

                    orderTable.numberOfGuests shouldBe 0
                    orderTable.occupied shouldBe false
                }
            }

            describe("sit 메소드는") {
                it("주문 테이블을 점유한다.") {
                    val orderTable = createOrderTableV2()

                    orderTable.sit()

                    orderTable.occupied shouldBe true
                }
            }

            describe("changeNumberOfGuests 메소드는") {
                context("적절한 손님 수가 주어지면") {
                    it("주문 테이블의 손님 수를 변경한다.") {
                        val orderTable =
                            createOrderTableV2().also {
                                it.occupied = true
                            }
                        val numberOfGuests = 5

                        orderTable.changeNumberOfGuests(numberOfGuests)

                        orderTable.numberOfGuests shouldBe numberOfGuests
                    }
                }

                context("0명 미만의 손님 수가 주어지면") {
                    it("예외를 발생시킨다.") {
                        val orderTable = createOrderTableV2()
                        val numberOfGuests = -1

                        val exception =
                            shouldThrow<IllegalArgumentException> {
                                orderTable.changeNumberOfGuests(numberOfGuests)
                            }

                        exception.message shouldBe "고객 수는 0명 이상이어야 합니다."
                    }
                }

                context("주문 테이블이 점유되지않은 경우") {
                    it("예외를 발생시킨다.") {
                        val orderTable = createOrderTableV2()
                        val numberOfGuests = 5

                        val exception =
                            shouldThrow<IllegalArgumentException> {
                                orderTable.changeNumberOfGuests(numberOfGuests)
                            }

                        exception.message shouldBe "점유되지않은 주문 테이블의 고객 수는 변경할 수 없습니다."
                    }
                }
            }
        }
    }
}
