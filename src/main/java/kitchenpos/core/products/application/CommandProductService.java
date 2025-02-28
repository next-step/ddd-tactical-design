package kitchenpos.core.products.application;

import kitchenpos.core.menus.domain.Menu;
import kitchenpos.core.menus.domain.MenuRepository;
import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.products.tobe.domain.Product;
import kitchenpos.core.products.tobe.domain.ProductPrice;
import kitchenpos.core.products.tobe.domain.TobeProductRepository;
import kitchenpos.core.products.tobe.domain.exception.ProductNotFoundException;
import kitchenpos.core.shared.identifier.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommandProductService implements AddProduct, ChangeProductPrice {
    private final TobeProductRepository tobeProductRepository;
    private final MenuRepository menuRepository;

    public CommandProductService(
            final TobeProductRepository tobeProductRepository,
            final MenuRepository menuRepository
    ) {
        this.tobeProductRepository = tobeProductRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public kitchenpos.core.products.tobe.domain.Product addProduct(final CreateProductRequest request) {
        return tobeProductRepository.save(kitchenpos.core.products.tobe.domain.Product.create(
                request.id(),
                request.name(),
                request.price()
        ));
    }

    @Transactional
    public Product changePrice(final ProductId productId, final ProductPrice request) {

        Product product = tobeProductRepository.findById(productId)
                .map(p -> p.changePrice(request))
                .orElseThrow(() -> new ProductNotFoundException(productId));

        menuRepository.findAllByProductId(productId)
                .forEach(Menu::recalculateDisplayStatus);
        return product;
    }


}
