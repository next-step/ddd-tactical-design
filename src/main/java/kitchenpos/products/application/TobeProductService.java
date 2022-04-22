package kitchenpos.products.application;

import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.products.domain.tobe.domain.ProductPriceChangeService;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.TobeProductRepository;
import kitchenpos.products.domain.tobe.domain.vo.ProductName;
import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;
import kitchenpos.products.dto.*;
import kitchenpos.support.infra.profanity.Profanity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TobeProductService {
    private final TobeProductRepository productRepository;
    private final TobeMenuRepository menuRepository;
    private final Profanity profanity;
    private final ProductPriceChangeService priceChangeService;

    public TobeProductService(final TobeProductRepository productRepository, final TobeMenuRepository menuRepository, final Profanity profanity, final ProductPriceChangeService priceChangeService) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanity = profanity;
        this.priceChangeService = priceChangeService;
    }

    @Transactional
    public ProductRegisterResponse create(final ProductRegisterRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeProduct tobeProduct = new TobeProduct.Builder()
                .name(new ProductName(request.getName(), profanity))
                .price(new ProductPrice(request.getPrice()))
                .build();
        return new ProductRegisterResponse(productRepository.save(tobeProduct));
    }

    @Transactional
    public ProductPriceChangeResponse changePrice(final ProductPriceChangeRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        return priceChangeService.priceChange(request);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
