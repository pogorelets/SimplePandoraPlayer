package ru.helen.simplepandoraplayer.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.helen.simplepandoraplayer.api.*
import ru.helen.simplepandoraplayer.model.*

class NetworkRepositoryImpl(val api: PandoraAPI): NetworkRepository {

    lateinit var call: Call<Responce<User>>
    lateinit var partnerCall: Call<Responce<PartnerUser>>
    lateinit var stationsCall: Call<Responce<ListStations>>
    lateinit var audioCall: Call<Responce<ResponseAudio>>

    override fun login(partnerId: String, authId: String, login: String, password: String, token: String, user: MutableLiveData<User>){
        call = api.login(partnerId,authId,true, RequestLogin(username = login, password = password, partnerAuthToken = token))
        call.enqueue(object : Callback<Responce<User>> {
            override fun onFailure(call: Call<Responce<User>>?, t: Throwable?) {
                Log.e("USERERROR", t.toString())
            }

            override fun onResponse(call: Call<Responce<User>>?, response: Response<Responce<User>>?) {
                user.postValue(response?.body()?.result)
            }

        })

    }

    override fun partnerLogin(user: MutableLiveData<PartnerUser>) {
        partnerCall = api.partnerLogin(RequestPartnerLogin())
        partnerCall.enqueue(object : Callback<Responce<PartnerUser>> {
            override fun onResponse(call: Call<Responce<PartnerUser>>?, response: Response<Responce<PartnerUser>>?) {
                user.postValue(response?.body()?.result)
            }

            override fun onFailure(call: Call<Responce<PartnerUser>>?, t: Throwable?) {
                Log.e("USERERROR", t.toString())
            }
        })
    }

    override fun getStationList(stations: MutableLiveData<ListStations>){
        stationsCall = api.getStationList (encrypted =  true, request = RequestStation())
        stationsCall.enqueue(object : Callback<Responce<ListStations>>{
            override fun onFailure(call: Call<Responce<ListStations>>?, t: Throwable?) {
                Log.e("USERERROR", t.toString())
            }

            override fun onResponse(call: Call<Responce<ListStations>>?, response: Response<Responce<ListStations>>?) {
                stations.postValue(response?.body()?.result)
            }

        })
    }

    override fun getAudioList(audioitems: MutableLiveData<List<AudioItem>>){
        audioCall = api.getAudioList(encrypted =  true, request = RequestAudio())
        audioCall.enqueue(object : Callback<Responce<ResponseAudio>>{
            override fun onFailure(call: Call<Responce<ResponseAudio>>?, t: Throwable?) {
                Log.e("USERERROR", t.toString())
            }

            override fun onResponse(call: Call<Responce<ResponseAudio>>?, response: Response<Responce<ResponseAudio>>?) {
                audioitems.postValue(response?.body()?.result?.items)
            }

        })


    }
}