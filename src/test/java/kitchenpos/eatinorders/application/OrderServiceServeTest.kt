package kitchenpos.eatinorders.application

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kitchenpos.deliveryorders.infra.DefaultKitchenridersClient
import kitchenpos.eatinorders.application.OrderService
import kitchenpos.eatinorders.domain.Order
import kitchenpos.eatinorders.domain.OrderRepository
import kitchenpos.eatinorders.domain.OrderStatus.ACCEPTED
import kitchenpos.eatinorders.domain.OrderStatus.SERVED
import kitchenpos.eatinorders.domain.OrderStatus.WAITING
import kitchenpos.eatinorders.domain.OrderType.TAKEOUT
import kitchenpos.testsupport.FakeMenuRepository
import kitchenpos.testsupport.FakeOrderRepository
import kitchenpos.testsupport.FakeOrderTableRepository
import kitchenpos.testsupport.OrderFixtures

class OrderServiceServeTest : ShouldSpec({
    lateinit var savedOrder: Order
    lateinit var orderRepository: OrderRepository
    lateinit var service: OrderService

    beforeTest {
        orderRepository = FakeOrderRepository()

        service = OrderService(
            orderRepository,
            FakeMenuRepository(),
            FakeOrderTableRepository(),
            DefaultKitchenridersClient()
        )

        savedOrder = orderRepository.save(
            OrderFixtures.createOrder(
                type = TAKEOUT,
                status = ACCEPTED,
                menus = listOf()
            )
        )
    }

    context("주문 전달") {
        should("성공") {
            // given
            val orderId = savedOrder.id

            // when
            val order = service.serve(orderId)

            // then
            order.status shouldBe SERVED
        }

        should("실패 - 주문 접수 상태가 아닌 경우") {
            // given
            val notAcceptedOrder = orderRepository.save(
                OrderFixtures.createOrder(
                    type = TAKEOUT,
                    status = WAITING,
                    menus = listOf()
                )
            )
            val orderId = notAcceptedOrder.id

            // when
            val exception = shouldThrowAny {
                service.serve(orderId)
            }

            // then
            exception.shouldBeTypeOf<IllegalStateException>()
        }
    }
})
