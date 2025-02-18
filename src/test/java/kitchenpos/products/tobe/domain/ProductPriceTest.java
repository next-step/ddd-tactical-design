package kitchenpos.products.tobe.domain;

import static org.junit.jupiter.api.Assertions.*;

import kitchenpos.products.tobe.exception.InvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductPriceTest {

    @Test
    @DisplayName("가격이 null일 때 InvalidPriceException이 발생해야 한다.")
    void testFrom_NullPrice_ThrowsInvalidPriceException() {
        // Arrange
        BigDecimal price = null;

        // Act & Assert
        assertThrows(InvalidPriceException.class, () -> {
            ProductPrice.from(price);
        });
    }

    @Test
    @DisplayName("가격이 0보다 작을 때 InvalidPriceException이 발생해야 한다.")
    void testFrom_NegativePrice_ThrowsInvalidPriceException() {
        // Arrange
        BigDecimal price = new BigDecimal("-10.00");

        // Act & Assert
        assertThrows(InvalidPriceException.class, () -> {
            ProductPrice.from(price);
        });
    }

    @Test
    @DisplayName("가격이 0일 때 ProductPrice 객체가 반환되어야 한다.")
    void testFrom_ZeroPrice_ReturnsProductPrice() {
        // Arrange
        BigDecimal price = BigDecimal.ZERO;

        // Act
        ProductPrice productPrice = ProductPrice.from(price);

        // Assert
        assertNotNull(productPrice);
        assertEquals(price, productPrice.getPrice());
    }

    @Test
    @DisplayName("가격이 0보다 클 때 ProductPrice 객체가 반환되어야 한다.")
    void testFrom_PositivePrice_ReturnsProductPrice() {
        // Arrange
        BigDecimal price = new BigDecimal("10.00");

        // Act
        ProductPrice productPrice = ProductPrice.from(price);

        // Assert
        assertNotNull(productPrice);
        assertEquals(price, productPrice.getPrice());
    }

    @Test
    @DisplayName("동일한 가격을 가진 두 ProductPrice 객체는 같아야 한다.")
    void testEquals_SamePrice_ReturnsTrue() {
        // Arrange
        BigDecimal price = new BigDecimal("10.00");
        ProductPrice productPrice1 = ProductPrice.from(price);
        ProductPrice productPrice2 = ProductPrice.from(price);

        // Act & Assert
        assertEquals(productPrice1, productPrice2);
        assertEquals(productPrice1.hashCode(), productPrice2.hashCode());
    }
}
