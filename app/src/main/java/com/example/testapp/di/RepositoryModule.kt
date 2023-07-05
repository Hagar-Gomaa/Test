package com.example.testapp.di

import com.example.testapp.data.repository.RepositoryImp
import com.example.testapp.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
  @ViewModelScoped
  abstract fun providesRepository(repositoryImp: RepositoryImp): Repository


}




