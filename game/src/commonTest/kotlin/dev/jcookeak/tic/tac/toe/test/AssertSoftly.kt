package dev.jcookeak.tic.tac.toe.test

inline fun <reified T: Any> T.assertSoftly(block: T.() -> Unit) = io.kotest.assertions.assertSoftly(this) {block()}