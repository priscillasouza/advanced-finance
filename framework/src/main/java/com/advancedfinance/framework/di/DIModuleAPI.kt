package com.advancedfinance.framework.di

import com.advancedfinance.framework.infrastruture.cloud.api.AdvancedFinanceAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {


    single { provideGson() }

    single { provideOkHttpCliente() }

    single { provideConverterFactory(get<Gson>()) }

    single() { provideRetrofit(BuildConfig.BASE_UR, get<OkHttpClient>(), get<GsonConverterFactory>(), get<RxJava2CallAdapterFactory>()) }

}


private fun provideGson(): Gson {
    return GsonBuilder()
        .create()
}

//TODO: Implementar os timeouts por remoteconfig do firebase
private fun provideOkHttpCliente(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}


private fun provideConverterFactory(gson: Gson): GsonConverterFactory {
    return GsonConverterFactory.create(gson)
}


private fun provideRetrofit(url : String,
                    client: OkHttpClient,
                    converterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()
}


private fun providerServiceGEOApi(retrofit: Retrofit): AdvancedFinanceAPI {
    return retrofit.create(AdvancedFinanceAPI::class.java)
}