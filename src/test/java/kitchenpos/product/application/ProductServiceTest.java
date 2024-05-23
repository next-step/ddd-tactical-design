package kitchenpos.product.application;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menu.infra.InMemoryMenuRepository;
import kitchenpos.product.application.dto.ProductCreationRequest;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.infra.InMemoryProductRepository;

class ProductServiceTest {
	private ProductRepository productRepository;
	private MenuRepository menuRepository;
	private PurgomalumClient purgomalumClient;
	private ProductService productService;

	@BeforeEach
	void setUp() {
		productRepository = new InMemoryProductRepository();
		menuRepository = new InMemoryMenuRepository();
		purgomalumClient = new FakePurgomalumClient();
		productService = new ProductService(productRepository, menuRepository, purgomalumClient);
	}

	@DisplayName("상품을 등록할 수 있다.")
	@Test
	void create() {
		final ProductCreationRequest expected = createProductCreationRequest("후라이드", 16_000L);
		final Product actual = productService.create(expected);
		assertThat(actual).isNotNull();
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.name()),
			() -> assertThat(actual.getPrice()).isEqualTo(expected.price())
		);
	}

	@DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
	@ValueSource(strings = "-1000")
	@NullSource
	@ParameterizedTest
	void create(final BigDecimal price) {
		final ProductCreationRequest expected = createProductCreationRequest("후라이드", price);
		assertThatThrownBy(() -> productService.create(expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
	@ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
	@NullSource
	@ParameterizedTest
	void create(final String name) {
		final ProductCreationRequest expected = createProductCreationRequest(name, 16_000L);
		assertThatThrownBy(() -> productService.create(expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("상품의 가격을 변경할 수 있다.")
	@Test
	void changePrice() {
		final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
		final BigDecimal expected = BigDecimal.valueOf(15_000L);
		final Product actual = productService.changePrice(productId, expected);
		assertThat(actual.getPrice()).isEqualTo(expected);
	}

	@DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
	@ValueSource(strings = "-1000")
	@NullSource
	@ParameterizedTest
	void changePrice(final BigDecimal price) {
		Product product = product("후라이드", 16_000L);
		final UUID productId = productRepository.save(product).getId();
		assertThatThrownBy(() -> productService.changePrice(productId, price))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
	@Test
	void changePriceInMenu() {
		final Product product = productRepository.save(product("후라이드", 16_000L));
		final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
		productService.changePrice(product.getId(), BigDecimal.valueOf(8_000L));
		assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
	}

	@DisplayName("상품의 목록을 조회할 수 있다.")
	@Test
	void findAll() {
		productRepository.save(product("후라이드", 16_000L));
		productRepository.save(product("양념치킨", 16_000L));
		final List<Product> actual = productService.findAll();
		assertThat(actual).hasSize(2);
	}

	private ProductCreationRequest createProductCreationRequest(final String name, final long price) {
		return createProductCreationRequest(name, BigDecimal.valueOf(price));
	}

	private ProductCreationRequest createProductCreationRequest(final String name, final BigDecimal price) {
		return new ProductCreationRequest(name, price);
	}
}
