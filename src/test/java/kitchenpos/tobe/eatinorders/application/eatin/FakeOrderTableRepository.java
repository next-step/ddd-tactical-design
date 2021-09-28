package kitchenpos.tobe.eatinorders.application.eatin;

import static kitchenpos.tobe.eatinorders.application.Fixtures.EMPTY_TABLE_ID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;

public class FakeOrderTableRepository implements OrderTableRepository {

    private final Map<UUID, OrderTable> orderTables = new HashMap<>();

    @Override
    public OrderTable save(OrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<OrderTable> findById(UUID id) {
        if (id.equals(EMPTY_TABLE_ID)) {
            return getEmptyTable();
        }

        OrderTable orderTable = new OrderTable("테이블1번");
        Arrays.stream(OrderTable.class.getDeclaredFields()).forEach(field -> {
            if (field.getName().equals("id")) {
                field.setAccessible(true);
                try {
                    field.set(orderTable, id);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.getName().equals("empty")) {
                field.setAccessible(true);
                try {
                    field.set(orderTable, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        return Optional.of(orderTable);
    }

    @Override
    public List<OrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }

    private Optional<OrderTable> getEmptyTable() {

        OrderTable orderTable = new OrderTable("빈테이블");
        final Optional<Field> id = Arrays.stream(OrderTable.class.getDeclaredFields())
                .filter(field -> field.getName().equals("empty"))
                .findFirst();

        final Optional<Field> numberOfGuest = Arrays.stream(OrderTable.class.getDeclaredFields())
                .filter(field -> field.getName().equals("numberOfGuests"))
                .findFirst();

        try {
            id.get().setAccessible(true);
            id.get().set(orderTable, true);
            numberOfGuest.get().setAccessible(true);
            numberOfGuest.get().set(orderTable, new NumberOfGuests(0));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Optional.of(orderTable);
    }
}
