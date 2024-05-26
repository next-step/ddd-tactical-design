package kitchenpos.products.tobe.application

import kitchenpos.menus.tode.application.PriceChangeDrivenMenuUpdater
import kitchenpos.products.application.ProductService
import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import kitchenpos.products.tobe.domain.CreateProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service("ProductService")
class DefaultProductService(
    private val productRepository: ProductRepository,
    private val createProductService: CreateProductService,
    private val changeProductPriceService: ChangeProductPriceService,
    private val priceChangeDrivenMenuUpdater: PriceChangeDrivenMenuUpdater,
): ProductService {
    override fun create(request: Product): Product {
        val product = createProductService.createProduct(
            price = request.price,
            name = request.name,
        )

        return productRepository.save(product)
    }

    override fun changePrice(
        productId: UUID,
        request: Product,
    ): Product {
        val product = changeProductPriceService.changeProductPrice(
            productId = productId,
            price = request.price,
        )

        priceChangeDrivenMenuUpdater.menuDisplayUpdateByProductId(productId)

        return product
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<Product> = productRepository.findAll()
}
