package com.example.work3;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    private  List<Map<String,String>> data;
    public int lastIndex=-1;
    Handler handler;

    public static class VH extends RecyclerView.ViewHolder{
        public final TextView Name;
        public final TextView IP;

        public VH(View itemView) {
            super(itemView);
            Name =(TextView) itemView.findViewById(R.id.tVw_name);
            IP =(TextView) itemView.findViewById(R.id.tVw_IP);
        }
    }

    public MyAdapter(List<Map<String,String>> data,Handler handler){
        this.data=data;
        this.handler=handler;
    }
    @NonNull
    @Override
    public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new VH(view);
    }

    public void changeIndex(int changeIndex){
        this.lastIndex=changeIndex;
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh,final int i) {
        if(lastIndex==i){
            vh.itemView.setBackgroundColor(0xff666666);
        }else{
            vh.itemView.setBackgroundColor(0x00666666);
        }
        vh.Name.setText(data.get(i).get("Name"));
        vh.IP.setText(data.get(i).get("IP"));
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.itemView.setBackgroundColor(0xff666666);
                int oldIndex=lastIndex;
                notifyItemChanged(oldIndex);
                lastIndex=i;
                Message msg=new Message();
                msg.what=1;
                msg.obj=i;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
