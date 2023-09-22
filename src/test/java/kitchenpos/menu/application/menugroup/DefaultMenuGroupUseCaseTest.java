package kitchenpos.menu.application.menugroup;

import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.menu.application.menugroup.port.in.MenuGroupDTO;
import kitchenpos.menu.application.menugroup.port.in.MenuGroupUseCase;
import kitchenpos.menu.application.menugroup.port.out.MenuGroupRepository;
import kitchenpos.menu.fakerepository.MenuGroupFakeRepository;
import kitchenpos.support.vo.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class DefaultMenuGroupUseCaseTest {

    private MenuGroupRepository repository;

    private MenuGroupUseCase menuGroupUseCase;

    @BeforeEach
    void setUp() {
        repository = new MenuGroupFakeRepository();

        menuGroupUseCase = new DefaultMenuGroupUseCase(repository);
    }

    @ParameterizedTest
    @ValueSource(strings = "추천메뉴")
    void register_name을_받아서_메뉴그룹을_등록한_후_반환한다(final String value) {
        // given
        final Name name = Name.create(value);

        // when
        final MenuGroupDTO actual = menuGroupUseCase.register(name);

        // then
        assertThat(actual.getId()).isNotNull();
    }
}