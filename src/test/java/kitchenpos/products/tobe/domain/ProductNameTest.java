package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.exception.ProductNameRequiredException;
import kitchenpos.products.tobe.exception.ProfanityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = Mockito.mock(PurgomalumClient.class);
    }

    @Test
    @DisplayName("상품명이 null일 때 ProductNameRequiredException이 발생해야 한다.")
    void testFrom_NullName_ThrowsProductNameRequiredException() {
        // Arrange
        String name = null;

        // Act & Assert
        assertThrows(ProductNameRequiredException.class, () -> {
            ProductName.from(name, purgomalumClient);
        });
    }

    @Test
    @DisplayName("상품명이 비어 있을 때 ProductNameRequiredException이 발생해야 한다.")
    void testFrom_EmptyName_ThrowsProductNameRequiredException() {
        // Arrange
        String name = "";

        // Act & Assert
        assertThrows(ProductNameRequiredException.class, () -> {
            ProductName.from(name, purgomalumClient);
        });
    }

    @Test
    @DisplayName("상품명에 비속어가 포함되어 있을 때 ProfanityException이 발생해야 한다.")
    void testFrom_NameWithProfanity_ThrowsProfanityException() {
        // Arrange
        String name = "badword";
        Mockito.when(purgomalumClient.containsProfanity(name)).thenReturn(true);

        // Act & Assert
        assertThrows(ProfanityException.class, () -> {
            ProductName.from(name, purgomalumClient);
        });
    }

    @Test
    @DisplayName("유효한 상품명이 주어졌을 때 ProductName 객체가 반환되어야 한다.")
    void testFrom_ValidName_ReturnsProductName() {
        // Arrange
        String name = "Valid Product";
        Mockito.when(purgomalumClient.containsProfanity(name)).thenReturn(false);

        // Act
        ProductName productName = ProductName.from(name, purgomalumClient);

        // Assert
        assertNotNull(productName);
        assertEquals(name, productName.getName());
    }
}
