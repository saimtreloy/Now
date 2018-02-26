package saim.com.now.Shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.now.Adapter.AdapterServiceItemList;
import saim.com.now.Adapter.AdapterServiceShopList;
import saim.com.now.Database.DatabaseHandler;
import saim.com.now.Model.ModelItemList;
import saim.com.now.Model.ModelShopMenu;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;
import saim.com.now.Utilities.SharedPrefDatabase;

public class ShopItemList extends AppCompatActivity {

    public static Toolbar toolbar;

    ArrayList<ModelItemList> itemListArrayList = new ArrayList<>();
    RecyclerView recyclerViewServiceItemList;
    RecyclerView.LayoutManager layoutManagerServiceShopList;
    RecyclerView.Adapter serviceItemListAdapter;

    ProgressBar progressBar;

    public String service_shop_id, service_shop_type;

    //Database
    public DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item_list);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        recyclerViewServiceItemList = (RecyclerView) findViewById(R.id.recyclerViewServiceItemList);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        layoutManagerServiceShopList = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewServiceItemList.setLayoutManager(layoutManagerServiceShopList);
        recyclerViewServiceItemList.setHasFixedSize(true);

        service_shop_id = getIntent().getExtras().getString("service_shop_id");
        service_shop_type = getIntent().getExtras().getString("service_shop_type");

        ServiceShopItemList(service_shop_id, service_shop_type);

        db = new DatabaseHandler(this);
    }


    public void ServiceShopItemList(final String service_shop_id, final String service_shop_type){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.Service_Shop_Item_List,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("SAIM RESPONSE", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArrayServiceList = jsonObject.getJSONArray("service_list");
                                for (int i=0; i<jsonArrayServiceList.length(); i++){
                                    JSONObject jsonObjectServiceList = jsonArrayServiceList.getJSONObject(i);

                                    String id = jsonObjectServiceList.getString("id");
                                    String item_id = jsonObjectServiceList.getString("item_id");
                                    String item_name = jsonObjectServiceList.getString("item_name");
                                    String item_price = jsonObjectServiceList.getString("item_price");
                                    String item_d_price = jsonObjectServiceList.getString("item_d_price");
                                    String item_quantity = jsonObjectServiceList.getString("item_quantity");
                                    String item_icon = jsonObjectServiceList.getString("item_icon");
                                    String item_vendor = jsonObjectServiceList.getString("item_vendor");
                                    String item_vendor_icon = jsonObjectServiceList.getString("item_vendor_icon");

                                    ModelItemList modelItemList = new ModelItemList(id, item_id, item_name, item_price, item_d_price, item_quantity, item_icon, item_vendor, item_vendor_icon);
                                    itemListArrayList.add(modelItemList);
                                }

                                serviceItemListAdapter = new AdapterServiceItemList(itemListArrayList);
                                recyclerViewServiceItemList.setAdapter(serviceItemListAdapter);

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
                params.put("service_shop_id", service_shop_id);
                params.put("service_shop_type", service_shop_type);
                params.put("item_vendor", ShopHome.VENDOR_ID);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu_shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private final BroadcastReceiver StopService = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String KEY_ID = intent.getExtras().getString("KEY_ID");
            String KEY_ITEM_ID = intent.getExtras().getString("KEY_ITEM_ID");
            String KEY_NAME = intent.getExtras().getString("KEY_NAME");
            String KEY_PRICE = intent.getExtras().getString("KEY_PRICE");
            String KEY_PRICE_D = intent.getExtras().getString("KEY_PRICE_D");
            String KEY_QUANTITY = intent.getExtras().getString("KEY_QUANTITY");
            String KEY_ICON = intent.getExtras().getString("KEY_ICON");
            String KEY_VENDOR = intent.getExtras().getString("KEY_VENDOR");
            String KEY_VENDOR_ICON = intent.getExtras().getString("KEY_VENDOR_ICON");
            int KEY_CART_Q = intent.getExtras().getInt("KEY_CART_Q");

            ModelItemList mil = new ModelItemList(KEY_ID, KEY_ITEM_ID, KEY_NAME, KEY_PRICE, KEY_PRICE_D, KEY_QUANTITY, KEY_ICON, KEY_VENDOR, KEY_VENDOR_ICON, KEY_CART_Q);


        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(StopService, new IntentFilter("com.moodybugs.banglamusic.StopService"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(StopService);
    }


    public void saveArrayList(ArrayList<ModelItemList> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);

        new SharedPrefDatabase(getApplicationContext()).StoreCartList(json);
    }

    public ArrayList<ModelItemList> getArrayList(){
        Gson gson = new Gson();
        String json = new SharedPrefDatabase(getApplicationContext()).RetriveCartList();
        Type type = new TypeToken<ArrayList<ModelItemList>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
