package com.kunlun.breadtrip.recommend;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunlun.breadtrip.R;
import com.kunlun.breadtrip.base.BaseFragment;
import com.kunlun.breadtrip.bean.RecommendBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/10.
 */
public class RecommendFragment extends BaseFragment {
    private ListView listView;
    private ListAdapter listAdapter;
    private List<RecommendBean> beanList;


    @Override
    public int initLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView(View view) {
        listView = bindView(R.id.recommend_lv);
        LinearLayout headViewLayout = (LinearLayout) LayoutInflater.
                from(context).inflate(R.layout.head_view, null);
        listView.addHeaderView(headViewLayout);

    }

    @Override
    public void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        listAdapter = new ListAdapter(context);
        StringRequest request = new StringRequest("http://chanyouji.com/api/trips/featured.json?page",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        beanList = new ArrayList<>();
                        Type type = new TypeToken<ArrayList<RecommendBean>>() {
                        }.getType();
                        beanList = gson.fromJson(response, type);
                        listAdapter.setRecommendBeen(beanList);
                        Log.d("aa", "1111" + beanList.get(0).getName());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
        listView.setAdapter(listAdapter);
    }
}
