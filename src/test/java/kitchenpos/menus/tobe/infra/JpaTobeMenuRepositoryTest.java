package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct;
import kitchenpos.shared.domain.DefaultProfanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Transactional
@DataJpaTest
class JpaTobeMenuRepositoryTest {

    @Autowired
    private TobeMenuRepository sut;

    @Autowired
    private TobeMenuGroupRepository tobeMenuGroupRepository;

    private TobeMenuGroup menuGroup;

    @BeforeEach
    void setUp() {
        this.menuGroup = tobeMenuGroupRepository.save(new TobeMenuGroup(UUID.randomUUID(), "슈퍼 후라이드 세트"));
    }

    @DisplayName("TobeMenuRepository is not null")
    @Test
    void case_1() {
        // given

        // when

        // then
        assertThat(sut).isNotNull();
    }

    @DisplayName("Menu 저장")
    @Test
    void case_2() {
        // given
        var name = "후라이드";
        var price = 10_000;
        var profanities = new DefaultProfanities();
        var menuProducts = List.of(new TobeMenuProduct(1, 10_000, UUID.randomUUID()));

        // when
        var menu = new TobeMenu(name, price, profanities, menuGroup, menuProducts);
        TobeMenu savedMenu = sut.save(menu);

        // then
        UUID id = savedMenu.getId();
    }

}