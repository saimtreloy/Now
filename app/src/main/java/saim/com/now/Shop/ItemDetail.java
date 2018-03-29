package saim.com.now.Shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;
import saim.com.now.Utilities.SharedPrefDatabase;

public class ItemDetail extends AppCompatActivity {

    public static Toolbar toolbar;
    ProgressDialog progressDialog;

    ImageView imgItem;
    TextView txtItemName, txtItemDetail, txtItemCategory, txtItemPrice, txtItemPriceD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeBody);
        setContentView(R.layout.activity_item_detail);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Item Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);


        imgItem = (ImageView) findViewById(R.id.imgItem);
        txtItemName = (TextView) findViewById(R.id.txtItemName);
        txtItemDetail = (TextView) findViewById(R.id.txtItemDetail);
        txtItemCategory = (TextView) findViewById(R.id.txtItemCategory);
        txtItemPrice = (TextView) findViewById(R.id.txtItemPrice);
        txtItemPriceD = (TextView) findViewById(R.id.txtItemPriceD);

        ItemDetails(getIntent().getExtras().getString("ITEM_ID"), new SharedPrefDatabase(getApplicationContext()).RetriveUserShopvendor());
        Log.d("SAIM", getIntent().getExtras().getString("ITEM_ID") + "\n" + new SharedPrefDatabase(getApplicationContext()).RetriveUserShopvendor());
    }


    public void ItemDetails(final String id, final String vendor_id){
        progressDialog.setMessage("Please wiat...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.Service_Item_Detail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArrayResult = jsonObject.getJSONArray("result");
                                JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(0);

                                String id = jsonObjectResult.getString("id");
                                String item_id = jsonObjectResult.getString("item_id");
                                String service_shop_ic_id = jsonObjectResult.getString("service_shop_ic_id");
                                String item_name = jsonObjectResult.getString("item_name");
                                String item_detail = jsonObjectResult.getString("item_detail");
                                String item_price = jsonObjectResult.getString("item_price");
                                String item_d_price = jsonObjectResult.getString("item_d_price");
                                String item_quantity = jsonObjectResult.getString("item_quantity");
                                String item_icon = jsonObjectResult.getString("item_icon");
                                String item_vendor = jsonObjectResult.getString("item_vendor");
                                String item_offer = jsonObjectResult.getString("item_offer");
                                String item_popular = jsonObjectResult.getString("item_popular");
                                String item_collection = jsonObjectResult.getString("item_collection");


                                txtItemName.setText(item_name);
                                txtItemDetail.setText(item_detail);
                                txtItemPrice.setText(item_price + " tk");
                                txtItemPrice.setText(item_d_price + " tk");
                                txtItemCategory.setText(item_quantity);


                                Picasso.with(getApplicationContext())
                                        .load(item_icon)
                                        .placeholder(R.drawable.ic_logo)
                                        .error(R.drawable.ic_logo)
                                        .into(imgItem);
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
                params.put("item_id", id);
                params.put("vendor_id", vendor_id);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
}
