package kitchenpos.menus.tobe.domain.menugroup.application;

import kitchenpos.common.WrongNameException;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.domain.menugroup.dto.MenuGroupDto;
import kitchenpos.menus.tobe.domain.menugroup.exception.MenuGroupDuplicationException;
import kitchenpos.menus.tobe.domain.menugroup.exception.MenuGroupNotFoundException;
import kitchenpos.menus.tobe.domain.menugroup.infra.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.menugroup.infra.InMemoryMenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

    @DisplayName("메뉴그룹이 등록되지 않았다.")
    @Test
    void notExistMenuGroup (){
        assertThatExceptionOfType(MenuGroupNotFoundException.class)
                .isThrownBy(() -> menuGroupService.isExist(Fixtures.TWO_CHICKENS_ID));
    }

}
