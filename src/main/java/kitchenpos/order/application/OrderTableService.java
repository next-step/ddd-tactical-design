package kitchenpos.order.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTableRepository;

@Service
public class OrderTableService {
	private final OrderTableRepository orderTableRepository;
	private final OrderRepository orderRepository;

	public OrderTableService(
		final OrderTableRepository orderTableRepository,
		final OrderRepository orderRepository
	) {
		this.orderTableRepository = orderTableRepository;
		this.orderRepository = orderRepository;
	}

	@Transactional
	public OrderTable create(final String name) {
		return orderTableRepository.save(
			new OrderTable(
				UUID.randomUUID(),
				name,
				0,
				false
			)
		);
	}

	@Transactional
	public OrderTable sit(final UUID orderTableId) {
		return orderTableRepository
				.findById(orderTableId)
				.orElseThrow(NoSuchElementException::new)
				.used(true);
	}

	@Transactional
	public OrderTable clear(final UUID orderTableId) {
		final OrderTable orderTable = orderTableRepository.findById(orderTableId)
			.orElseThrow(NoSuchElementException::new);

		if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
			throw new IllegalStateException();
		}

		orderTable.used(false);
		return orderTable;
	}

	@Transactional
	public OrderTable changeNumberOfGuests(final UUID orderTableId, final int numberOfGuests) {
		return orderTableRepository.findById(orderTableId)
			.orElseThrow(NoSuchElementException::new)
			.changedNumberOfGuests(numberOfGuests);
	}

	@Transactional(readOnly = true)
	public List<OrderTable> findAll() {
		return orderTableRepository.findAll();
	}
}
