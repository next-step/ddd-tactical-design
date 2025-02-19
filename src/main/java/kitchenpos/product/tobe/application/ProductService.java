package kitchenpos.product.tobe.application;

import kitchenpos.common.PurgomalumClient;
import kitchenpos.menu.domain.MenuRepository;

import kitchenpos.product.tobe.Profanities;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final Profanities profanities;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final Profanities profanities
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanities = profanities;
    }

    @Transactional
    public Product create(final Product request) {
        return productRepository.save(request);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.setPrice(request.getPrice());
        /*
         * 메뉴가 있던 자리
         * 이때 메뉴를 어떻게 해야하지.. 궁금합니다.. ㅠㅠ
         * */

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
