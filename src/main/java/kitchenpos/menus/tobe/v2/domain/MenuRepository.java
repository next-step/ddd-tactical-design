package kitchenpos.menus.tobe.v2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("select m from Menu m join fetch m.menuGroup join fetch m.menuProducts mp join fetch mp.product where mp.menu = m")
    List<Menu> findMenuAll();
}
