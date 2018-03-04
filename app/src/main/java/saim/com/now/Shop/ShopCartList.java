package saim.com.now.Shop;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import saim.com.now.Adapter.AdapterServiceItemList;
import saim.com.now.Database.DatabaseHandler;
import saim.com.now.Login;
import saim.com.now.Model.ModelItemList;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;
import saim.com.now.Utilities.SharedPrefDatabase;

public class ShopCartList extends AppCompatActivity {

    public static Toolbar toolbar;

    ArrayList<ModelItemList> itemListArrayList = new ArrayList<>();
    RecyclerView recyclerViewServiceItemList;
    RecyclerView.LayoutManager layoutManagerServiceShopList;
    RecyclerView.Adapter serviceItemListAdapter;

    int deliveryCost = 0;

    LinearLayout layoutPlaceOrder;
    TextView txtTotalPrice, txtDeliveryPrice, txtAllTotalPrice, txtPlaceOrder;

    ProgressBar progressBar;
    ProgressDialog progressDialog;

    //Database
    public DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart_list);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Shopping Bag");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        recyclerViewServiceItemList = (RecyclerView) findViewById(R.id.recyclerViewServiceItemList);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        layoutManagerServiceShopList = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewServiceItemList.setLayoutManager(layoutManagerServiceShopList);
        recyclerViewServiceItemList.setHasFixedSize(true);

        layoutPlaceOrder = (LinearLayout) findViewById(R.id.layoutPlaceOrder);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotalPrice);
        txtDeliveryPrice = (TextView) findViewById(R.id.txtDeliveryPrice);
        txtAllTotalPrice = (TextView) findViewById(R.id.txtAllTotalPrice);
        txtPlaceOrder = (TextView) findViewById(R.id.txtPlaceOrder);

        db = new DatabaseHandler(this);
        if (db.getAllContacts().size()>0){
            layoutPlaceOrder.setVisibility(View.VISIBLE);
            if (db.getTotalPrice() <1000) {
                deliveryCost = 29;
                txtDeliveryPrice.setText(deliveryCost + "");
            } else {
                deliveryCost = 10;
                txtDeliveryPrice.setText(deliveryCost + "");
            }
            txtTotalPrice.setText(db.getTotalPrice()+"");
            txtAllTotalPrice.setText((db.getTotalPrice() + deliveryCost) +"");
        } else {
            layoutPlaceOrder.setVisibility(View.GONE);
        }

        ServiceShopItemList();


        txtPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new SharedPrefDatabase(getApplicationContext()).RetriveUserID().isEmpty() || new SharedPrefDatabase(getApplicationContext()).RetriveUserID()==null) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    String placeOrder = "";
                    String spaceBlack = "";
                    for (int i=0; i<db.getAllContacts().size(); i++){
                        int l = db.getAllContacts().get(i).getItem_name().length();
                        int ml = 40 - l;
                        for (int j=0; j<ml; j++){
                            spaceBlack = spaceBlack + " ";
                        }
                        placeOrder = placeOrder + db.getAllContacts().get(i).getItem_name() + spaceBlack + db.getAllContacts().get(i).getItem_d_price() + " tk\n";
                        spaceBlack = "";
                    }
                    for (int i=0; i<50;i++){
                        placeOrder = placeOrder + "-";
                    }
                    placeOrder = placeOrder + "\n" + "Subtotal Price";
                    for (int i=0; i<(40 - "Subtotal Price".length());i++){
                        placeOrder = placeOrder + " ";
                    }
                    placeOrder = placeOrder + db.getTotalPrice() + " tk\n";

                    placeOrder = placeOrder + "Delivery Cost";
                    for (int i=0; i<(40 - "Delivery Cost".length());i++){
                        placeOrder = placeOrder + " ";
                    }
                    placeOrder = placeOrder + txtDeliveryPrice.getText().toString() + " tk\n";

                    for (int i=0; i<50;i++){
                        placeOrder = placeOrder + "-";
                    }
                    placeOrder = placeOrder + "\n" + "Main Total Price";
                    for (int i=0; i<(40 - "Main Total Price".length());i++){
                        placeOrder = placeOrder + " ";
                    }
                    placeOrder = placeOrder + txtAllTotalPrice.getText().toString() + " tk\n";
                    Log.d("PLACE ORDER SAIM", placeOrder);


                    showPlaceOrderDialog(new SharedPrefDatabase(getApplicationContext()).RetriveUserName(),
                            new SharedPrefDatabase(getApplicationContext()).RetriveUserMobile(),
                            new SharedPrefDatabase(getApplicationContext()).RetriveUserLocation(),
                            placeOrder);
                }
            }
        });
    }


    public void ServiceShopItemList(){
        serviceItemListAdapter = new AdapterServiceItemList(db.getAllContacts());
        recyclerViewServiceItemList.setAdapter(serviceItemListAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if(id == R.id.btnOptionShopCart) {
            startActivity(new Intent(getApplicationContext(), ShopCartList.class));
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
            String KEY_CART_Q = intent.getExtras().getString("KEY_CART_Q") + "";
            String KEY_TOTAL_PRICE = (Integer.parseInt(KEY_CART_Q) * Integer.parseInt(KEY_PRICE_D) ) + "";

            ModelItemList mil = new ModelItemList(KEY_ID, KEY_ITEM_ID, KEY_NAME, KEY_PRICE, KEY_PRICE_D, KEY_QUANTITY, KEY_ICON, KEY_VENDOR, KEY_VENDOR_ICON, KEY_CART_Q, KEY_TOTAL_PRICE);


            if (db.getAllContacts().size() > 0){
                for (int i=0; i<db.getAllContacts().size(); i++){
                    if (db.getAllContacts().get(i).getId().equals(KEY_ID)){
                        if (KEY_CART_Q.equals("0")){
                            db.deleteContact(KEY_ID);
                        } else {
                            db.updateCartQ(KEY_ID, KEY_CART_Q, KEY_TOTAL_PRICE);
                        }
                    } else {
                        db.addContact(mil);
                    }
                }
            } else {
                db.addContact(mil);
            }

            if (db.getAllContacts().size()>0){
                layoutPlaceOrder.setVisibility(View.VISIBLE);
                if (db.getTotalPrice() <1000) {
                    deliveryCost = 29;
                    txtDeliveryPrice.setText(deliveryCost + "");
                } else {
                    deliveryCost = 10;
                    txtDeliveryPrice.setText(deliveryCost + "");
                }
                txtTotalPrice.setText(db.getTotalPrice()+"");
                txtAllTotalPrice.setText((db.getTotalPrice() + deliveryCost) +"");
            } else {
                layoutPlaceOrder.setVisibility(View.GONE);
            }
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


    public void showPlaceOrderDialog(String name, String phone, String area, final String orderDetail) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_shop_placeorder, null);
        dialogBuilder.setView(dialogView);

        final EditText txtDName = (EditText) dialogView.findViewById(R.id.txtDName);
        final EditText txtDPhone = (EditText) dialogView.findViewById(R.id.txtDPhone);
        final EditText txtDArea = (EditText) dialogView.findViewById(R.id.txtDArea);
        final EditText txtDAddress = (EditText) dialogView.findViewById(R.id.txtDAddress);

        txtDName.setText(name);
        txtDPhone.setText(phone);
        txtDArea.setText(area);

        dialogBuilder.setTitle("Place your order");
        dialogBuilder.setIcon(R.drawable.ic_place);
        dialogBuilder.setMessage("Please provide your address");
        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                PlaceOrderFinal(new SharedPrefDatabase(getApplicationContext()).RetriveUserID(),
                        new SharedPrefDatabase(getApplicationContext()).RetriveUserName(),
                        new SharedPrefDatabase(getApplicationContext()).RetriveUserMobile(),
                        new SharedPrefDatabase(getApplicationContext()).RetriveUserShopvendor(),
                        txtDAddress.getText().toString() + "\n" +
                        new SharedPrefDatabase(getApplicationContext()).RetriveUserLocation(),
                        getCurrentDateTime(),
                        orderDetail,
                        "Text",
                        "Pending");
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.setCanceledOnTouchOutside(false);
        b.show();
    }


    public void PlaceOrderFinal(final String order_user_id, final String order_user_name, final String order_user_phone, final String order_vendor_id
            , final String order_user_location, final String order_time, final String order_detail, final String order_type, final String order_status){

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.Service_Shop_Place_Order,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                db.deleteAllContact();
                                finish();
                                progressDialog.dismiss();
                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
                params.put("order_user_id", order_user_id);
                params.put("order_user_name", order_user_name);
                params.put("order_user_phone", order_user_phone);
                params.put("order_vendor_id", order_vendor_id);
                params.put("order_user_location", order_user_location);
                params.put("order_time", order_time);
                params.put("order_detail", order_detail);
                params.put("order_type", order_type);
                params.put("order_status", order_status);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public String getCurrentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();

        return formatter.format(date) + "";
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        PopulateList();
    }


    public void PopulateList(){
        if (db.getAllContacts().size()>0){
            layoutPlaceOrder.setVisibility(View.VISIBLE);
            if (db.getTotalPrice() <1000) {
                deliveryCost = 29;
                txtDeliveryPrice.setText(deliveryCost + "");
            } else {
                deliveryCost = 10;
                txtDeliveryPrice.setText(deliveryCost + "");
            }
            txtTotalPrice.setText(db.getTotalPrice()+"");
            txtAllTotalPrice.setText((db.getTotalPrice() + deliveryCost) +"");
        } else {
            layoutPlaceOrder.setVisibility(View.GONE);
        }
    }
}
