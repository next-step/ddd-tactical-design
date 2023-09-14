package kitchenpos.product.application;

import static kitchenpos.product.support.constant.Name.ID;
import static kitchenpos.product.support.constant.Name.PRICE;
import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.product.application.exception.NotExistProductException;
import kitchenpos.product.application.port.in.ProductPriceChangeUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.domain.ProductPrice;
import org.springframework.transaction.annotation.Transactional;

public class DefaultProductPriceChangeUseCase implements ProductPriceChangeUseCase {

    private final ProductNewRepository repository;
    private final ProductPriceChangeEventPublisher eventPublisher;

    public DefaultProductPriceChangeUseCase(final ProductNewRepository repository,
        final ProductPriceChangeEventPublisher eventPublisher) {

        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void change(final UUID id, final ProductPrice price) {
        checkNotNull(id, ID);
        checkNotNull(price, PRICE);

        final ProductNew product = repository.findById(id)
            .orElseThrow(() -> new NotExistProductException(id));

        product.changePrice(price);

        eventPublisher.publish(id);
    }
}
