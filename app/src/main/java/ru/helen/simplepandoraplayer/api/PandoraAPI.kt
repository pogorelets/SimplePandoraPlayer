package ru.helen.simplepandoraplayer.api


import retrofit2.Call
import retrofit2.http.*
import ru.helen.simplepandoraplayer.model.*
import ru.helen.simplepandoraplayer.repository.Storage

interface PandoraAPI {
    @POST("json/?method=auth.partnerLogin")
    fun partnerLogin(@Body request: RequestPartnerLogin) :  Call<Responce<PartnerUser>>

    @POST("json/?method=auth.userLogin")
    fun login(
            @Query(value = "partner_id") partnerId: String?,
            @Query(value = "auth_token") authToken: String?,
            @Header(value = EncryptionInterceptor.ENC_HEADER_TAG) encrypted: Boolean,
            @Body request: RequestLogin): Call<Responce<User>>


    @POST("json/?method=user.getStationList")
    fun getStationList(
            @Query(value = "partner_id") partnerId: String? = Storage.partnerId,
            @Query(value = "auth_token") authToken: String? = Storage.authToken,
            @Query(value = "user_id") userId: String? = Storage.user.userId,
            @Header(value = EncryptionInterceptor.ENC_HEADER_TAG) encrypted: Boolean,
            @Body request: RequestStation): Call<Responce<ListStations>>


    @POST("json/?method=station.getPlaylist")
    fun getAudioList(
            @Query(value = "partner_id") partnerId: String? = Storage.partnerId,
            @Query(value = "auth_token") authToken: String? = Storage.authToken,
            @Query(value = "user_id") userId: String? = Storage.user.userId,
            @Header(value = EncryptionInterceptor.ENC_HEADER_TAG) encrypted: Boolean,
            @Body request: RequestAudio): Call<Responce<ResponseAudio>>

}