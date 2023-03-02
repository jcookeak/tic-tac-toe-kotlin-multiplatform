package dev.jcookeak.tic.tac.toe.test

object GivenScope {
    fun When(block: WhenScope.() -> Unit) = with(WhenScope) { block() }
}

object WhenScope {
    fun Then(block: ThenScope.() -> Unit) = with(ThenScope) { block() }
}

object ThenScope

fun Given(block: GivenScope.() -> Unit) = with(GivenScope) { block() }
fun When(block: WhenScope.() -> Unit) = with(GivenScope) { When(block) }