package kitchenpos.menus.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupRegisterRequestTest {
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

    @DisplayName("메뉴그룹 생성을 요청한다")
    @Test
    void create() throws Exception {
        //given
        MenuGroupRegisterRequest request = new MenuGroupRegisterRequest("맛있는치킨들");
        //when
        Set<ConstraintViolation<MenuGroupRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isEmpty();
    }

    @DisplayName("이름 없는 메뉴그룹은 생성을 요청할 수 없다")
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    @ParameterizedTest
    void create_fail_blank_name(final String name) throws Exception {
        //given
        MenuGroupRegisterRequest request = new MenuGroupRegisterRequest(name);
        //when
        Set<ConstraintViolation<MenuGroupRegisterRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("메뉴그룹의_이름은_필수값_입니다");
        });
    }

}
