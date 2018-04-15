package saim.com.now.News;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.now.Adapter.AdapterServiceList;
import saim.com.now.Adapter.AdapterShopOrderList;
import saim.com.now.AdapterNews.AdapterNewsMenuList;
import saim.com.now.AdapterNews.AdapterNewsRecentPostList;
import saim.com.now.Model.ModelServiceList;
import saim.com.now.Model.ModelShopOrder;
import saim.com.now.ModelNews.ModelMenu;
import saim.com.now.ModelNews.ModelRecentPost;
import saim.com.now.ModelNews.ModelTicker;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;

public class NewsHome extends AppCompatActivity {

    public static Toolbar toolbar;
    ProgressDialog progressDialog;

    RelativeLayout layoutNewsHome;
    ScrollView scrollMain;

    static Handler tickerHandler = new Handler();
    static ArrayList<ModelTicker> tickerList = new ArrayList<>();
    static ArrayList<ModelRecentPost> recentPostList = new ArrayList<>();
    static ArrayList<ModelMenu> modelMenus = new ArrayList<>();
    static int start = 0;

    static TextView txtTicker, txtDate;

    RecyclerView recyclerViewNewsMenu;
    LinearLayoutManager layoutManagerNewsMenu;
    RecyclerView.Adapter newsMenuListAdapter;

    RecyclerView recyclerViewNewsRecentPost;
    RecyclerView.LayoutManager layoutManagerRecentPostList;
    RecyclerView.Adapter newsRecentPostListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeBody);
        setContentView(R.layout.activity_news_home);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Now News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        layoutNewsHome = (RelativeLayout) findViewById(R.id.layoutNewsHome);
        scrollMain = (ScrollView) findViewById(R.id.scrollMain);
        txtTicker = (TextView) findViewById(R.id.txtTicker);
        txtDate = (TextView) findViewById(R.id.txtDate);

        recyclerViewNewsMenu = (RecyclerView) findViewById(R.id.recyclerViewNewsMenu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewsMenu.setLayoutManager(layoutManager);
        recyclerViewNewsMenu.setHasFixedSize(true);

        recyclerViewNewsRecentPost = (RecyclerView) findViewById(R.id.recyclerViewNewsRecentPost);
        layoutManagerRecentPostList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNewsRecentPost.setLayoutManager(layoutManagerRecentPostList);
        recyclerViewNewsRecentPost.setHasFixedSize(true);
        LoadMoreData(recyclerViewNewsRecentPost);

        //ServiceList();
        MenuList();
    }


    public void ServiceList(){
        recentPostList.clear();
        modelMenus.clear();
        tickerList.clear();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.NEWS_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("RESPONSE SAIM", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArrayRecentPost = jsonObject.getJSONArray("recent_post");
                                JSONArray jsonArrayMenu = jsonObject.getJSONArray("menu_list");
                                JSONArray jsonArrayTickList = jsonObject.getJSONArray("tic_list");

                                ExtractData(jsonArrayRecentPost, jsonArrayMenu, jsonArrayTickList);
                            } else {
                                Snackbar.make(layoutNewsHome ,"Something is wrong", Snackbar.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void ExtractData(JSONArray RecentPost, JSONArray MenuList, JSONArray TickList) {

        scrollMain.setVisibility(View.VISIBLE);

        for (int i=0; i<RecentPost.length(); i++) {
            try {

                JSONObject jsonObjectPost = RecentPost.getJSONObject(i);
                String category = jsonObjectPost.getString("category");
                String category_link = jsonObjectPost.getString("category_link");
                String image = jsonObjectPost.getString("image");
                String date = jsonObjectPost.getString("date");
                String title = jsonObjectPost.getString("title");
                String title_link = jsonObjectPost.getString("title_link");
                String detail = jsonObjectPost.getString("detail");

                ModelRecentPost modelRecentPost = new ModelRecentPost(category, category_link, image, date, title, title_link, detail);
                recentPostList.add(modelRecentPost);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        newsRecentPostListAdapter = new AdapterNewsRecentPostList(recentPostList);
        recyclerViewNewsRecentPost.setAdapter(newsRecentPostListAdapter);
        LoadMoreData(recyclerViewNewsRecentPost);

        for (int i=0; i<MenuList.length(); i++) {
            try {

                JSONObject jsonObjectPost = MenuList.getJSONObject(i);
                String menu_name = jsonObjectPost.getString("menu_name");
                String menu_link = jsonObjectPost.getString("menu_link");

                ModelMenu modelMenu = new ModelMenu(menu_name, menu_link);
                modelMenus.add(modelMenu);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("SAIM MENU", modelMenus.toString());
        newsMenuListAdapter = new AdapterNewsMenuList(modelMenus);
        recyclerViewNewsMenu.setAdapter(newsMenuListAdapter);

        for (int i=0; i<TickList.length(); i++) {
            try {

                JSONObject jsonObjectTicker = TickList.getJSONObject(i);
                String tic_name = jsonObjectTicker.getString("tic_name");
                String tic_date = jsonObjectTicker.getString("tic_date");
                String tic_link = jsonObjectTicker.getString("tic_link");

                ModelTicker modelTicker = new ModelTicker(tic_name, tic_date, tic_link);
                tickerList.add(modelTicker);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tickerHandler.postDelayed(mUpdateTickerTask, 0);

    }


    static Runnable mUpdateTickerTask = new Runnable() {
        public void run() {
            try {
                if (start > tickerList.size()){
                    start = 0;
                }
                txtDate.setText(tickerList.get(start).getTickerDate().trim() + ": ");
                txtTicker.setText(tickerList.get(start).getTickerName().trim());
                txtTicker.setTag(tickerList.get(start).getTickerLink().trim());

                txtTicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("SAIM TICKER LINK", txtTicker.getTag().toString());
                    }
                });

                start++;
            } catch (Exception e) {
                e.printStackTrace();
            }
            tickerHandler.postDelayed(mUpdateTickerTask, 5000);
        }

    };


    public void LoadMoreData(final RecyclerView recycler) {
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recycler.getLayoutManager().getChildCount();
                int totalItemCount = recycler.getLayoutManager().getItemCount();
                int pastVisiblesItems = ((LinearLayoutManager) recycler.getLayoutManager()).findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    Log.d("SAIM RECYCLER VIEW", " Reached Last Item");
                    PostList(recentPostList.size() + "");
                }
            }
        });


    }

    public void MenuList(){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.NEWS_MENU,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAM RESPOSNE ", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){

                                JSONArray jsonArrayUser = jsonObject.getJSONArray("post_list");
                                for (int i=0; i<jsonArrayUser.length(); i++){
                                    JSONObject jsonObjectUser = jsonArrayUser.getJSONObject(i);

                                    String id = jsonObjectUser.getString("id");
                                    String menu_link = jsonObjectUser.getString("menu_link");
                                    String menu = jsonObjectUser.getString("menu");

                                    ModelMenu modelMenu = new ModelMenu(menu, menu_link);
                                    modelMenus.add(modelMenu);
                                }

                                newsMenuListAdapter = new AdapterNewsMenuList(modelMenus);
                                recyclerViewNewsMenu.setAdapter(newsMenuListAdapter);

                                TickerList();

                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void TickerList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.NEWS_TICKER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAM RESPOSNE ", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){

                                JSONArray jsonArrayUser = jsonObject.getJSONArray("post_list");
                                for (int i=0; i<jsonArrayUser.length(); i++){
                                    JSONObject jsonObjectUser = jsonArrayUser.getJSONObject(i);

                                    String id = jsonObjectUser.getString("id");
                                    String ticker_name = jsonObjectUser.getString("ticker_name");
                                    String ticker_date = jsonObjectUser.getString("ticker_date");
                                    String ticker_link = jsonObjectUser.getString("ticker_link");

                                    ModelTicker modelTicker = new ModelTicker(ticker_name, ticker_date, ticker_link);
                                    tickerList.add(modelTicker);
                                }

                                //tickerHandler.postDelayed(mUpdateTickerTask, 0);

                                PostList(0 + "");

                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void PostList(final String list_size){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.NEWS_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAM RESPOSNE ", response);
                        progressDialog.dismiss();
                        scrollMain.setVisibility(View.VISIBLE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){

                                JSONArray jsonArrayUser = jsonObject.getJSONArray("post_list");
                                if (jsonArrayUser.length()>0) {
                                    for (int i=0; i<jsonArrayUser.length(); i++){
                                        JSONObject jsonObjectUser = jsonArrayUser.getJSONObject(i);

                                        String id = jsonObjectUser.getString("id");
                                        String title = jsonObjectUser.getString("title");
                                        String detail = jsonObjectUser.getString("detail");
                                        String image = jsonObjectUser.getString("image");
                                        String title_link = jsonObjectUser.getString("title_link");
                                        String date = jsonObjectUser.getString("date");
                                        String menu = jsonObjectUser.getString("menu");
                                        String menu_link = jsonObjectUser.getString("menu_link");
                                        String source = jsonObjectUser.getString("source");

                                        ModelRecentPost modelRecentPost = new ModelRecentPost(menu, menu_link, image, date, title, title_link, detail);
                                        recentPostList.add(modelRecentPost);
                                    }

                                    newsRecentPostListAdapter = new AdapterNewsRecentPostList(recentPostList);
                                    recyclerViewNewsRecentPost.setAdapter(newsRecentPostListAdapter);
                                    LoadMoreData(recyclerViewNewsRecentPost);
                                }


                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("list_size", list_size);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
