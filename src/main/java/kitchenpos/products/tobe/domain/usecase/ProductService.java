package kitchenpos.products.tobe.domain.usecase;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.entity.ProductRepository;
import kitchenpos.products.tobe.web.dto.ProductResponseDto;
import kitchenpos.products.tobe.web.dto.ProductSaveRequestDto;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto create(final ProductSaveRequestDto requestDto) {
        Product created = productRepository.save(requestDto.toEntity());
        return new ProductResponseDto(created);
    }

    public List<ProductResponseDto> list() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }
}
