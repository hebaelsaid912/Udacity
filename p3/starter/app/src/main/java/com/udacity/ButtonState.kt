package com.udacity


sealed class ButtonState {
    //object Idel : ButtonState()
    object Clicked : ButtonState()
    object Loading : ButtonState()
    object Completed : ButtonState()
}