package kitchenpos.products.application;

import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.TobeProductRepository;
import kitchenpos.products.dto.ProductPriceChangeRequest;
import kitchenpos.products.dto.ProductRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class TobeProductService {
    private final TobeProductRepository productRepository;
    private final MenuRepository menuRepository;

    public TobeProductService(final TobeProductRepository productRepository, final MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public TobeProduct create(final ProductRegisterRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeProduct tobeProduct = new TobeProduct.Builder()
                .name(request.getName())
                .namingRule(request.getProductNamingRule())
                .price(request.getPrice())
                .pricingRule(request.getProductPricingRule()).build();
        return productRepository.save(tobeProduct);
    }

    @Transactional
    public TobeProduct changePrice(final ProductPriceChangeRequest request) {
        //TODO: 추후 도메인 서비스를 도입하여 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeProduct tobeProduct = productRepository.findById(request.getProductId())
                .orElseThrow(NoSuchElementException::new);
        return tobeProduct
                .changePrice(request.getPrice(), request.getProductPricingRule());
    }

    @Transactional(readOnly = true)
    public List<TobeProduct> findAll() {
        return productRepository.findAll();
    }
}
