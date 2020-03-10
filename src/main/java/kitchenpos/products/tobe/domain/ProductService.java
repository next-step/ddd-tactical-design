package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.dto.ProductDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProduct(final long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductDto(product);
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public ProductDto create(ProductDto productDto) {
        Product product = productRepository.save(productDto.toEntity());
        return new ProductDto(product);
    }
}
