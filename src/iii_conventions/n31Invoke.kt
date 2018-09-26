package iii_conventions

import util.TODO


class Invokable {
    var numberOfInvocations = 0
        private set


    operator fun invoke(): Invokable {
        numberOfInvocations += 1
        return this
    }

}

fun todoTask31(): Nothing = TODO(
    """
        Task 31.
        Change the class 'Invokable' to count the number of invocations:
        for 'invokable()()()()' it should be 4.
    """,
    references = { invokable: Invokable -> })

fun task31(invokable: Invokable): Int {
    //todoTask31()
    return invokable()()()().numberOfInvocations
}
