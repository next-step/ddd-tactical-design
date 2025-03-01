package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemId;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.order.vo.Quantity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
                        .addValue("name", eatInOrderLineItem.name())
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
        return jdbcTemplate.query(
                "SELECT id, menu_id, name, price, eat_in_order_id, quantity FROM eat_in_order_line_items WHERE eat_in_order_id = :eat_in_order_id",
                new MapSqlParameterSource("eat_in_order_id", id.getValue()),
                (rs, rowNum) -> new EatInOrderLineItem(
                        new EatInOrderLineItemId(rs.getString("id")),
                        UUID.fromString(rs.getString("menu_id")),
                        rs.getString("name"),
                        new EatInOrderLineItemPrice(rs.getInt("price")),
                        new Quantity(rs.getInt("quantity"))
                )
        );
    }

    @Override
    public List<EatInOrderLineItem> findAll() {
        return jdbcTemplate.query(
                "SELECT id, menu_id, name, price, eat_in_order_id, quantity FROM eat_in_order_line_items",
                (rs, rowNum) -> new EatInOrderLineItem(
                        new EatInOrderLineItemId(rs.getString("id")),
                        UUID.fromString(rs.getString("menu_id")),
                        rs.getString("name"),
                        new EatInOrderLineItemPrice(rs.getInt("price")),
                        new Quantity(rs.getInt("quantity"))
                )
        );
    }
}
