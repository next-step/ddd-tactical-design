package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.ui.ProductForm;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TobeProductService {
    private final TobeProductRepository productRepository;
    private final TobePurgomalumClient purgomalumClient;

    public TobeProductService(
        final TobeProductRepository productRepository,
        final TobePurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductForm create(final ProductForm request) {
        ProductName name = new ProductName(request.getName(), this.purgomalumClient);
        ProductPrice price = new ProductPrice(request.getPrice());
        TobeProduct saveProduct = productRepository.save(new TobeProduct(name, price));
        return ProductForm.of(saveProduct);
    }

    @Transactional
    public ProductForm changePrice(final UUID productId, final ProductForm request) {
        final TobeProduct product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);

        product.changePrice(request.getPrice());

        return ProductForm.of(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public List<ProductForm> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductForm::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductForm findById(UUID id) {
        return ProductForm.of(productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Transactional(readOnly = true)
    public List<ProductForm> findAllByIdIn(List<UUID> ids) {
        return productRepository.findAllByIdIn(ids)
                .stream()
                .map(ProductForm::of)
                .collect(Collectors.toList());
    }
}
