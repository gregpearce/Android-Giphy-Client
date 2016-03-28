package gregpearce.gifhub.rx

import android.util.Log
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

/**
 * Always subscribe on IO Thread and observe on UI Thread, prevent blocking UI Thread
 */
inline fun <T> Observable<T>.applySchedulers() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/**
 *  Assert that some condition is true or throw a runtime exception
 */
fun <T> Observable<T>.assert(condition: (T) -> Boolean, message: (T) -> String) =
        map {
            // assert that condition is true
            if (!condition(it)) {
                throw RuntimeException(message(it))
            }
            // pass along the value
            it
        }

/**
 *  For inserting temporary log statements into a stream to help with debugging
 */
fun <T> Observable<T>.debug(getMessage: (T) -> String) =
        map {
            Log.d("---DEBUG LOG---", getMessage(it))
            it
        }


/**
 * For debug log statements that you intend to leave inside the app (won't be logged in production builds)
 */
fun <T> Observable<T>.timberd(getMessage: (T) -> String) =
        map {
            Timber.d(getMessage(it))
            it
        }