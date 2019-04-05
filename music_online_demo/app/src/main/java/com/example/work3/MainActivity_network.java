package com.example.work3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity_network extends AppCompatActivity implements View.OnTouchListener {
    MediaPlayer mMediaPlayer=new MediaPlayer();
    Button btn_play,btn_stop,btn_next,btn_prev;
    List<String> music =new ArrayList<>();
    int index=0;
    int musicMaxIndex=0;
    boolean isStopped=true;
    RecyclerView recyclerView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMyAdapter();
        initview();
    }

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0: {
                    adapter = new MyAdapter(data, handler);
                    recyclerView.setAdapter(adapter);
                    break;
                }
                case 1: {
                    index = (int) msg.obj;
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.reset();
                        try {
                            mMediaPlayer.setDataSource(data.get(index).get("IP"));
                            mMediaPlayer.prepareAsync();
                            mMediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        btn_play.setBackground(getDrawable(R.drawable.pause));

                    }else{
                        try {
                            if(isStopped==true) {
                                mMediaPlayer.setDataSource(data.get(index).get("IP"));
                                mMediaPlayer.prepareAsync();
                                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mMediaPlayer.start();
                                    }
                                });
                            }
                            mMediaPlayer.start();
                            btn_play.setBackground(getDrawable(R.drawable.pause));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }
    };

    private void initview() {
        btn_play=findViewById(R.id.btn_play);
        btn_stop=findViewById(R.id.btn_stop);
        btn_next=findViewById(R.id.btn_next);
        btn_prev=findViewById(R.id.btn_prev);
        btn_play.setOnTouchListener(this);
        btn_stop.setOnTouchListener(this);
        btn_next.setOnTouchListener(this);
        btn_prev.setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            v=changesize(v);
            switch(v.getId()){
                case R.id.btn_play:{
                    if(!mMediaPlayer.isPlaying()){
                        try {
                            if(isStopped==true) {
                                mMediaPlayer.setDataSource(data.get(index).get("IP"));
                                adapter.changeIndex(index);
                                recyclerView.setAdapter(adapter);
                                //mMediaPlayer.prepareAsync();
                                // 监听异步准备，一旦准备完成，就会调用此方法
                                mMediaPlayer.prepareAsync();
                                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        // 调用此方法，代表异步准备完成✅
                                        // 开始播放
                                        mMediaPlayer.start();
                                    }
                                });
                                //mMediaPlayer.prepare();
                            }
                            mMediaPlayer.start();

                            //mMediaPlayer.start();
                            btn_play.setBackground(getDrawable(R.drawable.pause));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        mMediaPlayer.pause();
                        btn_play.setBackground(getDrawable(R.drawable.play));
                        isStopped=false;
                    }
                    break;
                }
                case R.id.btn_stop:{
                    mMediaPlayer.reset();
                    adapter.changeIndex(-1);
                    recyclerView.setAdapter(adapter);
                    btn_play.setBackground(getDrawable(R.drawable.play));
                    isStopped=true;
                    break;
                }
                case R.id.btn_next:
                    index=++index>musicMaxIndex-1?0:index;
                    adapter.changeIndex(index);
                    recyclerView.setAdapter(adapter);
                    mMediaPlayer.reset();
                    try {
                        mMediaPlayer.setDataSource(data.get(index).get("IP"));
                        //mMediaPlayer.prepare();
                        mMediaPlayer.prepareAsync();
                        // 监听异步准备，一旦准备完成，就会调用此方法

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //mMediaPlayer.start();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // 调用此方法，代表异步准备完成✅
                            // 开始播放
                            mMediaPlayer.start();
                        }
                    });
                    btn_play.setBackground(getDrawable(R.drawable.pause));
                    break;
                case R.id.btn_prev:
                    index=--index<0?musicMaxIndex-1:index;
                    adapter.changeIndex(index);
                    recyclerView.setAdapter(adapter);
                    mMediaPlayer.reset();
                    try {
                        mMediaPlayer.setDataSource(data.get(index).get("IP"));
                        //mMediaPlayer.prepare();
                        mMediaPlayer.prepareAsync();
                        // 监听异步准备，一旦准备完成，就会调用此方法

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //mMediaPlayer.start();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // 调用此方法，代表异步准备完成✅
                            // 开始播放
                            mMediaPlayer.start();
                        }
                    });
                    btn_play.setBackground(getDrawable(R.drawable.pause));
                    break;
                default:break;
            }
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            v=gobacksize(v);
        }
        return false;
    }

    //按钮按下状态
    View changesize(View v){
        v.setAlpha((float)0.8);
        v.setScaleX((float) 0.8);
        v.setScaleY((float) 0.8);
        //vb.vibrate(500);//震动0.5s
        //Toast.makeText(MainActivity.this,"按下了" ,Toast.LENGTH_SHORT).show();
        return  v;
    }

    //按钮弹起状态
    View gobacksize(View v){
        v.setAlpha((float)1);
        v.setScaleX((float)1);
        v.setScaleY((float)1);
        //vb.cancel();
        //Toast.makeText(MainActivity.this,"松开了" ,Toast.LENGTH_SHORT).show();
        return  v;
    }

    List<Map<String,String>> data=new ArrayList<>();

    void initMyAdapter(){
        //RecyclerView rv=(RecyclerView)findViewById(R.id.recyclerView);
        adapter=new MyAdapter(get(),handler);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private final OkHttpClient client = new OkHttpClient();
    List<Map<String,String>> get(){
        Request request = new Request.Builder().url("http://www.nblingke.com:9100/interface/get_music.ashx")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                try {
                    JSONArray array=new JSONArray(response.body().string());
                    musicMaxIndex = array.length();
                    if(array.length()>0) {
                        for (int i = 0; i < musicMaxIndex; i++) {
                            JSONObject json = array.getJSONObject(i);
                            Map<String,String> musicNemu=new HashMap<>();
                            musicNemu.put("Name", json.getString("MusicName"));
                            musicNemu.put("IP", json.getString("MusicAddress"));
                            data.add(musicNemu);
                        }
                    }
                    handler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return data;
    }
}
