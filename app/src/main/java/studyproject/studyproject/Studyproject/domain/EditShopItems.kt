package studyproject.studyproject.Studyproject.domain

class EditShopItems(private val shopListRepository: ShopListRepository) {
    fun editShopItems(shopItem: ShopItem){
        shopListRepository.editShopItems(shopItem)
    }
}