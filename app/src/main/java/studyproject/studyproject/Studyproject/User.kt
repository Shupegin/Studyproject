package studyproject.studyproject.Studyproject

import android.provider.ContactsContract

open class User (
    private var firstName: String?,
    password: String,
    email: String
){
    private var listUser : MutableList<User> = ArrayList()
    fun registerHolder(user: User): String{
        var firstUser: User? = null
        for(item in listUser){
            firstUser = item
        }
        if(firstUser != user){
            listUser.add(user)
            return "Пользователя добавили"
        }else throw IllegalArgumentException("A user with this email already exists")
    }
}


