package com.gregkluska.minesweeper

fun MutableSet<Int>.toggle(value: Int) {
    if(this.indexOf(value)>-1) {
        // value exists, remove it
        this.remove(value)
    } else {
        this.add(value)
    }
}