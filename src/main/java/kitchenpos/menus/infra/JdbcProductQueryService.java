package kitchenpos.menus.infra;

import kitchenpos.menus.application.ProductQueryService;
import kitchenpos.menus.tobe.domain.ProductPriceInfo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JdbcProductQueryService implements ProductQueryService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcProductQueryService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<UUID, BigDecimal> findAllByIdIn(List<UUID> productIds) {
        String sql = "SELECT id, price FROM product p WHERE p.id IN (:productIds)";
        MapSqlParameterSource map = new MapSqlParameterSource("productIds", productIds);
        List<ProductPriceInfo> productPriceInfos = jdbcTemplate.queryForList(sql, map, ProductPriceInfo.class);
        return productPriceInfos.stream()
                .collect(Collectors.toMap(ProductPriceInfo::getId, ProductPriceInfo::getPrice));
    }
}
