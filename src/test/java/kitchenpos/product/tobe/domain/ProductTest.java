package kitchenpos.product.tobe.domain;

import kitchenpos.product.tobe.Profanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    private final Profanities profanities = text -> text.equals("바보"); // 테스트용 비속어 설정

    @Nested
    @DisplayName("상품 생성")
    class CreateProduct {
        @Test
        @DisplayName("상품을 생성한다")
        void create() {
            // when
            Product product = new Product("후라이드", 16000, profanities);

            // then
            assertAll(
                    () -> assertThat(product.getId()).isNotNull(),
                    () -> assertThat(product.getName()).isEqualTo(new ProductName("후라이드", profanities)),
                    () -> assertThat(product.getPrice()).isEqualTo(new ProductPrice(16000L))
            );
        }

        @Test
        @DisplayName("상품명에 비속어가 포함되어 있으면 생성에 실패한다")
        void createFailWithProfanity() {
            assertThatThrownBy(() -> new Product("바보", 16000, profanities))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비속어가 포함되어 있습니다.");
        }

        @Test
        @DisplayName("상품명이 null이거나 빈 문자열이면 생성에 실패한다")
        void createFailWithInvalidName() {
            assertAll(
                    () -> assertThatThrownBy(() -> new Product(null, 16000, profanities))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("상품명은 필수값입니다."),
                    () -> assertThatThrownBy(() -> new Product("", 16000, profanities))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("상품명은 필수값입니다.")
            );
        }

        @ParameterizedTest
        @ValueSource(longs = {-1000, 0})
        @DisplayName("상품 가격이 0 이하면 생성에 실패한다")
        void createFailWithInvalidPrice(long invalidPrice) {
            assertThatThrownBy(() -> new Product("후라이드", invalidPrice, profanities))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("상품 가격은 0보다 작을 수 없습니다.");
        }
    }


    @Nested
    @DisplayName("상품 수정")
    class UpdateProduct {
        @Test
        @DisplayName("상품의 이름을 변경한다")
        void updateName() {
            // given
            Product product = new Product();
            ReflectionTestUtils.setField(product, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(product, "name", new ProductName("후라이드", profanities));
            ReflectionTestUtils.setField(product, "price", new ProductPrice(16000L));
            ProductName newName = new ProductName("양념치킨", profanities);

            // when
            ReflectionTestUtils.setField(product, "name", newName);

            // then
            assertThat(product.getName()).isEqualTo(newName);
        }

        @Test
        @DisplayName("상품의 가격을 변경한다")
        void updatePrice() {
            // given
            Product product = new Product();
            ReflectionTestUtils.setField(product, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(product, "name", new ProductName("후라이드", profanities));
            ReflectionTestUtils.setField(product, "price", new ProductPrice(16000L));
            ProductPrice newPrice = new ProductPrice(18000L);

            // when
            ReflectionTestUtils.setField(product, "price", newPrice);

            // then
            assertThat(product.getPrice()).isEqualTo(newPrice);
        }
    }
}