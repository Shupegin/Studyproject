package studyproject.studyproject.Studyproject.domain

class DeleteShopItems (private val shopListRepository: ShopListRepository) {
    fun deleteShopItems(shopItem: ShopItem){
        shopListRepository.deleteShopItems(shopItem)
    }
}