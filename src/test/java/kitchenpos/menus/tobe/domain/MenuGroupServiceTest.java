package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MenuGroupServiceTest {

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    private MenuGroup expectedMenuGroup;

    @BeforeEach
    void setUp() {
        expectedMenuGroup = new MenuGroup("추천 메뉴");
    }

    @Test
    @DisplayName("객체 생성 테스트")
    void create() {
        // given
        given(menuGroupRepository.save(new MenuGroup("메뉴 그룹")))
                .willReturn(expectedMenuGroup);
        MenuGroup actualMenuGroup = menuGroupService.create(new MenuGroup("메뉴 그룹"));
        // when
        boolean same = actualMenuGroup.equals(expectedMenuGroup);
        // then
        assertThat(same).isTrue();
    }
}
