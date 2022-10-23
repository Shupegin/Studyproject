package studyproject.studyproject.Studyproject

object UserHolder {
    fun start(){
        val neo = User("Дмитрий","123456","Ten@mail.ru")
        val  value = neo.registerHolder(neo)
    }
}