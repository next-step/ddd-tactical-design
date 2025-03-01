package kitchenpos.eatinorders.tobe.poc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Deprecated
@Component
public class JdbcEatInOrderLineItemMenuDao implements EatInOrderLineItemMenuDao {

    private static final String SEQ = "seq";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert orderLineItemMenuJdbcInsert;

    public JdbcEatInOrderLineItemMenuDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.orderLineItemMenuJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("eat_in_order_line_item_menus")
                .usingGeneratedKeyColumns(SEQ);
        throw new UnsupportedOperationException("Deprecated 클래스입니다.");
    }

    @Override
    public void saveAll(final List<EatInOrderLineItemMenu> eatInOrderLineItemMenus) {
        final List<MapSqlParameterSource> mapSqlParameterSources = eatInOrderLineItemMenus.stream()
                .map(eatInOrderLineItemMenu -> new MapSqlParameterSource()
                        .addValue("eat_in_order_line_item_id", eatInOrderLineItemMenu.eatInOrderLineItemIdValue())
                        .addValue("menu_id", eatInOrderLineItemMenu.menuId())
                        .addValue("name", eatInOrderLineItemMenu.name())
                        .addValue("price", eatInOrderLineItemMenu.priceValue())
                ).toList();
        final int[] results = orderLineItemMenuJdbcInsert.executeBatch(mapSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        if (results.length != eatInOrderLineItemMenus.size()) {
            throw new IllegalArgumentException("주문 항목 메뉴 저장에 실패했습니다.");
        }
    }
}
