package kitchenpos.menus.tobe.application.menugroup;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.menugroup.Name;
import kitchenpos.menus.tobe.infra.menugroup.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.ui.menugroup.MenuGroupCreateRequest;

public class MenuGroupServiceTest {
	private MenuGroupRepository menuGroupRepository;
	private MenuGroupService menuGroupService;

	@BeforeEach
	void setUp() {
		menuGroupRepository = new InMemoryMenuGroupRepository();
		menuGroupService = new MenuGroupService(menuGroupRepository);
	}

	@Test
	void 메뉴그룹을_등록할_수_있다() {
		// given
		MenuGroupCreateRequest request = new MenuGroupCreateRequest("추천메뉴");

		// when
		MenuGroup actual = menuGroupService.create(request);

		// then
		assertThat(actual).isNotNull();
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(new Name(request.getName()))
		);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void 이름이_빈_값이면_등록할_수_없다(String name) {
		// given
		MenuGroupCreateRequest request = new MenuGroupCreateRequest(name);

		// when & then
		assertThatThrownBy(() -> menuGroupService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
