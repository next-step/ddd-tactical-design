package kitchenpos.deliveryorders.application;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderAddress;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItemPrice;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItemQuantity;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItems;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.domain.KitchenridersClient;
import kitchenpos.deliveryorders.domain.MenuClient;
import kitchenpos.deliveryorders.shared.dto.DeliveryOrderLineItemDto;
import kitchenpos.deliveryorders.shared.dto.DeliveryOrderDto;
import kitchenpos.deliveryorders.shared.dto.request.DeliveryOrderCreateRequest;
import kitchenpos.shared.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryOrderService {
    private DeliveryOrderRepository deliveryOrderRepository;
    private MenuClient menuClient;
    private KitchenridersClient kitchenridersClient;

    public DeliveryOrderService(DeliveryOrderRepository deliveryOrderRepository, MenuClient menuClient, KitchenridersClient kitchenridersClient) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.menuClient = menuClient;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public DeliveryOrderDto create(final DeliveryOrderCreateRequest request) {
        final List<DeliveryOrderLineItemDto> orderLineItemRequests = request.getOrderLineItems();

        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<DeliveryOrderLineItem> deliveryOrderLineItems = request.getOrderLineItems().stream()
                .map(deliveryOrderLineItemDto -> DeliveryOrderLineItem.of(
                        deliveryOrderLineItemDto.getMenuId(),
                        new DeliveryOrderLineItemQuantity(deliveryOrderLineItemDto.getQuantity()),
                        new DeliveryOrderLineItemPrice(deliveryOrderLineItemDto.getPrice()),
                        menuClient
                ))
                .collect(Collectors.toList());

        DeliveryOrder deliveryOrder = DeliveryOrder.of(
                new DeliveryOrderLineItems(deliveryOrderLineItems),
                new DeliveryOrderAddress(request.getDeliveryAddress()),
                menuClient
        );

        return ConvertUtil.convert(deliveryOrderRepository.save(deliveryOrder), DeliveryOrderDto.class);
    }

    @Transactional
    public DeliveryOrderDto accept(final UUID orderId) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        deliveryOrder.accept(kitchenridersClient);
        return ConvertUtil.convert(deliveryOrder, DeliveryOrderDto.class);
    }

    @Transactional
    public DeliveryOrderDto serve(final UUID orderId) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        deliveryOrder.serve();
        return ConvertUtil.convert(deliveryOrder, DeliveryOrderDto.class);
    }

    @Transactional
    public DeliveryOrderDto startDelivery(final UUID orderId) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        deliveryOrder.startDelivery();
        return ConvertUtil.convert(deliveryOrder, DeliveryOrderDto.class);
    }

    @Transactional
    public DeliveryOrderDto completeDelivery(final UUID orderId) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        deliveryOrder.completeDelivery();
        return ConvertUtil.convert(deliveryOrder, DeliveryOrderDto.class);
    }

    @Transactional
    public DeliveryOrderDto complete(final UUID orderId) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        deliveryOrder.complete();
        return ConvertUtil.convert(deliveryOrder, DeliveryOrderDto.class);
    }

    @Transactional(readOnly = true)
    public List<DeliveryOrderDto> findAll() {
        return ConvertUtil.convertList(deliveryOrderRepository.findAll(), DeliveryOrderDto.class);
    }
}
