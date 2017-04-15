package com.example.lining.mydemo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lining.mydemo.R;
import com.example.lining.mydemo.viewholder.BottomViewHolder;
import com.example.lining.mydemo.viewholder.HeaderViewHolder;
import com.example.lining.mydemo.viewholder.MyViewHolder;

import java.util.List;

/**
 * Created by lining on 17-3-23.
 */
public class MyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> numbers;
    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;
    private View headerView;
    private RecyclerView recyclerView;


    private final static int TYPE_HEADVIEW=100;
    private final static int TYPE_ITEM=101;
    private final static int TYPE_PROGRESS=102;

    private int animPosition=10000000;


    public MyAdapter(Context mContext, List<String> numbers , RecyclerView recyclerView) {
        this.mContext = mContext;
        this.numbers = numbers;
        this.recyclerView=recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      if(viewType==TYPE_HEADVIEW){
          HeaderViewHolder headerViewHolder=new HeaderViewHolder(headerView);
          return headerViewHolder;
      }else if(viewType==TYPE_PROGRESS){
          View bottom=LayoutInflater.from(mContext).inflate(R.layout.progress_item,parent,false);
          BottomViewHolder bottomViewHolder=new BottomViewHolder(bottom);
          return  bottomViewHolder;
      }else{
          View view = LayoutInflater.from(mContext).inflate(R.layout.simple_horizontal_item, parent, false);
          MyViewHolder myViewHolder = new MyViewHolder(view);
          return myViewHolder;
      }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            myViewHolder.tv.setText(numbers.get(position));

            Log.e("position===",""+position);
            if(position==0){
                myViewHolder.itemView.requestFocus();
            }

            if(null!=onItemClickListener){
                myViewHolder.iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v,position);
                    }
                });
            }

            if(null!=onLongItemClickListener){
                myViewHolder.iv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onLongItemClickListener.onLongItemClick(v,position);
                        return true;
                    }
                });
            }

//            if(position==animPosition){
//                Log.e("animPosition222=======",""+animPosition);
//                ObjectAnimator.ofFloat(holder.itemView, "translationX", 200).setDuration(2000).start();
//            }



        }

        Log.e("position=======", "" + position);
    }




    public void insertMoreData() {

        for (int i = 0; i < 10; i++) {
            numbers.add("我是被添加的数据" + i);
        }
        Log.e("getItemCount==", "" + getItemCount());
//      注意在子线程里面  我们是不可以调用刷新ui的方法的
        notifyItemInserted(getItemCount() - 1);
//        notifyDataSetChanged();

//      recyclerView.smoothScrollToPosition(getItemCount());
    }





    public void refreshPosition(int position) {
        numbers.set(position, "哈哈哈" + position);
        animPosition=position;
        Log.e("animPosition11111======",""+animPosition);
        notifyItemChanged(position);
    }

    public void removePosition(int position) {
        numbers.remove(position);
        notifyItemRemoved(position);
    }


    public void movePOsition(int srcPosition,int targetPOsition){
        String src=numbers.get(srcPosition);
        String target=numbers.get(targetPOsition);
        numbers.remove(srcPosition);
        numbers.add(srcPosition,target);
        numbers.remove(targetPOsition);
        numbers.add(targetPOsition,src);
        notifyItemMoved(srcPosition,targetPOsition);

    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }



    public interface OnItemClickListener{
         void onItemClick(View view, int position);
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener){
        this.onLongItemClickListener=onLongItemClickListener;
    }


    public void addHeaderView(View view){
        this.headerView=view;
    }

    @Override
    public int getItemViewType(int position) {

        if(headerView!=null){
            if(position==getItemCount()-1){
                return TYPE_PROGRESS;
            }else if(position==0){
                return TYPE_HEADVIEW;
            }else{
                return TYPE_ITEM;
            }
        }else {
            return TYPE_ITEM;
        }
//        return super.getItemViewType(position);
    }
}
