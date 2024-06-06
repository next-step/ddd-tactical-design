package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.domain.TobeProduct
import kitchenpos.products.tobe.domain.TobeProductRepository
import org.springframework.stereotype.Service

@Service
class TobeProductService(
    private val tobeProductRepository: TobeProductRepository,
) {
    fun create(request: TobeProduct): TobeProduct {
        return tobeProductRepository.save(request)
    }
}
