package kitchenpos.menu.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.menu.application.dto.MenuCreationRequest;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuGroupRepository;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;

@Service
public class MenuService {
	private static final String PRODUCT_NOT_FOUND = "상품을 찾을 수 없습니다";
	private static final String MENU_GROUP_NOT_FOUND_ERROR = "메뉴 그룹을 찾을 수 없습니다.";

	private final MenuRepository menuRepository;
	private final MenuGroupRepository menuGroupRepository;
	private final ProductRepository productRepository;
	private final PurgomalumClient purgomalumClient;

	public MenuService(
		final MenuRepository menuRepository,
		final MenuGroupRepository menuGroupRepository,
		final ProductRepository productRepository,
		final PurgomalumClient purgomalumClient
	) {
		this.menuRepository = menuRepository;
		this.menuGroupRepository = menuGroupRepository;
		this.productRepository = productRepository;
		this.purgomalumClient = purgomalumClient;
	}

	@Transactional
	public Menu create(final MenuCreationRequest request) {
		final MenuGroup menuGroup = menuGroupRepository.findById(request.menuGroupId())
			.orElseThrow(() -> new NoSuchElementException(MENU_GROUP_NOT_FOUND_ERROR));

		final Map<UUID, Product> products =
			productRepository.findAllByIdIn(new ArrayList<>(request.menuProductQuantities().keySet()))
				.stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));

		Menu menu = new Menu(
			request.name(),
			request.price(),
			menuGroup,
			buildMenuProducts(request, products),
			request.displayed(),
			purgomalumClient
		);

		return menuRepository.save(menu);
	}

	private List<MenuProduct> buildMenuProducts(MenuCreationRequest request, Map<UUID, Product> products) {
		return request.menuProductQuantities()
			.entrySet()
			.stream()
			.map(entry -> buildMenuProduct(entry, products))
			.toList();
	}

	private MenuProduct buildMenuProduct(Map.Entry<UUID, Long> productQuantityEntry, Map<UUID, Product> products) {
		UUID productId = productQuantityEntry.getKey();
		Product product = Optional.ofNullable(products.get(productId))
			.orElseThrow(() -> new NoSuchElementException(String.format("%s: %s", PRODUCT_NOT_FOUND, productId)));
		return new MenuProduct(product, productQuantityEntry.getValue());
	}

	@Transactional
	public Menu changePrice(final UUID menuId, final BigDecimal requestPrice) {
		final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
		menu.changePrice(requestPrice);
		return menu;
	}

	@Transactional
	public Menu display(final UUID menuId) {
		final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
		menu.display(true);
		return menu;
	}

	@Transactional
	public Menu hide(final UUID menuId) {
		final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
		menu.display(false);
		return menu;
	}

	@Transactional(readOnly = true)
	public List<Menu> findAll() {
		return menuRepository.findAll();
	}
}
