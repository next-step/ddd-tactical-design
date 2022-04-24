package kitchenpos.menus.dto;

import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static kitchenpos.Fixtures.tobeMenuProducts;
import static org.assertj.core.api.Assertions.assertThat;

class MenuRegisterRequestTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }

    @DisplayName("메뉴 생성을 요청한다")
    @Test
    void create() throws Exception {
        //given
        MenuGroupId menuGroupId = new MenuGroupId(UUID.randomUUID());
        MenuRegisterRequest request = new MenuRegisterRequest(
                "맛있는치킨들",
                BigDecimal.valueOf(17_000),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 17_000L, 1),
                true
        );
        //when
        Set<ConstraintViolation<MenuRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isEmpty();
    }

    @DisplayName("이름 없는 메뉴는 생성을 요청할 수 없다")
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    @ParameterizedTest
    void create_fail_blank_name(final String name) throws Exception {
        //given
        MenuGroupId menuGroupId = new MenuGroupId(UUID.randomUUID());
        MenuRegisterRequest request = new MenuRegisterRequest(
                name,
                BigDecimal.valueOf(17_000),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 17_000L, 1),
                true
        );
        //when
        Set<ConstraintViolation<MenuRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("메뉴의 이름은 필수값 입니다");
        });
    }

    @DisplayName("메뉴의 가격은 0 이상이어야 합니다")
    @ValueSource(strings = {"-1"})
    @NullSource
    @ParameterizedTest
    void create_fail_price(final BigDecimal price) throws Exception {
        //given
        MenuGroupId menuGroupId = new MenuGroupId(UUID.randomUUID());
        MenuRegisterRequest request = new MenuRegisterRequest(
                "맛있는치킨",
                price,
                menuGroupId,
                tobeMenuProducts("맛나치킨", 17_000L, 1),
                true
        );
        //when
        Set<ConstraintViolation<MenuRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("메뉴의 가격은 0 이상이어야 합니다");
        });
    }

    @DisplayName("메뉴는 반드시 하나의 메뉴그룹에 속해야 합니다")
    @NullSource
    @ParameterizedTest
    void create_fail_menuGroup(final MenuGroupId menuGroupId) throws Exception {
        MenuRegisterRequest request = new MenuRegisterRequest(
                "맛있는치킨",
                BigDecimal.valueOf(17_000),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 17_000L, 1),
                true
        );
        //when
        Set<ConstraintViolation<MenuRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("메뉴는 반드시 하나의 메뉴그룹에 속해야 합니다");
        });
    }

    @DisplayName("메뉴는 반드시 하나 이상의 상품을 포함하고 있어야 합니다")
    @NullSource
    @ParameterizedTest
    void create_fail_menuProducts(final MenuProducts menuProducts) throws Exception {
        //given
        MenuGroupId menuGroupId = new MenuGroupId(UUID.randomUUID());
        MenuRegisterRequest request = new MenuRegisterRequest(
                "맛있는치킨",
                BigDecimal.valueOf(17_000),
                menuGroupId,
                menuProducts,
                true
        );
        //when
        Set<ConstraintViolation<MenuRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("메뉴는 반드시 하나 이상의 상품을 포함하고 있어야 합니다");
        });
    }

    @DisplayName("메뉴의 전시상태를 선택해 주세요")
    @NullSource
    @ParameterizedTest
    void create_fail_display(final Boolean displayed) throws Exception {
        //given
        MenuGroupId menuGroupId = new MenuGroupId(UUID.randomUUID());
        MenuRegisterRequest request = new MenuRegisterRequest(
                "맛있는치킨",
                BigDecimal.valueOf(17_000),
                menuGroupId,
                tobeMenuProducts("맛나치킨", 17_000L, 1),
                displayed
        );
        //when
        Set<ConstraintViolation<MenuRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("메뉴의 전시상태를 선택해 주세요");
        });
    }
}
