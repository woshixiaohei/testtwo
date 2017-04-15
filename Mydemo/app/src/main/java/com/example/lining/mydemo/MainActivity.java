package com.example.lining.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lining.mydemo.adapter.MyAdapter;
import com.example.lining.mydemo.animator.MyItemAnimator;
import com.example.lining.mydemo.decoration.DividerListItemDecoration;
import com.example.lining.mydemo.touchhelper.DefaultItemTouchHelpCallback;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;


public class MainActivity extends Activity {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<String> numbers;
    MyAdapter myAdapter;
    private TextView tv;
    private MyItemAnimator myItemAnimator;


//    addOnChildAttachStateChangeListener
//    removeOnChildAttachStateChangeListener
//    clearOnChildAttachStateChangeListeners
//    setOnScrollListener
//    scrollToPosition
//    jumpToPositionForSmoothScroller
//    smoothScrollToPosition
//    scrollTo
//    scrollBy
//    smoothScrollBy
//    focusSearch
//    requestChildFocus
//    addOnItemTouchListener
//    getChildViewHolder
//    getChildPosition










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        initListView();
        initListener();
    }






































    private void initListView() {


        int j=0%4;
        Log.e("j=====",""+j);

        tv = (TextView) findViewById(R.id.tv);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DividerListItemDecoration dividerListItemDecoration=new DividerListItemDecoration(this,LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerListItemDecoration);
        numbers = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            numbers.add("测试数据" + i);
        }
        myAdapter = new MyAdapter(this, numbers,recyclerView);
        View view= LayoutInflater.from(this).inflate(R.layout.headview,recyclerView,false);
//        myAdapter.addHeaderView(view);
        recyclerView.setAdapter(myAdapter);
        myItemAnimator = new MyItemAnimator();
        myItemAnimator.setChangeDuration(2000);
        recyclerView.setItemAnimator(myItemAnimator);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }
            @Override
            public void onChildViewDetachedFromWindow(View view) {

            }
        });



    }


private void initListener(){


    recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == SCROLL_STATE_IDLE) {
                Log.e("静止状态==", "1111111");
                Log.e("LastVisible==",""+linearLayoutManager.findLastVisibleItemPosition());
                Log.e("getItemCount==",""+(linearLayoutManager.getItemCount()-1));
                if (linearLayoutManager.getItemCount()-1 == linearLayoutManager.findLastVisibleItemPosition()) {
                    Log.e("滑到最底部了==","111111");
                    Toast.makeText(MainActivity.this, "滑到最底部了", Toast.LENGTH_SHORT).show();
                    View view = linearLayoutManager.findViewByPosition(linearLayoutManager.findLastCompletelyVisibleItemPosition());
                    myAdapter.insertMoreData();
                }else{
                    Log.e("滑到最底部了==","2222222");
                }
            } else if (newState == SCROLL_STATE_DRAGGING) {
                Log.e("拖拽状态==", "111111");
            } else if (newState == SCROLL_STATE_SETTLING) {
                Log.e("滑动状态==", "1111111");
                Log.e("LastVisible==",""+linearLayoutManager.findLastVisibleItemPosition());
                if (linearLayoutManager.getItemCount() - 1 == linearLayoutManager.findLastVisibleItemPosition()) {
                    Log.e("添加数据==", "1111111");
                    Toast.makeText(MainActivity.this, "滑到最底部了", Toast.LENGTH_SHORT).show();
                    View view = linearLayoutManager.findViewByPosition(linearLayoutManager.findLastCompletelyVisibleItemPosition());
                    myAdapter.insertMoreData();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            tv.setText("第一个可见==" + linearLayoutManager.findFirstVisibleItemPosition() + "/n" + "    最后一个==" + linearLayoutManager.findLastVisibleItemPosition());
        }
    });

    myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(MainActivity.this,"点击事件执行",Toast.LENGTH_SHORT).show();
//            if(view.isActivated()){
//                view.setActivated(true);
//            }else{
//                view.setActivated(false);
//            }

            myAdapter.refreshPosition(position);

        }
    });

    myAdapter.setOnLongItemClickListener(new MyAdapter.OnLongItemClickListener() {
        @Override
        public void onLongItemClick(View view, int position) {
            Toast.makeText(MainActivity.this,"长点击事件执行",Toast.LENGTH_SHORT).show();
        }
    });

    initTouchHelper();


}



private void initTouchHelper(){

    DefaultItemTouchHelpCallback callBack=new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwip(int position) {
            if(null!=numbers){
                myAdapter.removePosition(position);
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if(null!=numbers){
                myAdapter.movePOsition(srcPosition,targetPosition);
            }
            return false;
        }
    });
    ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callBack);
    itemTouchHelper.attachToRecyclerView(recyclerView);
}







































//  测试okhttpclient的重试策略


    public  Response requestUrl(String url) throws IOException {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(25000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new RetryIntercepter(3))
                ;
        OkHttpClient client = okHttpClientBuilder.build();
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(url).build();
        Response response = client.newCall(request).execute();
        return response;
    }







    public class RetryIntercepter implements Interceptor {

        public int maxRetry;//最大重试次数
        private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        public RetryIntercepter(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.e("retryNum2111=" ,""+ retryNum);
            Response response = chain.proceed(request);
            Log.e("retryNum222=" ,""+ retryNum);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                Log.e("retryNum3333=" ,""+ retryNum);
                response = chain.proceed(request);
            }
            return response;
        }
    }


}