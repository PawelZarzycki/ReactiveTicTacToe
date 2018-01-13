package io.bkraszewski.reactivetictactoe

import io.reactivex.Observable


typealias Events = Observable<Any>
typealias Reducer<T> = (events: Events) -> Observable<T>
