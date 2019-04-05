package com.example.work3;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mMediaPlayer=null;
    Button btn_play,btn_stop,btn_next,btn_prev;
    List<Integer> music =new ArrayList<>();
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        btn_play=findViewById(R.id.btn_play);
        btn_stop=findViewById(R.id.btn_stop);
        btn_next=findViewById(R.id.btn_next);
        btn_prev=findViewById(R.id.btn_prev);
        btn_play.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_prev.setOnClickListener(this);
        music.add(R.raw.beyond1);
        music.add(R.raw.beyond2);
        music.add(R.raw.beyond3);
        music.add(R.raw.beyond4);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_play:{
                if(mMediaPlayer==null){
                    mMediaPlayer=MediaPlayer.create(this,music.get(index));
                }
                if(!mMediaPlayer.isPlaying()){
                    mMediaPlayer.start();
                    btn_play.setBackground(getDrawable(R.drawable.pause));
                }else{
                    mMediaPlayer.pause();
                    btn_play.setBackground(getDrawable(R.drawable.play));
                }
                break;
            }
            case R.id.btn_stop:{
                if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer=null;
                    btn_play.setBackground(getDrawable(R.drawable.play));
                }
                break;
            }
            case R.id.btn_next:
                index=++index>3?0:index;
                if(mMediaPlayer!=null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                }
                mMediaPlayer = MediaPlayer.create(this, music.get(index));
                mMediaPlayer.start();
                btn_play.setBackground(getDrawable(R.drawable.pause));
                break;
            case R.id.btn_prev:
                index=--index<0?3:index;
                if(mMediaPlayer!=null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                }
                mMediaPlayer = MediaPlayer.create(this, music.get(index));
                mMediaPlayer.start();
                btn_play.setBackground(getDrawable(R.drawable.pause));
                break;
            default:break;
        }
    }
}
