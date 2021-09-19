package kitchenpos.menus.tobe.application.menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.Quantity;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.tobe.ui.menu.MenuCreateRequest;
import kitchenpos.menus.tobe.ui.menu.MenuPriceChangeRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

@Service(value = "TobeMenuService")
public class MenuService {
	private final MenuGroupRepository menuGroupRepository;
	private final ProductRepository productRepository;
	private final Profanities profanities;
	private final MenuRepository menuRepository;

	public MenuService(
		MenuGroupRepository menuGroupRepository,
		ProductRepository productRepository,
		Profanities profanities,
		MenuRepository menuRepository
	) {
		this.menuGroupRepository = menuGroupRepository;
		this.productRepository = productRepository;
		this.profanities = profanities;
		this.menuRepository = menuRepository;
	}

	@Transactional
	public Menu create(MenuCreateRequest request) {
		DisplayedName name = new DisplayedName(request.getName(), profanities);
		Price price = new Price(new BigDecimal(request.getPrice()));
		boolean displayed = request.getDisplayed();

		MenuProducts menuProducts = new MenuProducts(
			request.getMenuProducts().stream().map(
				dto -> {
					Product product = productRepository.findById(dto.getProductId())
						.orElseThrow(IllegalArgumentException::new);
					Quantity quantity = new Quantity(dto.getQuantity());
					return new MenuProduct(product, quantity);
				}
			).collect(Collectors.toList()));

		MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
			.orElseThrow(NoSuchElementException::new);

		Menu menu = new Menu(name, price, displayed, menuProducts, menuGroup);

		return menuRepository.save(menu);
	}

	@Transactional
	public Menu changePrice(UUID menuId, MenuPriceChangeRequest request) {
		Price price = new Price(new BigDecimal(request.getPrice()));
		Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
		menu.changePrice(price);
		return menu;
	}

	@Transactional
	public Menu display(UUID menuId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
		menu.display();
		return menu;
	}

	@Transactional
	public Menu hide(UUID menuId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
		menu.hide();
		return menu;
	}

	@Transactional(readOnly = true)
	public List<Menu> findAll() {
		return menuRepository.findAll();
	}
}
