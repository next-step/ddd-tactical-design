package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.dto.ProductDto;
import kitchenpos.products.tobe.dto.ProductRegisterDto;
import kitchenpos.products.tobe.exception.ProductDuplicationException;
import kitchenpos.products.tobe.infra.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;

    public ProductService(final ProductRepository repository){
        this.repository = repository;
    }

    @Transactional()
    public ProductRegisterDto register(ProductRegisterDto dto){
        validateRegisteredProduct(dto.getName());

        Product product = repository.save(new Product(
                dto.getName(),
                dto.getPrice())
            );

        return new ProductRegisterDto(product);
    }

    public List<ProductDto> list(){
        return repository.findAll()
            .stream()
            .map(product -> new ProductDto(product))
            .collect(Collectors.toList());
    }

    private void validateRegisteredProduct (final String name){
        if(repository.findByNameContaining(name)){
            throw new ProductDuplicationException("동일한 상품이 이미 등록 되었습니다.");
        }
    }

}
