package com.dicoding.popularband.di

import com.dicoding.popularband.data.repository.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}