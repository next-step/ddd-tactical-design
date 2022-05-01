package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuDisplayRequestTest {
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


    @DisplayName("메뉴 전시를 요청한다")
    @Test
    void create() throws Exception {
        //given
        MenuDisplayRequest request = new MenuDisplayRequest(new MenuId(UUID.randomUUID()));
        //when
        Set<ConstraintViolation<MenuDisplayRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isEmpty();
    }

    @DisplayName("메뉴 번호 없이는 메뉴 전시를 요청할 수 없습니다")
    @NullSource
    @ParameterizedTest
    void create_fail_blank_name(final MenuId menuId) throws Exception {
        //given
        MenuDisplayRequest request = new MenuDisplayRequest(menuId);
        //when
        Set<ConstraintViolation<MenuDisplayRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("메뉴 번호 없이는 메뉴 전시를 요청할 수 없습니다");
        });
    }

}
