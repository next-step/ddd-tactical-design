package kitchenpos.eatinorders.application

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kitchenpos.deliveryorders.infra.DefaultKitchenridersClient
import kitchenpos.eatinorders.application.OrderService
import kitchenpos.eatinorders.domain.Order
import kitchenpos.eatinorders.domain.OrderRepository
import kitchenpos.eatinorders.domain.OrderStatus.DELIVERING
import kitchenpos.eatinorders.domain.OrderStatus.SERVED
import kitchenpos.eatinorders.domain.OrderStatus.WAITING
import kitchenpos.eatinorders.domain.OrderType.DELIVERY
import kitchenpos.eatinorders.domain.OrderType.TAKEOUT
import kitchenpos.testsupport.FakeMenuRepository
import kitchenpos.testsupport.FakeOrderRepository
import kitchenpos.testsupport.FakeOrderTableRepository
import kitchenpos.testsupport.OrderFixtures

class OrderServiceStartDeliveryTest : ShouldSpec({
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
                type = DELIVERY,
                status = SERVED,
                menus = listOf()
            )
        )
    }

    context("주문 시작") {
        should("성공") {
            // given
            val orderId = savedOrder.id

            // when
            val order = service.startDelivery(orderId)

            // then
            order.status shouldBe DELIVERING
        }

        should("실패 - 배달 주문이 아닌 경우") {
            // given
            val invalidOrder = orderRepository.save(
                OrderFixtures.createOrder(
                    type = TAKEOUT,
                    status = SERVED,
                    menus = listOf()
                )
            )
            val orderId = invalidOrder.id

            // when
            val exception = shouldThrowAny {
                service.startDelivery(orderId)
            }

            // then
            exception.shouldBeTypeOf<IllegalStateException>()
        }

        should("실패 - 주문 전달 상태가 아닌 경우") {
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
                service.startDelivery(orderId)
            }

            // then
            exception.shouldBeTypeOf<IllegalStateException>()
        }
    }
})
