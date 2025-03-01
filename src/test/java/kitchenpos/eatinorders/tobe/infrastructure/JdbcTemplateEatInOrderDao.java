package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderDateTime;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.NoneEatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;

@Component
public class JdbcTemplateEatInOrderDao implements EatInOrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert eatInOrderJdbcInsert;
    private static final int EXECUTE_FAILED = 0;

    public JdbcTemplateEatInOrderDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.eatInOrderJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("eat_in_orders");
    }

    @Override
    public void save(final EatInOrder eatInOrder) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(eatInOrder);
        final int execute = eatInOrderJdbcInsert.execute(parameterSource);
        if (execute == EXECUTE_FAILED) {
            throw new IllegalArgumentException("매장 주문 저장에 실패했습니다.");
        }
    }

    @Override
    public EatInOrder findById(final EatInOrderId id, final List<EatInOrderLineItem> eatInOrderLineItems) {
        final Map<UUID, List<EatInOrderLineItem>> eatInOrderIdListMap = eatInOrderLineItemMap(eatInOrderLineItems);
        final String sql = "SELECT id, order_table_id, order_datetime, order_status FROM eat_in_orders WHERE id = :id";
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameterSource, (resultSet, rowNumber) -> toEatInOrder(resultSet, eatInOrderIdListMap));
    }

    @Override
    public List<EatInOrder> findAll(final List<EatInOrderLineItem> eatInOrderLineItems) {
        final Map<UUID, List<EatInOrderLineItem>> eatInOrderIdListMap = eatInOrderLineItemMap(eatInOrderLineItems);
        final String sql = "SELECT id, order_table_id, order_datetime, order_status FROM eat_in_orders";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toEatInOrder(resultSet, eatInOrderIdListMap));
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final OrderTableId orderTableId, final EatInOrderStatus eatInOrderStatus) {
        final String sql = "SELECT COUNT(*) FROM eat_in_orders WHERE order_table_id = :order_table_id AND order_status != :order_status";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_table_id", orderTableId.getValue())
                .addValue("order_status", eatInOrderStatus.name());
        return jdbcTemplate.queryForObject(sql, parameterSource, Integer.class) > 0;
    }

    private Map<UUID, List<EatInOrderLineItem>> eatInOrderLineItemMap(final List<EatInOrderLineItem> eatInOrderLineItems) {
        return eatInOrderLineItems.stream()
                .collect(groupingBy(EatInOrderLineItem::eatInOrderIdValue));
    }

    private EatInOrder toEatInOrder(final ResultSet resultSet, final Map<UUID, List<EatInOrderLineItem>> eatInOrderIdListMap) throws SQLException {
        final UUID eatInOrderId = UUID.fromString(resultSet.getString("id"));
        return new EatInOrder(
                new EatInOrderId(eatInOrderId),
                EatInOrderStatus.of(resultSet.getString("eat_in_order_status")),
                new EatInOrderDateTime(resultSet.getTimestamp("order_datetime").toLocalDateTime()),
                new EatInOrderLineItems(eatInOrderIdListMap.get(eatInOrderId)),
                new NoneEatInOrderMenus(),
                new OrderTableId(resultSet.getString("order_table_id"))
        );
    }
}
