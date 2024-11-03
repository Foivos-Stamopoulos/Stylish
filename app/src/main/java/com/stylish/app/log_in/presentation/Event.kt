package com.stylish.app.log_in.presentation

sealed class Event {
    data object LoginClick: Event()
}