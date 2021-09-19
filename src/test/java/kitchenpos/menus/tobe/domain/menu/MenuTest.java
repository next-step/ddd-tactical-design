package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.fixture.MenuGroupFixture;

public class MenuTest {
	@Test
	void 메뉴를_생성할_수_있다() {
		// given
		DisplayedName name = new DisplayedName("후라이드+후라이드", text -> false);
		Price price = new Price(new BigDecimal(19_000L));
		MenuGroup menuGroup = MenuGroupFixture.메뉴그룹("추천메뉴");

		// when
		Menu menu = new Menu(name, price, menuGroup);

		// & then
		assertAll(
			() -> assertThat(menu).isNotNull(),
			() -> assertThat(menu.getId()).isNotNull(),
			() -> assertThat(menu.getName()).isEqualTo(name),
			() -> assertThat(menu.getPrice()).isEqualTo(price),
			() -> assertThat(menu.getMenuGroup()).isEqualTo(menuGroup)
		);
	}
}
