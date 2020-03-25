package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.infra.ProductEntity;
import kitchenpos.products.tobe.dto.ProductDto;
import kitchenpos.products.tobe.exception.ProductDuplicationException;
import kitchenpos.products.tobe.infra.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository repository;

    public DefaultProductService(final ProductRepository repository){
        this.repository = repository;
    }

    @Transactional
    @Override
    public ProductDto register(ProductDto dto){
        validateRegisteredProduct(dto.getName());

        ProductEntity productEntity = new ProductEntity.Builder()
            .id(dto.getId())
            .name(dto.getName())
            .price(dto.getPrice())
            .build();

        ProductEntity savedProductEntity = repository.save(productEntity);

        return new ProductDto(savedProductEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> list(){
        return repository.list()
            .stream()
            .map(product -> new ProductDto(product))
            .collect(Collectors.toList());
    }

    protected void validateRegisteredProduct (final String name){
        if(repository.findByName(name)){
            throw new ProductDuplicationException("동일한 상품이 이미 등록 되었습니다.");
        }
    }

}
