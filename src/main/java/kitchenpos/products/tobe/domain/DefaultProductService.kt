package kitchenpos.products.tobe.domain

import kitchenpos.menus.domain.MenuRepository
import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import kitchenpos.products.infra.PurgomalumClient
import kitchenpos.products.tobe.application.CreateProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@Service
class DefaultProductService(
    private val productRepository: ProductRepository,
    private val menuRepository: MenuRepository,
    private val purgomalumClient: PurgomalumClient,
    private val createProductService: CreateProductService,
) {
    fun create(request: Product): Product {
        val product = createProductService.createProduct(
            price = request.price,
            name = request.name,
        )

        return productRepository.save(product)
    }
}
