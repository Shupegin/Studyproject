package studyproject.studyproject.Studyproject.domain

interface ShopListRepository {
    fun addShopItems(shopItem: ShopItem)
    fun deleteShopItems(shopItem: ShopItem)
    fun editShopItems(shopItem: ShopItem)
    fun getShopItemsIdCase(shopItem: Int) : ShopItem
    fun getShopUseCase(): List<ShopItem>

}