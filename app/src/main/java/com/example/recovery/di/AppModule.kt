package com.example.recovery.di

import android.content.Context
import android.util.Log
import com.example.recovery.data.local.LocalDs
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.domain.repository.DetailRepoInterface
import com.example.recovery.domain.repository.FavouriteRepoInterface
import com.example.recovery.domain.repository.HomeRepoInterface
import com.example.recovery.domain.repository.PopularRepoInterface
import com.example.recovery.domain.repository.SearchRepoInterface
import com.example.recovery.domain.repository.TopRatedRepoInterface
import com.example.recovery.domain.repository.UpComingRepoInterface
import com.example.recovery.ui.detail.repo.DetailRepo
import com.example.recovery.ui.detail.viewmodel.DetailViewModel
import com.example.recovery.ui.favourite.repo.FavouriteRepo
import com.example.recovery.ui.home.repo.HomeRepo
import com.example.recovery.ui.popular.repo.PopularRepo
import com.example.recovery.ui.search.repo.SearchRepo
import com.example.recovery.ui.toprated.repo.TopRatedRepo
import com.example.recovery.ui.toprated.viewmodel.TopRatedViewModel
import com.example.recovery.ui.upcoming.repo.UpComingRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDs(@ApplicationContext context: Context): LocalDs {
        return LocalDs(context)
    }

    @Provides
    @Singleton
    fun provideDetailRepo(localDs: LocalDs): DetailRepoInterface {
        return DetailRepo(ApiClient, localDs)
    }

    @Provides
    @Singleton
    fun provideFavouriteRepo(localDs: LocalDs): FavouriteRepoInterface {
        return FavouriteRepo(localDs)
    }

    @Provides
    @Singleton
    fun provideHomeRepo(): HomeRepoInterface {
        return HomeRepo(ApiClient)
    }

    @Provides
    @Singleton
    fun providePopularRepo(): PopularRepoInterface {
        return PopularRepo(ApiClient)
    }

    @Provides
    @Singleton
    fun provideSearchRepo(): SearchRepoInterface {
        return SearchRepo(ApiClient)
    }

    @Provides
    @Singleton
    fun provideTopRatedRepo(): TopRatedRepoInterface {
        return TopRatedRepo(ApiClient)
    }

    @Provides
    @Singleton
    fun provideUpComingRepo(): UpComingRepoInterface {
        return UpComingRepo(ApiClient)

    }


}