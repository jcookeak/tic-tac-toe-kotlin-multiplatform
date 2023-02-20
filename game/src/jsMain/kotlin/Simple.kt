@file:OptIn(ExperimentalJsExport::class)

package game

@JsExport
class KotlinGreeter(private val who: String) {
    fun greet() = "Hello, $who!"
}

@JsExport
fun farewell(who: String) = "Bye, $who!"

fun secretGreeting(who: String) = "Sup, $who!" // only from Kotlin!

@JsExport
fun greet(): String = "hello, Jonathon"

@JsExport
sealed class Outcome<S, F>() {
    data class Success<S>(val value: S) : Outcome<S, Nothing>()
    data class Failure<F>(val reason: F) : Outcome<Nothing, F>()
}

@JsExport
fun <S, F, U> Outcome<S, F>.fold(onSuccess: S.() -> U, onFailure: F.() -> U): U = when (this) {
    is Outcome.Success -> value.onSuccess()
    is Outcome.Failure -> reason.onFailure()
}



