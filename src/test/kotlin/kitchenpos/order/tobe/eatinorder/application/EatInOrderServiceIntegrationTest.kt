package kitchenpos.order.tobe.eatinorder.application

import java.math.BigDecimal
import java.util.*
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.domain.ProductInfo
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderCompleteEvent
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.order.tobe.eatinorder.domain.OrderTableOccupancy
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository
import kitchenpos.order.tobe.eatinorder.domain.OrderTableStatus
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.event.ApplicationEvents
import org.springframework.test.context.event.RecordApplicationEvents

@SpringBootTest
@RecordApplicationEvents
class EatInOrderServiceIntegrationTest {
    @Autowired
    private lateinit var events: ApplicationEvents

    @SpyBean
    private lateinit var orderTableEatInOrderCompleteEventListener: OrderTableEatInOrderCompleteEventListener


    @Autowired
    private lateinit var eatInOrderService: EatInOrderService

    @Autowired
    private lateinit var orderTableRepository: OrderTableRepository

    @Autowired
    private lateinit var eatInOrderRepository: EatInOrderRepository

    @Autowired
    private lateinit var menuRepository: MenuRepository

    @Autowired
    private lateinit var menuGroupRepository: MenuGroupRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    private lateinit var menuId: UUID
    private lateinit var orderTableId: UUID
    private lateinit var eatInOrder: EatInOrder


    @BeforeEach
    fun setup() {
        val menuGroup = menuGroupRepository.save(Fixtures.menuGroup())
        val product = productRepository.save(Fixtures.product(price = 10000))
        menuId = menuRepository.save(
            Fixtures.menu(
                productInfos = mapOf(product.id to ProductInfo(product.id, price = BigDecimal.valueOf(10000))),
                menuGroup = menuGroup,
                price = 10000,
                menuProducts = MenuProducts(
                    listOf(
                        MenuProduct(
                            seq = 1,
                            productId = product.id,
                            quantity = 1
                        )
                    )
                )
            )
        ).id
        orderTableId = orderTableRepository.save(
            Fixtures.orderTable(
                orderTableOccupancy = OrderTableOccupancy(
                    4,
                    OrderTableStatus.OCCUPIED
                )
            )
        ).id

        eatInOrder = eatInOrderRepository.save(
            Fixtures.eatInOrder(
                orderTableId = orderTableId,
                menuId = menuId,
                status = EatInOrderStatus.SERVED
            )
        )

    }

    @Test
    @DisplayName("EatInOrder를 complete하면 EatInOrderCompleteEvent를 발행한다")
    fun publishEatInOrderCompleteEvent() {
        // when
        eatInOrderService.complete(eatInOrder.id)

        // then
        assertThat(events.stream(EatInOrderCompleteEvent::class.java)).hasSize(1)
    }

    @Test
    @DisplayName("EatInOrder를 complete하면 OrderTable도 EmtpyTable로 변경된다")
    fun changeOrderTableStatusToEmptyTable() {
        // when
        eatInOrderService.complete(eatInOrder.id)

        // then
        val orderTable = orderTableRepository.findById(orderTableId).orElseThrow()
        assertThat(orderTable.orderTableOccupancy.status).isEqualTo(OrderTableStatus.EMPTY)
        assertThat(orderTable.orderTableOccupancy.numberOfGuests).isEqualTo(0)
    }

    @Test
    @DisplayName("EatInOrder를 complete할때 OrderTable이 EmptyTable로 변경되지 않으면 롤백된다")
    fun rollbackWhenOrderTableStatusNotChanged() {
        // given
        doThrow(RuntimeException()).`when`(orderTableEatInOrderCompleteEventListener)
            .handle(any())

        // when
        assertThatThrownBy { eatInOrderService.complete(eatInOrder.id) }

        // then
        val orderTable = orderTableRepository.findById(orderTableId).orElseThrow()
        assertThat(orderTable.orderTableOccupancy.status).isEqualTo(OrderTableStatus.OCCUPIED)
        assertThat(orderTable.orderTableOccupancy.numberOfGuests).isEqualTo(4)
    }


}
