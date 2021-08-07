package test.dev.importantpeople.presentation

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    override fun equals(other: Any?): Boolean {
        return this.peekContent() == (other as? Event<*>)?.peekContent()
    }

    override fun toString(): String {
        return "Event($content)-handled : $hasBeenHandled"
    }

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

fun <T> T.toEvent(): Event<T> = Event(this)