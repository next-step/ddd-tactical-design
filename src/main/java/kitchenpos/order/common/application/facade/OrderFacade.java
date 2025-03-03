package kitchenpos.order.common.application.facade;

import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.application.dto.OrderRequest;
import kitchenpos.order.common.application.dto.OrderResponse;
import kitchenpos.order.common.application.dto.OrderResponse.GetOrder;
import kitchenpos.order.common.application.dto.OrderTableRequest.Create;
import kitchenpos.order.common.application.dto.OrderTableRequest.UpdateGuests;
import kitchenpos.order.common.application.dto.OrderTableResponse;
import kitchenpos.order.common.application.dto.OrderTableResponse.GetOrderTable;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.service.OrderCommandService;
import kitchenpos.order.common.domain.service.OrderQueryService;
import kitchenpos.order.delivery.domain.service.DeliveryService;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.service.EatinService;
import org.springframework.stereotype.Component;

@Component
public class OrderFacade {
    private final DeliveryService deliveryService;
    private final EatinService eatinService;
    private final OrderQueryService orderQueryService;

    private final OrderCommandService orderCommandService;

    public OrderFacade(
        DeliveryService deliveryService,
        EatinService eatinService,
        OrderQueryService orderQueryService,
        OrderCommandService orderCommandService
    ) {
        this.deliveryService = deliveryService;
        this.eatinService = eatinService;
        this.orderQueryService = orderQueryService;
        this.orderCommandService = orderCommandService;
    }

    public GetOrder create(OrderRequest.Create request) {
        return GetOrder.fromVo(orderCommandService.create(request.toVo()));
    }

    public OrderResponse.GetOrder accept(final UUID orderId) {
        return GetOrder.fromVo(orderCommandService.accept(OrderId.of(orderId)));
    }

    public OrderResponse.GetOrder serve(final UUID orderId) {
        return GetOrder.fromVo(orderCommandService.serve(OrderId.of(orderId)));
    }

    public GetOrder startDelivery(final UUID orderId) {
        return GetOrder.fromVo(deliveryService.startDelivery(OrderId.of(orderId)));
    }

    public GetOrder completeDelivery(final UUID orderId) {
        return GetOrder.fromVo(deliveryService.completeDelivery(OrderId.of(orderId)));
    }


    public GetOrder complete(UUID orderId) {
        return GetOrder.fromVo(orderCommandService.complete(OrderId.of(orderId)));
    }

    public List<GetOrder> findAll() {
        return orderQueryService.findAll()
            .stream()
            .map(OrderResponse.GetOrder::fromVo)
            .toList();
    }

    public List<GetOrderTable> findOrderTableAll() {
        return eatinService.findAll()
            .stream()
            .map(OrderTableResponse.GetOrderTable::fromVo)
            .toList();
    }

    public GetOrderTable createOrderTable(Create request) {
        return GetOrderTable.fromVo(eatinService.create(request.toVo()));
    }

    public GetOrderTable sit(UUID orderTableId) {
        return GetOrderTable.fromVo(eatinService.sit(OrderTableId.of(orderTableId)));
    }

    public GetOrderTable clear(UUID orderTableId) {
        return GetOrderTable.fromVo(eatinService.clear(OrderTableId.of(orderTableId)));
    }

    public GetOrderTable changeNumberOfGuests(UpdateGuests request) {
        return GetOrderTable.fromVo(eatinService.changeNumberOfGuests(request.toVo()));
    }
}
