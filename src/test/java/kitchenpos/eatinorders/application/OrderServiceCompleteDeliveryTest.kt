package kitchenpos.eatinorders.application

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.mockk
import kitchenpos.eatinorders.application.OrderService
import kitchenpos.eatinorders.domain.Order
import kitchenpos.eatinorders.domain.OrderRepository
import kitchenpos.eatinorders.domain.OrderStatus.DELIVERED
import kitchenpos.eatinorders.domain.OrderStatus.DELIVERING
import kitchenpos.eatinorders.domain.OrderStatus.WAITING
import kitchenpos.eatinorders.domain.OrderType.DELIVERY
import kitchenpos.testsupport.FakeMenuRepository
import kitchenpos.testsupport.FakeOrderRepository
import kitchenpos.testsupport.FakeOrderTableRepository
import kitchenpos.testsupport.OrderFixtures

class OrderServiceCompleteDeliveryTest : ShouldSpec({
    lateinit var savedOrder: Order
    lateinit var orderRepository: OrderRepository
    lateinit var service: OrderService

    beforeTest {
        orderRepository = FakeOrderRepository()

        service = OrderService(
            orderRepository,
            FakeMenuRepository(),
            FakeOrderTableRepository(),
            mockk()
        )

        savedOrder = orderRepository.save(
            OrderFixtures.createOrder(
                type = DELIVERY,
                status = DELIVERING,
                menus = listOf()
            )
        )
    }

    context("주문 배달 완료") {
        should("성공") {
            // given
            val orderId = savedOrder.id

            // when
            val order = service.completeDelivery(orderId)

            // then
            order.status shouldBe DELIVERED
        }

        should("실패 - 주문이 배달 중 상태가 아닌 경우") {
            // given
            val invalidOrder = orderRepository.save(
                OrderFixtures.createOrder(
                    type = DELIVERY,
                    status = WAITING,
                    menus = listOf()
                )
            )
            val orderId = invalidOrder.id

            // when
            val exception = shouldThrowAny {
                service.completeDelivery(orderId)
            }

            // then
            exception.shouldBeTypeOf<IllegalStateException>()
        }
    }
})
