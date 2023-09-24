package kitchenpos.eatinorders.application.ordertable.port.in;

import java.util.UUID;

public interface OrderTableSitUseCase {

	void sit(final UUID id, final int numberOfGuests);

	void changeGuests(final UUID id, final int numberOfGuests);

	void clear(final UUID id);
}
