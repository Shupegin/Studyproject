package studyproject.studyproject.Studyproject.domain

data class ShopItem(
    val name : String,
    val count : Int,
    val enabled : Boolean,
    var id : Int = UNDEFINDID
)

{
    companion object {
        const val UNDEFINDID = -1
    }
}
