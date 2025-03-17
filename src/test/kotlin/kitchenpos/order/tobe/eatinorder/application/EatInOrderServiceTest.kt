package kitchenpos.order.tobe.eatinorder.application

import java.math.BigDecimal
import java.util.*
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.ProductInfo
import kitchenpos.menu.tobe.infra.FakeMenuRepository
import kitchenpos.order.tobe.common.DefaultOrderMenuClient
import kitchenpos.order.tobe.eatinorder.application.dto.CreateEatInOrderLineItemReq
import kitchenpos.order.tobe.eatinorder.application.dto.CreateEatInOrderReq
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.order.tobe.eatinorder.domain.OrderTableOccupancy
import kitchenpos.order.tobe.eatinorder.domain.OrderTableStatus
import kitchenpos.order.tobe.eatinorder.infra.DefaultEatInOrderOrderTableClient
import kitchenpos.order.tobe.eatinorder.infra.FakeEatInOrderRepository
import kitchenpos.order.tobe.eatinorder.infra.FakeOrderTableRepository
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class EatInOrderServiceTest {

    private lateinit var eatInOrderService: EatInOrderService

    private lateinit var eatInOrderRepository: EatInOrderRepository

    private lateinit var menuId: UUID
    private lateinit var orderTableId: UUID

    @BeforeEach
    fun setUp() {
        val menuRepository = FakeMenuRepository()
        val orderTableRepository = FakeOrderTableRepository()

        eatInOrderRepository = FakeEatInOrderRepository()
        eatInOrderService = EatInOrderService(
            eatInOrderRepository = eatInOrderRepository,
            orderMenuClient = DefaultOrderMenuClient(menuRepository),
            eatInOrderOrderTableClient = DefaultEatInOrderOrderTableClient(orderTableRepository)
        )

        val productId = UUID.randomUUID()
        menuId = menuRepository.save(
            Fixtures.menu(
                productInfos = mapOf(productId to ProductInfo(productId, price = BigDecimal.valueOf(10000))),
                price = 10000,
                menuProducts = MenuProducts(
                    listOf(
                        MenuProduct(
                            seq = 1,
                            productId = productId,
                            quantity = 1
                        )
                    )
                )
            )
        ).id
        orderTableId = orderTableRepository.save(
            Fixtures.orderTable(
                orderTableOccupancy = OrderTableOccupancy(
                    0,
                    OrderTableStatus.OCCUPIED
                )
            )
        ).id
    }

    @Test
    @DisplayName("EatInOrder를 생성한다")
    fun create() {
        // given
        val createEatInOrderReq = CreateEatInOrderReq(
            orderLineItems = listOf(
                CreateEatInOrderLineItemReq(
                    seq = 1,
                    menuId = menuId,
                    quantity = 1,
                )
            ),
            orderTableId = orderTableId
        )

        // when
        val eatInOrderId = eatInOrderService.create(createEatInOrderReq)

        // then
        val eatInOrder = eatInOrderRepository.findById(eatInOrderId).get()
        assertAll(
            { assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.WAITING) },
            { assertThat(eatInOrder.orderTableId).isEqualTo(orderTableId) },
        )
    }

    @Test
    @DisplayName("존재하지않는 Menu가 포함된 EatInOrder를 생성할 수 없다")
    fun createFailNonExistMenu() {
        // given
        val createEatInOrderReq = CreateEatInOrderReq(
            orderLineItems = listOf(
                CreateEatInOrderLineItemReq(
                    seq = 1,
                    menuId = Fixtures.INVALID_UUID,
                    quantity = 1,
                )
            ),
            orderTableId = orderTableId
        )

        // when & then
        assertThatThrownBy {
            eatInOrderService.create(createEatInOrderReq)
        }.isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    @DisplayName("존재하지않는 OrderTable로 EatInOrder를 생성할 수 없다")
    fun createFailNonExistOrderTable() {
        // given
        val createEatInOrderReq = CreateEatInOrderReq(
            orderLineItems = listOf(
                CreateEatInOrderLineItemReq(
                    seq = 1,
                    menuId = menuId,
                    quantity = 1,
                )
            ),
            orderTableId = Fixtures.INVALID_UUID
        )

        // when & then
        assertThatThrownBy {
            eatInOrderService.create(createEatInOrderReq)
        }.isInstanceOf(NoSuchElementException::class.java)
    }


    @Test
    @DisplayName("EatInOrder를 accept한다")
    fun accept() {
        // given
        val eatInOrderId = eatInOrderRepository.save(
            Fixtures.eatInOrder(
                orderTableId = orderTableId,
                menuId = menuId,
                status = EatInOrderStatus.WAITING
            )
        ).id

        // when
        eatInOrderService.accept(eatInOrderId)

        // then
        val eatInOrder = eatInOrderRepository.findById(eatInOrderId).get()
        assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.ACCEPTED)
    }

    @Test
    @DisplayName("존재하지 않는 EatInOrder를 accept할 수 없다")
    fun acceptFailNonExistEatInOrder() {
        // given
        val nonExistEatInOrderId = Fixtures.INVALID_UUID

        // when & then
        assertThatThrownBy {
            eatInOrderService.accept(nonExistEatInOrderId)
        }.isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    @DisplayName("EatInOrder를 serve한다")
    fun serve() {
        // given
        val eatInOrderId = eatInOrderRepository.save(
            Fixtures.eatInOrder(
                orderTableId = orderTableId,
                menuId = menuId,
                status = EatInOrderStatus.ACCEPTED
            )
        ).id

        // when
        eatInOrderService.serve(eatInOrderId)

        // then
        val eatInOrder = eatInOrderRepository.findById(eatInOrderId).get()
        assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.SERVED)
    }

    @Test
    @DisplayName("존재하지 않는 EatInOrder를 serve할 수 없다")
    fun serveFailNonExistEatInOrder() {
        // given
        val nonExistEatInOrderId = Fixtures.INVALID_UUID

        // when & then
        assertThatThrownBy {
            eatInOrderService.serve(nonExistEatInOrderId)
        }.isInstanceOf(NoSuchElementException::class.java)
    }
}
