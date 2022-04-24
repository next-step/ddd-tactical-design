package kitchenpos.menus.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MenuPriceChangeRequestTest {
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

    @DisplayName("메뉴가격 변경을 요청한다")
    @Test
    void create() throws Exception {
        //given
        MenuPriceChangeRequest request = new MenuPriceChangeRequest(BigDecimal.valueOf(17_000));
        //when
        Set<ConstraintViolation<MenuPriceChangeRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isEmpty();
    }

    @DisplayName(value = "변경하려는 메뉴가격은 0원 이상이어야 합니다")
    @NullSource
    @ValueSource(strings = {"-1"})
    @ParameterizedTest
    void create_fail_price(final BigDecimal price) throws Exception {
        //given
        MenuPriceChangeRequest request = new MenuPriceChangeRequest(price);
        //when
        Set<ConstraintViolation<MenuPriceChangeRequest>> violations = validator.validate(request);
        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo("변경하려는 메뉴가격은 0원 이상이어야 합니다");
        });

    }

}
