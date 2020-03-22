package kitchenpos.menus.tobe.application;

import kitchenpos.common.WrongNameException;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.menugroup.application.DefaultMenuGroupService;
import kitchenpos.menus.tobe.menugroup.application.MenuGroupService;
import kitchenpos.menus.tobe.menugroup.dto.MenuGroupDto;
import kitchenpos.menus.tobe.menugroup.entity.MenuGroup;
import kitchenpos.menus.tobe.menugroup.exception.MenuGroupDuplicationException;
import kitchenpos.menus.tobe.menugroup.infra.MenuGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MenuGroupServiceTest {

    private final MenuGroupRepository repository = new InMemoryMenuGroupRepository();
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setuip(){
        menuGroupService = new DefaultMenuGroupService(repository);
    }

    @DisplayName("메뉴그룹을 등록한다.")
    @Test
    void register (){
        //given
        MenuGroupDto menuGroupDto = new MenuGroupDto(Fixtures.twoChickens());

        //when
        MenuGroupDto savedMenuGroupDto = menuGroupService.register(menuGroupDto);

        //then
        assertThat(savedMenuGroupDto).isEqualToComparingFieldByField(menuGroupDto);
    }

    @DisplayName("이미 등록한 메뉴그룹을 다시 등록 할 수 없다.")
    @Test
    void duplicatedMenuGroup (){
        //given
        repository.save(Fixtures.twoChickens());

        //when
        MenuGroupDto menuGroupDto = new MenuGroupDto(Fixtures.twoChickens());

        //then
        assertThatExceptionOfType(MenuGroupDuplicationException.class)
                .isThrownBy(() -> menuGroupService.register(menuGroupDto));
    }

    @DisplayName("메뉴그룹 이름을 입력해야 한다.")
    @Test
    void registerWithoutManuGroupName () {
        assertThatExceptionOfType(WrongNameException.class)
                .isThrownBy(() -> menuGroupService.register(new MenuGroupDto(Fixtures.nonameMenuGroup())));
    }

}
