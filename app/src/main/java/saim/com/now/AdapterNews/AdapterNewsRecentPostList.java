package saim.com.now.AdapterNews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import saim.com.now.ModelNews.ModelMenu;
import saim.com.now.ModelNews.ModelRecentPost;
import saim.com.now.News.NewsBrowser;
import saim.com.now.News.NewsComment;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;
import saim.com.now.Utilities.SharedPrefDatabase;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterNewsRecentPostList extends RecyclerView.Adapter<AdapterNewsRecentPostList.ServiceListViewHolder>{

    ArrayList<ModelRecentPost> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterNewsRecentPostList(ArrayList<ModelRecentPost> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterNewsRecentPostList(ArrayList<ModelRecentPost> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_recent_post_list, parent, false);
        ServiceListViewHolder serviceListViewHolder = new ServiceListViewHolder(view);
        return serviceListViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {

        Picasso.with(holder.imgNewsRecentPost.getContext())
                .load(adapterList.get(position).getImage())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.imgNewsRecentPost);

        holder.txtNewsTitle.setText(adapterList.get(position).getTitle().trim());
        holder.txtNewsDetail.setText(adapterList.get(position).getDetail().trim());
        holder.txtLike.setText(adapterList.get(position).getLike().trim());
        holder.txtComment.setText(adapterList.get(position).getComments().trim());

        if (adapterList.get(position).getUser_id().equals("True")) {
            holder.imgNewsLike.setColorFilter(Color.YELLOW);
        }

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNewsTitle, txtNewsDetail, txtLike, txtComment;
        ImageView imgNewsRecentPost, imgNewsLike, imgNewsComment;

        public ServiceListViewHolder(View itemView) {
            super(itemView);

            txtNewsTitle = (TextView) itemView.findViewById(R.id.txtNewsTitle);
            txtNewsDetail = (TextView) itemView.findViewById(R.id.txtNewsDetail);
            txtLike = (TextView) itemView.findViewById(R.id.txtLike);
            txtComment = (TextView) itemView.findViewById(R.id.txtComment);
            imgNewsRecentPost = (ImageView) itemView.findViewById(R.id.imgNewsRecentPost);
            imgNewsLike = (ImageView) itemView.findViewById(R.id.imgNewsLike);
            imgNewsComment = (ImageView) itemView.findViewById(R.id.imgNewsComment);

            itemView.setOnClickListener(this);


            imgNewsLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("SAIM POST ID", adapterList.get(getAdapterPosition()).getId());
                    new android.os.Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            PostLike(imgNewsLike.getContext().getApplicationContext());
                        }
                    });

                }
            });

            imgNewsComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), NewsComment.class)
                            .putExtra("NEWS_NAME", adapterList.get(getAdapterPosition()).getTitle())
                            .putExtra("NEWS_ID", adapterList.get(getAdapterPosition()).getId()));
                }
            });
        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), NewsBrowser.class).putExtra("MAIN_URL", adapterList.get(getAdapterPosition()).getTitle_link()).putExtra("TITLE", adapterList.get(getAdapterPosition()).getTitle()));
        }


        public void PostLike(final Context context){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.NEWS_LIKE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                if (code.equals("success")){
                                    String message = jsonObject.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    imgNewsLike.setColorFilter(Color.YELLOW);
                                }else {
                                    String message = jsonObject.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                    if (new SharedPrefDatabase(context).RetriveUserID().isEmpty() || new SharedPrefDatabase(context).RetriveUserID()==null) {
                        Toast.makeText(context, "You are not loged in", Toast.LENGTH_SHORT).show();
                    } else {
                        params.put("post_id", adapterList.get(getAdapterPosition()).getId());
                        params.put("user_id", new SharedPrefDatabase(context).RetriveUserID());
                    }
                    return params;
                }
            };
            stringRequest.setShouldCache(false);
            MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        }
    }
}
