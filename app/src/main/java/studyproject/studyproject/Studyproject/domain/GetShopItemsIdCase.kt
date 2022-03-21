package studyproject.studyproject.Studyproject.domain

class GetShopItemsIdCase(private val shopListRepository: ShopListRepository) {
    fun getShopItemsIdCase(shopItemID: Int) : ShopItem{
        return shopListRepository.getShopItemsIdCase(shopItemID)
    }
}