package com.dicoding.popularband.data.repository

import com.dicoding.popularband.data.model.Band
import com.dicoding.popularband.data.model.BandData
import com.dicoding.popularband.data.model.Profile
import com.dicoding.popularband.data.model.ProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository {

    private val listBand = mutableListOf<Band>()

    init {
        if (listBand.isEmpty()){
            BandData.band.forEach {
                listBand.add(it)
            }
        }
    }

    fun getAllBand(): Flow<List<Band>> {
        return flowOf(listBand)
    }

    fun getBandById(id: Long): Flow<Band> {
        return flowOf( listBand.first {
            it.id == id
        })
    }

    fun getProfile(): Flow<Profile> {
        return flowOf(ProfileData.profile)
    }



    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}