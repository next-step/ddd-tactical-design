package kitchenpos.products.tobe.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.TobeFixtures;
import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.Profanities;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import kitchenpos.products.tobe.ui.ProductPriceChangeRequest;

public class ProductServiceTest {
	private ProductRepository productRepository;
	private Profanities profanities;
	private ProductService productService;

	@BeforeEach
	void setUp() {
		productRepository = new InMemoryProductRepository();
		profanities = text -> Stream.of("비속어", "욕설").anyMatch(text::contains);
		productService = new ProductService(productRepository, profanities);
	}

	@Test
	void 상품을_등록할_수_있다() {
		// given
		ProductCreateRequest request = new ProductCreateRequest("후라이드", 16_000L);

		// when
		Product actual = productService.create(request);

		// then
		assertThat(actual).isNotNull();
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(new DisplayedName(request.getName(), profanities)),
			() -> assertThat(actual.getPrice()).isEqualTo(new Price(new BigDecimal(request.getPrice())))
		);
	}

	@ParameterizedTest
	@ValueSource(strings = "-1000")
	void 상품의_가격이_올바르지_않으면_등록할_수_없다(long price) {
		// given
		ProductCreateRequest request = new ProductCreateRequest("후라이드", price);

		// when & then
		assertThatThrownBy(() -> productService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
	@NullSource
	void 상품의_이름이_올바르지_않으면_등록할_수_없다(String name) {
		// given
		ProductCreateRequest request = new ProductCreateRequest(name, 16_000L);

		// when & then
		assertThatThrownBy(() -> productService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 상품의_가격을_변경할_수_있다() {
		// given
		Product given = TobeFixtures.product("후라이드", 16_000L);
		UUID givenId = productRepository.save(given).getId();
		ProductPriceChangeRequest request = new ProductPriceChangeRequest(15_000L);

		// when
		Product actual = productService.changePrice(givenId, request);

		// then
		assertThat(actual.getPrice()).isEqualTo(new Price(new BigDecimal(request.getPrice())));
	}

	@ParameterizedTest
	@ValueSource(strings = "-1000")
	void 상품의_가격이_올바르지_않으면_변경할_수_없다(long price) {
		// given
		Product given = TobeFixtures.product("후라이드", 16_000L);
		UUID givenId = productRepository.save(given).getId();
		ProductPriceChangeRequest request = new ProductPriceChangeRequest(price);

		// when & then
		assertThatThrownBy(() -> productService.changePrice(givenId, request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	// TODO : 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.

	@Test
	void 상품의_목록을_조회할_수_있다() {
		// given
		productRepository.save(TobeFixtures.product("후라이드", 16_000L));
		productRepository.save(TobeFixtures.product("양념치킨", 16_000L));

		// when
		List<Product> actual = productService.findAll();

		// then
		assertThat(actual).hasSize(2);
	}
}
