package ru.helen.simplepandoraplayer.ui.player

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.*
import ru.helen.simplepandoraplayer.R
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.source.ExtractorMediaSource
import kotlinx.android.synthetic.main.activity_player.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.trackselection.*
import ru.helen.simplepandoraplayer.App
import ru.helen.simplepandoraplayer.model.AudioItem
import ru.helen.simplepandoraplayer.model.Track
import ru.helen.simplepandoraplayer.viewmodel.PlayerModel
import ru.helen.simplepandoraplayer.viewmodel.ViewModelFactory
import javax.inject.Inject
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.TrackGroupArray
import ru.helen.simplepandoraplayer.ui.station.StationActivity


class PlayerActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: PlayerModel
    var trackList: MutableList<Track> = ArrayList()
    var currentTrack: Track? = null
    var prevTrack: Track? = null
    var nextTrack: Track? = null
    var playerPosition: Long = 0
    lateinit var handler: Handler


    lateinit var player: SimpleExoPlayer

    //TODO Аудиофокус когда звонят или выдёргивают наушники
    //TODO Все это в foreground service


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        App.instance.appComponent.inject(this)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(PlayerModel::class.java)

        viewModel.getAudio()
        viewModel.audioitems.observe(this, Observer { responce ->
               makeTrackList(responce!!)
        })
        initializePlayer()
        exoPlay.setOnClickListener {
            playCurrentTrack()
            exoPlay.visibility = View.GONE
            exoPause.visibility = View.VISIBLE
        }
        exoPause.setOnClickListener {
            player.setPlayWhenReady(false)

            exoPause.visibility = View.GONE
            exoPlay.visibility = View.VISIBLE
            Log.e("player.playbackState",player.playbackState.toString())
            Log.e("player.currentPosition",player.currentPosition.toString())
        }

        exoNext.setOnClickListener {
            if (nextTrack != null){
                currentTrack = nextTrack
                setScren()
                playCurrentTrack()
            }

        }
        exoPrev.setOnClickListener {
            if (prevTrack != null){
                currentTrack = prevTrack
                setScren()
                playCurrentTrack()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> startActivity(Intent(this, StationActivity::class.java))
        }
        return true
    }

    fun makeTrackList(audioitems: List<AudioItem>){
        val filterItems = audioitems.filter{it.adToken == null}
        for ( items in filterItems){
            trackList.add(Track(items.albumArtUrl!!, items.audioUrlMap?.highQuality?.audioUrl!!, items.artistName!!, items.albumName!!, items.songName))
        }
        currentTrack = trackList.get(0)
        setScren()

    }

    fun setScren(){
        Glide.with(this)
                .load(currentTrack?.albumArtUrl)
                .into(cover)
        nameAlbum.text = currentTrack?.albumName
        songName.text = currentTrack?.songName
    }

    fun initializePlayer(){
        handler = Handler()
        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(trackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(applicationContext, trackSelector)
        player.addListener(object : Player.EventListener{
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

            }

            override fun onSeekProcessed() {
                Log.e("onSeekProcessed","onSeekProcessed")
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
                Log.e("onTracksChanged","onTracksChanged")
            }

            override fun onPlayerError(error: ExoPlaybackException?) {

            }

            override fun onLoadingChanged(isLoading: Boolean) {

                Log.e("onLoadingchange","onLoadingchange")
            }

            override fun onPositionDiscontinuity(reason: Int) {
                Log.e("onPositionDiscontinuity","onPositionDiscontinuity")
            }

            override fun onRepeatModeChanged(repeatMode: Int) {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                Log.e("onPlayerStateChanged", "onPlayerStateChanged")
                if (playbackState == ExoPlayer.STATE_ENDED){
                    if (nextTrack != null){
                        currentTrack = nextTrack
                        setScren()
                        playCurrentTrack()
                    }
                }


            }

        })


    }

    fun playCurrentTrack(){
        if (playerPosition > 0){
            player.seekTo(playerPosition)
            player.setPlayWhenReady(true)
        } else {

            if (currentTrack != null) {
                val bandwidthMeter = DefaultBandwidthMeter()
                val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, javaClass.simpleName), bandwidthMeter)
                val extractorsFactory = DefaultExtractorsFactory()
                val mediaSource = ExtractorMediaSource(Uri.parse(currentTrack?.audioUrl), dataSourceFactory, extractorsFactory, handler, null)

                player.prepare(mediaSource)
                player.setPlayWhenReady(true)
                // player.seekTo()
                Log.e("player.playbackState", player.playbackState.toString())
                Log.e("player.currentPosition", player.currentPosition.toString())
                playerPosition = player.currentPosition
            }
            //Когда начинаем проигрывать трэк подготавливаем следующий и предыдущий
            setNextAndPrevTrack()
        }
    }

    fun setNextAndPrevTrack(){
        if (!trackList.isEmpty() && currentTrack != null){
            val pos = trackList.indexOf(currentTrack!!)
            val len = trackList.size
            val next = pos + 1
            if (next<len){
                nextTrack = trackList.get(next)
            }  else {
                nextTrack = trackList.get(0)
            }

            val prev = pos - 1
            if (prev >= 0 && prev <len ){
                prevTrack = trackList.get(prev)
            } else {
                prevTrack = trackList.get(len - 1)
            }
        }
    }

}
