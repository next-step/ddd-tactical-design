package kitchenpos.products.domain

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository
) : ProductRepository {
    override fun save(product: Product): Product {
        return productJpaRepository.save(product.toRecord()).toProduct()
    }

    override fun findById(id: UUID?): Optional<Product> {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<Product> {
        TODO("Not yet implemented")
    }

    override fun findAllByIdIn(ids: MutableList<UUID>?): MutableList<Product> {
        TODO("Not yet implemented")
    }
}

fun Product.toRecord(): ProductRecord {
    return ProductRecord(
        id,
        getName(),
        getPrice(),
    )
}

fun ProductRecord.toProduct(): Product {
    return Product(
        id = id,
        name = ProductName.create(name),
        price = ProductPrice.create(price)
    )
}
