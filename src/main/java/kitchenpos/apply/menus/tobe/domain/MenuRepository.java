package kitchenpos.apply.menus.tobe.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> findById(UUID id);

    @Query("select distinct m from Menu m join fetch m.menuProducts.menuProductList mp where m.id = :menuId")
    Optional<Menu> findByMenuId(@Param("menuId") UUID menuId);

    @Query("select count(m) from Menu m where m.id = :id and m.displayed = true and m.price = :price")
    boolean existsByIdAndDisplayedWithPrice(UUID id, BigDecimal price);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<UUID> ids);

    @Query("select distinct m from Menu m join fetch m.menuProducts.menuProductList mp where mp.productId = :productId")
    List<Menu> findAllByProductId(@Param("productId") UUID productId);
}

