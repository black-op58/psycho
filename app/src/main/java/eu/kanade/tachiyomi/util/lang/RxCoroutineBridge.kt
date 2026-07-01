package eu.kanade.tachiyomi.util.lang
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import rx.Observable
import rx.Subscriber
import rx.Subscription
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException/* * Util functions for bridging RxJava and coroutines. Taken from TachiyomiEH/SY. */suspend 
fun <T> Observable<T>.awaitSingle(): T = single().awaitOne()
@OptIn(InternalCoroutinesApi::class)
private suspend 
fun <T> Observable<T>.awaitOne(): T = suspendCancellableCoroutine { 
        c
object : Subscriber<T>() {
    override fun onStart() {                    
        r

override fun onNext(t: T) {                    
        c

override fun onCompleted() {
if (cont.isActive) {
        cont.resumeWithException(                            IllegalStateException(                                "Should have invoked onNext",                            ),                        )
                    }
}

override fun onError(e: Throwable) {                    
        /
val token = cont.tryResumeWithException(e)
if (token != null) {
        cont.completeResume(token)                    }}
},        ),    )
 }
private fun <T> CancellableContinuation<T>.unsubscribeOnCancellation(sub: Subscription) =    invokeOnCancellation { 
        s