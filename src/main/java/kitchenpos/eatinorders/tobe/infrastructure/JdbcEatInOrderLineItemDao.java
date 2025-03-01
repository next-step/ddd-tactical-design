package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.vo.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public class JdbcEatInOrderLineItemDao implements EatInOrderLineItemDao {

    private static final String EAT_IN_ORDER_LINE_ITEMS_TABLE = "eat_in_order_line_items";
    private static final String ID = "id";
    private static final String MENU_ID = "menu_id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String EAT_IN_ORDER_ID = "eat_in_order_id";
    private static final String QUANTITY = "quantity";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert menuProductJdbcInsert;

    public JdbcEatInOrderLineItemDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.menuProductJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(EAT_IN_ORDER_LINE_ITEMS_TABLE);
    }

    @Override
    public void saveAll(final List<EatInOrderLineItem> eatInOrderLineItems) {
        final List<MapSqlParameterSource> mapSqlParameterSources = eatInOrderLineItems.stream()
                .map(eatInOrderLineItem -> new MapSqlParameterSource()
                        .addValue(ID, eatInOrderLineItem.eatInOrderLineItemIdValue())
                        .addValue(MENU_ID, eatInOrderLineItem.menuId())
                        .addValue(NAME, eatInOrderLineItem.orderLineItemNameValue())
                        .addValue(PRICE, eatInOrderLineItem.orderLineItemPriceValue())
                        .addValue(EAT_IN_ORDER_ID, eatInOrderLineItem.eatInOrderIdValue())
                        .addValue(QUANTITY, eatInOrderLineItem.quantityValue())
                ).toList();
        final int[] results = menuProductJdbcInsert.executeBatch(mapSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        if (results.length != eatInOrderLineItems.size()) {
            throw new IllegalArgumentException("주문 항목 저장에 실패했습니다.");
        }
    }

    @Override
    public List<EatInOrderLineItem> findAllByEatInOrderId(final EatInOrderId id) {
        final String sql = "SELECT id, menu_id, name, price, eat_in_order_id, quantity FROM eat_in_order_line_items WHERE eat_in_order_id = :eat_in_order_id";
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("eat_in_order_id", id.getValue());
        return jdbcTemplate.query(sql, mapSqlParameterSource, (rs, rowNum) -> toEatInOrder(rs));
    }

    @Override
    public List<EatInOrderLineItem> findAll() {
        final String sql = "SELECT id, menu_id, name, price, eat_in_order_id, quantity FROM eat_in_order_line_items";
        return jdbcTemplate.query(sql, (rs, rowNum) -> toEatInOrder(rs));
    }

    private EatInOrderLineItem toEatInOrder(final ResultSet resultSet) throws SQLException {
        return new EatInOrderLineItem(
                new EatInOrderLineItemId(resultSet.getString(ID)),
                UUID.fromString(resultSet.getString(MENU_ID)),
                new EatInOrderLineItemName(resultSet.getString(NAME)),
                new EatInOrderLineItemPrice(resultSet.getInt(PRICE)),
                new Quantity(resultSet.getInt(QUANTITY)));
    }
}
