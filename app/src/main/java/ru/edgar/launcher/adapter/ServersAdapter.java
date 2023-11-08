package ru.edgar.launcher.adapter;

import android.os.Bundle;
import android.os.Environment;
import android.os.Build;

import android.util.Log;
import android.view.View.OnClickListener;
import android.graphics.PorterDuff;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.Context;

import ru.edgar.matrp.R;
import ru.edgar.launcher.activity.MainActivity;
import ru.edgar.launcher.model.Servers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.dinuscxj.progressbar.CircleProgressBar;

import org.ini4j.Wini;

public class ServersAdapter extends RecyclerView.Adapter<ServersAdapter.ServersViewHolder> {
	Context context;
	
	ArrayList<Servers> slist;
	
	public ServersAdapter(Context context, ArrayList<Servers> slist){
		 this.context = context;
		 this.slist = slist; 
	}
	
	@NonNull
	@Override
    public ServersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.serverlist_item, parent, false);
		return new ServersViewHolder(v); 
    }
  
    @Override
    public void onBindViewHolder(@NonNull ServersViewHolder holder, int position) {
        Servers servers = slist.get(position);
		holder.ponka.setColorFilter(Color.parseColor("#" + servers.getColor()),PorterDuff.Mode.SRC_ATOP);
		holder.people.setColorFilter(Color.parseColor("#" + servers.getColor()),PorterDuff.Mode.SRC_ATOP);
		holder.backColor.setColorFilter(Color.parseColor("#" + servers.getColor()),PorterDuff.Mode.SRC_ATOP);
		holder.name.setText(servers.getname());
		holder.textonline.setText(Integer.toString(servers.getOnline()) + "/" + Integer.toString(servers.getmaxOnline()));
	   /* holder.progressBar.setProgressStartColor(Color.parseColor("#" + servers.getColor()));
		holder.progressBar.setProgressEndColor(Color.parseColor("#" + servers.getColor()));
		holder.progressBar.setProgress(servers.getOnline());
		holder.progressBar.setMax(servers.getmaxOnline());*/
		boolean status = servers.getRecommend();
		Log.e("бля", "pon ==== " + status);
		if(status)
		{
			holder.server_recommend_text.setVisibility(0);
			holder.backColor.setVisibility(0);
		} else {
			holder.server_recommend_text.setVisibility(8);
			holder.backColor.setVisibility(8);
		}
		holder.container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_click));
				MainActivity.getMainActivity().closeServers();
				try {
					Wini w = new Wini(new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP/localsettings.ini"));
					w.put("server", "server", 1);
					w.put("server", "name", servers.getname());
					w.put("server", "color", servers.getColor());
					w.put("server", "maxonline", servers.getmaxOnline());
					w.put("server", "online", servers.getOnline());
					w.put("server", "edgar_host", servers.getHost());
					w.put("server", "edgar_port", servers.getPort());
					w.put("server", "id", servers.getId());
					w.store();
				} catch (IOException e) {
					e.printStackTrace();

				}
            }
        });
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public static class ServersViewHolder extends RecyclerView.ViewHolder {
        
		ConstraintLayout container;
		ImageView ponka, people;
		TextView name, textonline, server_recommend_text;
		ImageView backColor;
	    
        public ServersViewHolder(View itemView) {
            super(itemView);
            
		    name = itemView.findViewById(R.id.server_item_name);
			server_recommend_text = itemView.findViewById(R.id.server_recommend_text);
			ponka = itemView.findViewById(R.id.server_item_background);
			people = itemView.findViewById(R.id.server_item_image);
			textonline = itemView.findViewById(R.id.server_item_online);
			backColor = itemView.findViewById(R.id.server_recommend_card);
			container = itemView.findViewById(R.id.edgar_con);
        }
    }
	
}