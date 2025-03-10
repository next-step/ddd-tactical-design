package kitchenpos.eatinorders.tobe.application.restaurant;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.TobeEatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTable;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTableRepository;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableChangeRequest;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableCreateRequest;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TobeRestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;
    private final TobeEatInOrderRepository eatInOrderRepository;

    public TobeRestaurantTableService(final RestaurantTableRepository restaurantTableRepository,
        TobeEatInOrderRepository eatInOrderRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Transactional(readOnly = true)
    public RestaurantTable getById(UUID restaurantTableId) {
        return restaurantTableRepository.findById(restaurantTableId)
            .orElseThrow(() -> new NoSuchElementException(
                "RestaurantTable not found with id: " + restaurantTableId));
    }

    @Transactional
    public RestaurantTableResponse create(final RestaurantTableCreateRequest request) {
        final RestaurantTable restaurantTable = new RestaurantTable(request.name());
        return RestaurantTableResponse.from(restaurantTableRepository.save(restaurantTable));
    }

    @Transactional
    public RestaurantTableResponse sit(final UUID restaurantTableId) {
        final RestaurantTable restaurantTable = getById(restaurantTableId);
        restaurantTable.occupy();
        return RestaurantTableResponse.from(restaurantTable);
    }

    @Transactional
    public RestaurantTableResponse clear(final UUID restaurantTableId) {
        final RestaurantTable restaurantTable = getById(restaurantTableId);
        if (eatInOrderRepository.existsByRestaurantTableIdAndStatusNot(restaurantTableId,
            EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        restaurantTable.clear();
        return RestaurantTableResponse.from(restaurantTable);
    }

    @Transactional
    public RestaurantTableResponse changeNumberOfGuests(final UUID restaurantTableId,
        final RestaurantTableChangeRequest request) {
        final RestaurantTable restaurantTable = getById(restaurantTableId);
        restaurantTable.changeNumberOfGuests(request.numberOfGuests());
        return RestaurantTableResponse.from(restaurantTable);
    }

    @Transactional(readOnly = true)
    public List<RestaurantTable> findAll() {
        return restaurantTableRepository.findAll();
    }
}
