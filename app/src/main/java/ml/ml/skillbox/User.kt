package ml.ml.skillbox

import android.annotation.SuppressLint
import android.os.Message
import androidx.annotation.VisibleForTesting
import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.Security
import java.util.*

class User  private  constructor(
    private val firstName : String,
    private val lastName : String?,
    email : String? = null,
    rawPhone : String? = null,
    meta : Map <String, Any>? = null
){

    val userInfo : String
    private val fullName : String
        get() = listOfNotNull(firstName,lastName)
            .joinToString {" "}
            .capitalize()
    private val initials : String
        get() = listOfNotNull(firstName,lastName)
            .map{it.first().toUpperCase()}
            .joinToString {" "}

    private var phone : String? = null
        set(value){
            field = value?.replace("[^+\\d]".toRegex(),"")
        }
    private var logins: String? = null
    var login : String
        set(value){
          logins  = value?.toLowerCase()
        }
    get() = logins!!

    private val salt : String by lazy {
        ByteArray(16).also { SecureRandom().nextBytes(it) }.toString()
    }

    private lateinit var passwordHash : String

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    var accessCode: String? = null

    constructor(
        firstName : String,
        lastName: String?,
        email: String,
        password : String
    ) : this(firstName, lastName, email = email, meta = mapOf("auth" to "password")){
        println("Secondary mail constructor")
        passwordHash = encrypt(password)
    }



    @SuppressLint("RestrictedApi")
    constructor(
        firstName : String,
        lastName: String?,
        rawPhone: String?
    ) : this(firstName, lastName, rawPhone =  rawPhone, meta = mapOf("auth" to "sms")){
        println("Secondary phone constructor")
        val code = generateAccessCode()
        passwordHash = encrypt(code)
        accessCode = code
        if (rawPhone != null) {
            sendAccessCodeToUser(rawPhone,code)
        }

    }


    init {
        println("First init block, primary constructor was called")
          check(!firstName.isBlank()){"FirstName must  be not blank"}
          check(!email.isNullOrBlank() || rawPhone.isNullOrBlank() ){"Email or phone must  be not blank"}

        phone = rawPhone
        login = email ?: phone!!
        userInfo = """
            firstName = $firstName
            lastName = $lastName
            login = $login
            fullName = $fullName
            initials = $initials
            email = $email
            phone = $phone
            meta = $meta
        """.trimIndent()
    }

    fun checkPassword(pass : String) = encrypt(pass) == passwordHash

    fun changePassword(oldPass : String, newPass : String){
        if (checkPassword(oldPass)) passwordHash = encrypt(newPass)
        else throw IllegalArgumentException("Новый пароль не соотвествет старому паролю")
    }

    private fun encrypt(password: String): String = salt.plus(password).md5()



    private fun generateAccessCode(): String {
        val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZzyxwvutsrqponmlkjihgfedcba0123456789"
        return StringBuilder().apply {
            repeat(6){
                (possible.indices).random().also { index->
                    append(possible[index])
                }
            }
        }.toString()
    }

    private fun sendAccessCodeToUser(phone: String, code: String) {
        println("...... sending access code $code on $phone")
    }

    private fun String.md5() : String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(toByteArray())
        val hexString = BigInteger(1,digest).toString(16)
        return hexString.padStart(32,'0')
    }
    companion object Factory{
        fun makeUser(
            fullName : String,
            email: String? = null,
            password: String? = null,
            phone: String? = null
        ) : User{
            val (firstName,lastName) = fullName.fullNameToPair()
            return when{
                !phone.isNullOrBlank() -> User(firstName,lastName,phone)
                !email.isNullOrBlank() && !password.isNullOrBlank() -> User(firstName,lastName,email,password)
                else -> throw IllegalArgumentException("Email or phone must be not null or blank")
            }
        }

        private fun String.fullNameToPair() : Pair<String,String?>{
            return this.split(" ")
                .filter { it.isNotBlank() }
                .run{
                    when(size){
                        1 -> first() to null
                        2 -> first() to last()
                        else -> throw IllegalArgumentException("FullName must contain only first name and last name, current split result ${this@fullNameToPair} ")
                    }
                }

        }
    }

}