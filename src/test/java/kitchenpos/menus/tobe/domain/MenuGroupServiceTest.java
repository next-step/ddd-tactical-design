package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    private MenuGroup expectedMenuGroup;
    private List<MenuGroup> expectedMenuGroups;

    @BeforeEach
    void setUp() {
        expectedMenuGroup = new MenuGroup("추천 메뉴");
        expectedMenuGroups = Arrays.asList(
                new MenuGroup("두마리메뉴"),
                new MenuGroup("한마리메뉴"),
                new MenuGroup("순살파닭두마리메뉴"),
                new MenuGroup("신메뉴")
        );
    }

    @Test
    @DisplayName("객체 생성 테스트")
    void create() {
        // give
        given(menuGroupRepository.save(new MenuGroup("메뉴 그룹")))
                .willReturn(expectedMenuGroup);
        MenuGroup actualMenuGroup = menuGroupService.create(new MenuGroup("메뉴 그룹"));
        // when
        boolean same = actualMenuGroup.equals(expectedMenuGroup);
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("객체 조회")
    void list() {
        // give
        given(menuGroupRepository.findAll())
                .willReturn(expectedMenuGroups);

        List<MenuGroup> actualMenuGroups = menuGroupService.list();
        // when
        boolean same = actualMenuGroups.containsAll(expectedMenuGroups);
        // then
        assertThat(same).isTrue();
    }
}
