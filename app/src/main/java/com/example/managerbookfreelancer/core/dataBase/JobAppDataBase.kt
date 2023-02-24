package com.example.managerbookfreelancer.core.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.managerbookfreelancer.core.dataBase.dao.JobDAO
import com.example.managerbookfreelancer.core.dataBase.dao.ProfessionalDAO
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.core.entity.ProfessionalEntity


@Database(version = 1, entities = [ JobEntity::class, ProfessionalEntity::class])
abstract class JobAppDataBase : RoomDatabase(){

    abstract fun JobDAO() : JobDAO
    abstract  fun ProfessionalDAO(): ProfessionalDAO

    companion object{
        private const val DATABASE_NAME = "dataBase_Jobs"


        @Volatile
        private var INSTANCE: JobAppDataBase? = null

        fun getInstance(context: Context): JobAppDataBase {

            return if (INSTANCE != null){
                INSTANCE!!
        } else{
            synchronized(this){
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }
            }
    }

        private fun buildDataBase(context: Context): JobAppDataBase =
            Room.databaseBuilder(
                context,
                JobAppDataBase::class.java,
                DATABASE_NAME
            ).build()
    }

}