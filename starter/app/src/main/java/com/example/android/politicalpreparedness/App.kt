package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.database.ElectionsDataSource
import com.example.android.politicalpreparedness.database.ElectionsRepository
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()


        /**
         * Use koin library as a service locator
         */


        val myModule = module {
            // Declare singleton definitions to be later injected using by inject()
            single {
                ElectionsViewModel(get())
            }
            single {
                VoterInfoViewModel(get())
            }
            //TODO: Add a repository
            single {
                ElectionsRepository(get())
            }
            single {
                ElectionDatabase.getInstance(this@App)
            }
            single {
                ElectionDatabase.getInstance(this@App).electionDao
            }
        }

        startKoin {
            androidContext(this@App)
            modules(listOf(myModule))
        }
    }
    }

