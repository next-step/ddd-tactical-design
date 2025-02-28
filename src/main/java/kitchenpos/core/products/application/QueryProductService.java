package kitchenpos.core.products.application;

import kitchenpos.core.products.tobe.domain.Product;
import kitchenpos.core.products.tobe.domain.TobeProductRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class QueryProductService implements FindProducts {

    private final TobeProductRepository tobeProductRepository;

    public QueryProductService(final TobeProductRepository tobeProductRepository) {
        this.tobeProductRepository = tobeProductRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findProducts() {
        return tobeProductRepository.findAll();
    }
}
