package studyproject.studyproject.Studyproject.domain

class AddShopItems( private val shopListRepository: ShopListRepository) {
    fun addShopItems(shopItem: ShopItem){
        shopListRepository.addShopItems(shopItem)

    }
}