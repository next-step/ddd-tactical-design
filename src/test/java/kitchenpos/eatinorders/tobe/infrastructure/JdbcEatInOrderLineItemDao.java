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

    private static final String SEQ = "seq";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert menuProductJdbcInsert;

    public JdbcEatInOrderLineItemDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.menuProductJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("eat_in_order_line_items")
                .usingGeneratedKeyColumns(SEQ);
    }

    @Override
    public void saveAll(final List<EatInOrderLineItem> eatInOrderLineItems) {
        final List<MapSqlParameterSource> mapSqlParameterSources = eatInOrderLineItems.stream()
                .map(eatInOrderLineItem -> new MapSqlParameterSource()
                        .addValue("id", eatInOrderLineItem.idValue())
                        .addValue("menu_id", eatInOrderLineItem.menuId())
                        .addValue("name", eatInOrderLineItem.nameValue())
                        .addValue("price", eatInOrderLineItem.priceValue())
                        .addValue("eat_in_order_id", eatInOrderLineItem.eatInOrderIdValue())
                        .addValue("quantity", eatInOrderLineItem.quantityValue())
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
                new EatInOrderLineItemId(resultSet.getString("id")),
                UUID.fromString(resultSet.getString("menu_id")),
                new EatInOrderLineItemName(resultSet.getString("name")),
                new EatInOrderLineItemPrice(resultSet.getInt("price")),
                new Quantity(resultSet.getInt("quantity")));
    }
}
