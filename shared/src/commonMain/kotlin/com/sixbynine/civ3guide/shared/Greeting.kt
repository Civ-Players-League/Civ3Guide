package com.sixbynine.civ3guide.shared


class Greeting {
    fun greeting(): String {
        MR.strings.lets_go
        return "Hello, ${Platform().platform}, really!"
    }
}
