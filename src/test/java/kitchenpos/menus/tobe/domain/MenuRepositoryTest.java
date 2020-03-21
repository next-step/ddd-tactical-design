package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void name() {
        for (Menu menu : menuRepository.findAll()) {
            System.out.println(menu);
        }
    }
}
