package com.example.lining.mydemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lining.mydemo.R;

/**
 * Created by lining on 17-3-23.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView tv;
    public ImageView iv;
    public MyViewHolder(View view) {
        super(view);
        tv = (TextView) view.findViewById(R.id.tv);
        iv= (ImageView) view.findViewById(R.id.iv);
    }

}
