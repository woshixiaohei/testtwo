package com.example.lining.mydemo.touchhelper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by lining on 17-3-23.
 */
public class DefaultItemTouchHelpCallback extends ItemTouchHelper.Callback {
    public DefaultItemTouchHelpCallback(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    private OnItemTouchCallbackListener onItemTouchCallbackListener;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

    RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
   if(layoutManager instanceof GridLayoutManager){
       int dragFlag=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN;
       int swipFlag=0;
       return  makeMovementFlags(dragFlag,swipFlag);
    }else if(layoutManager instanceof LinearLayoutManager){
       int dragFlag = 0;
       int swipeFlag = 0;
       LinearLayoutManager linearLayoutManager= (LinearLayoutManager) layoutManager;
       int orientation=linearLayoutManager.getOrientation();
       if (orientation == LinearLayoutManager.HORIZONTAL) {// 如果是横向的布局
           swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
           dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
       } else if (orientation == LinearLayoutManager.VERTICAL) {// 如果是竖向的布局，相当于ListView
           dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
           swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
       }
       return makeMovementFlags(dragFlag, swipeFlag);
   }

        return 0;


    }




    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

    if(onItemTouchCallbackListener!=null){
        return onItemTouchCallbackListener.onMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }

        return false;



    }




    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    if(null!=onItemTouchCallbackListener){
        onItemTouchCallbackListener.onSwip(viewHolder.getAdapterPosition());
    }




    }



    public interface OnItemTouchCallbackListener{


        void onSwip(int position);

        boolean onMove(int srcPosition, int targetPosition);

    }







}
