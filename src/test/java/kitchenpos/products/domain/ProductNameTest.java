package kitchenpos.products.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ProductNameTest {

    @Test
    @DisplayName("상품 이름이 정상이면 정상동작")
    void testNameCanBeAssigned() {
        ProductName productName = new ProductName("Burger");
        assertThat(productName.getName()).isEqualTo("Burger");
    }

    @Test
    @DisplayName("상품 이름이 같으면 같은 객체")
    void testEqual() {
        ProductName productName1 = new ProductName("Burger");
        ProductName productName2 = new ProductName("Burger");
        Assertions.assertEquals(productName1, productName2);
    }

    @Test
    @DisplayName("상품 이름이 null 이면 예외발생")
    void testNameCannotBeNull() {
        assertThatCode(() -> new ProductName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}