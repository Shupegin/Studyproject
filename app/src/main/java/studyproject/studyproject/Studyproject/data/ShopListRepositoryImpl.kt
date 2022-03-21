package studyproject.studyproject.Studyproject.data

import studyproject.studyproject.Studyproject.domain.ShopItem
import studyproject.studyproject.Studyproject.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private  var autoIncrementID = 0

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name $i",i,true)
            addShopItems(item)
        }
    }
    override fun addShopItems(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINDID) {
            shopItem.id = autoIncrementID++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItems(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItems(shopItem: ShopItem) {
        val oldElement = getShopItemsIdCase(shopItem.id)
        shopList.remove(oldElement)
        addShopItems(shopItem)
    }

    override fun getShopItemsIdCase(shopItemID: Int): ShopItem {
        return shopList.find { it.id == shopItemID } ?: throw RuntimeException("Element with id $shopItemID not found")

    }

    override fun getShopUseCase(): List<ShopItem> {
        return shopList.toList()
    }
}