package com.example.androidsprint.di

interface Factory<T> {
    fun create(): T
}