package studyproject.studyproject.Studyproject.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopUseCase(): List<ShopItem>{
        return shopListRepository.getShopUseCase()
    }


}