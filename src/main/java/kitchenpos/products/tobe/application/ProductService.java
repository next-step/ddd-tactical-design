package kitchenpos.products.tobe.application;

import java.util.List;
import kitchenpos.global.domain.client.ProfanityCheckClient;
import kitchenpos.products.tobe.application.dto.ProductCommand;
import kitchenpos.products.tobe.application.dto.ProductInfo;
import kitchenpos.products.tobe.application.dto.ProductInfo.ChangePrice;
import kitchenpos.products.tobe.application.dto.ProductInfo.Create;
import kitchenpos.products.tobe.domain.model.entity.Product;
import kitchenpos.products.tobe.domain.model.vo.ProductName;
import kitchenpos.products.tobe.domain.model.vo.ProductPrice;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class ProductService {

    private final ProductRepository productRepository;

    private final ProfanityCheckClient profanityCheckClient;

    public ProductService(
        ProductRepository productRepository,
        ProfanityCheckClient profanityCheckClient
    ) {
        this.productRepository = productRepository;
        this.profanityCheckClient = profanityCheckClient;
    }

    public ProductInfo.Create create(ProductCommand.Create request) {
        var name = new ProductName(request.getName(), profanityCheckClient);
        var price = new ProductPrice(request.getPrice());

        Product product = productRepository.save(new Product(name, price));

        return new Create(product);
    }

    public ProductInfo.ChangePrice changePrice(ProductCommand.ChangePrice request) {
        Product product = productRepository.getById(request.getId());
        product.changePrice(new ProductPrice(request.getPrice()));

        // TODO: 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.

        return new ChangePrice(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
