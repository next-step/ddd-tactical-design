package kitchenpos.menu.domain.repository;

import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuSummary;
import kitchenpos.menu.infra.persistence.QuerydslMenuQueryRepository;
import kitchenpos.product.domain.model.ProductSummary;
import kitchenpos.product.domain.repository.ProductSummaryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(QuerydslMenuQueryRepository.class)
class MenuQueryRepositoryTest {

    @Autowired
    private MenuSummaryRepository menuSummaryRepository;

    @Autowired
    private ProductSummaryRepository productSummaryRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private MenuQueryRepository menuQueryRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }
    }

    @Test
    @DisplayName("메뉴 요약 정보를 조회한다.")
    void find_all() {
        // given
        MenuGroup menuGroup1 = createAndSaveMenuGroup();
        MenuGroup menuGroup2 = createAndSaveMenuGroup();

        List<ProductSummary> firstProductSummaries = List.of(createAndSaveProductSummary());
        MenuSummary menuSummary1 = new MenuSummary("김치찌개1", BigDecimal.valueOf(8000), true, menuGroup1.getId(),
                menuGroup1.getName(),
                firstProductSummaries);
        menuSummaryRepository.save(menuSummary1);

        List<ProductSummary> secondProductSummaries = List.of(createAndSaveProductSummary());
        MenuSummary menuSummary2 = new MenuSummary("김치찌개2", BigDecimal.valueOf(8000), true, menuGroup2.getId(),
                menuGroup2.getName(),
                secondProductSummaries);
        menuSummaryRepository.save(menuSummary2);

        // when
        List<MenuSummary> result = menuQueryRepository.findAll();

        // then
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .satisfiesExactlyInAnyOrder(
                        menu -> {
                            assertThat(menu.getMenuName()).isEqualTo(menuSummary1.getMenuName());
                            assertThat(menu.getPrice()).isEqualTo(menuSummary1.getPrice());
                            assertThat(menu.getMenuGroupId()).isEqualTo(menuGroup1.getId());
                            assertThat(menu.getProductSummaries()).hasSize(1);
                            assertThat(menu.getProductSummaries().getFirst().getName()).isEqualTo("김치");
                        },
                        menu -> {
                            assertThat(menu.getMenuName()).isEqualTo(menuSummary2.getMenuName());
                            assertThat(menu.getPrice()).isEqualTo(menuSummary2.getPrice());
                            assertThat(menu.getMenuGroupId()).isEqualTo(menuGroup2.getId());
                            assertThat(menu.getProductSummaries()).hasSize(1);
                            assertThat(menu.getProductSummaries().getFirst().getName()).isEqualTo("김치");
                        }
                );
    }

    private ProductSummary createAndSaveProductSummary() {
        ProductSummary productSummary = new ProductSummary("김치", 3);
        productSummaryRepository.save(productSummary);
        return productSummary;
    }

    private MenuGroup createAndSaveMenuGroup() {
        return menuGroupRepository.save(createMenuGroup());
    }
}
