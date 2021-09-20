package kitchenpos.products.tobe.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.Quantity;
import kitchenpos.menus.tobe.domain.menu.fixture.MenuFixture;
import kitchenpos.menus.tobe.infra.menu.InMemoryMenuRepository;
import kitchenpos.products.tobe.domain.fixture.ProductFixture;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.domain.Profanities;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import kitchenpos.products.tobe.ui.ProductPriceChangeRequest;

public class ProductServiceTest {
	private ProductRepository productRepository;
	private Profanities profanities;
	private ProductService productService;
	private MenuRepository menuRepository;

	@BeforeEach
	void setUp() {
		productRepository = new InMemoryProductRepository();
		profanities = text -> Stream.of("비속어", "욕설").anyMatch(text::contains);
		menuRepository = new InMemoryMenuRepository();
		productService = new ProductService(productRepository, profanities, menuRepository);
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
		Product given = ProductFixture.상품("후라이드", 16_000L);
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
		Product given = ProductFixture.상품("후라이드", 16_000L);
		UUID givenId = productRepository.save(given).getId();
		ProductPriceChangeRequest request = new ProductPriceChangeRequest(price);

		// when & then
		assertThatThrownBy(() -> productService.changePrice(givenId, request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 상품의_가격이_변경될_때_메뉴의_가격이_메뉴에_속한_상품_금액의_합보다_크면_메뉴가_숨겨진다() {
		// given
		Product givenProduct = ProductFixture.상품("후라이드", 16_000L);
		productRepository.save(givenProduct);

		Menu givenMenu = MenuFixture.전시_메뉴(
			new Price(new BigDecimal(15_000L)),
			new MenuProducts(Collections.singletonList(new MenuProduct(givenProduct, new Quantity(2L)))));
		menuRepository.save(givenMenu);

		ProductPriceChangeRequest request = new ProductPriceChangeRequest(100L);

		// when
		Product product = productService.changePrice(givenProduct.getId(), request);
		Menu menu = menuRepository.findById(givenMenu.getId()).get();

		// then
		assertThat(product.getPrice()).isEqualTo(new Price(new BigDecimal(100L)));
		assertThat(menu.isDisplayed()).isFalse();
	}

	@Test
	void 상품의_목록을_조회할_수_있다() {
		// given
		productRepository.save(ProductFixture.상품("후라이드", 16_000L));
		productRepository.save(ProductFixture.상품("양념치킨", 16_000L));

		// when
		List<Product> actual = productService.findAll();

		// then
		assertThat(actual).hasSize(2);
	}
}
