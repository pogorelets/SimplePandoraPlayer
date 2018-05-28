package ru.helen.simplepandoraplayer.di

import android.content.Context
import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.helen.simplepandoraplayer.api.EncryptionInterceptor
import ru.helen.simplepandoraplayer.api.PandoraAPI
import ru.helen.simplepandoraplayer.repository.NetworkRepository
import ru.helen.simplepandoraplayer.repository.NetworkRepositoryImpl
import ru.helen.simplepandoraplayer.viewmodel.ViewModelFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(val context : Context) {

    private val BASE_URL = "http://tuner.pandora.com/services/"

    @Provides
    @Singleton
    fun provideContext(): Context = context



    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(EncryptionInterceptor())
            .readTimeout(600, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            //прокси для пандоры 18.221.250.107
            .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("18.221.250.107", 8080)))
            .build()



    @Provides
    @Singleton
    fun gson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providePandoraAPI(retrofit: Retrofit): PandoraAPI = retrofit.create(PandoraAPI::class.java)

    @Provides
    @Singleton
    fun provideNetworkRepository(pandoraAPI: PandoraAPI): NetworkRepositoryImpl = NetworkRepositoryImpl(pandoraAPI)

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: NetworkRepositoryImpl) = ViewModelFactory(repository)

}
