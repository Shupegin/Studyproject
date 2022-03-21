package studyproject.studyproject.Studyproject.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studyproject.studyproject.Studyproject.data.ShopListRepositoryImpl
import studyproject.studyproject.Studyproject.domain.DeleteShopItems
import studyproject.studyproject.Studyproject.domain.EditShopItems
import studyproject.studyproject.Studyproject.domain.GetShopListUseCase
import studyproject.studyproject.Studyproject.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItems = DeleteShopItems(repository)
    private val editShopItems = EditShopItems(repository)

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList (){
        val list = getShopListUseCase.getShopUseCase()
        shopList.value = list
    }
}