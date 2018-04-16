package saim.com.now.AdapterNews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import saim.com.now.ModelNews.ModelNewsComment;
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

public class AdapterNewsComments extends RecyclerView.Adapter<AdapterNewsComments.ServiceListViewHolder>{

    ArrayList<ModelNewsComment> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterNewsComments(ArrayList<ModelNewsComment> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterNewsComments(ArrayList<ModelNewsComment> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_comments_list, parent, false);
        ServiceListViewHolder serviceListViewHolder = new ServiceListViewHolder(view);
        return serviceListViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {

        Picasso.with(holder.imgListComment.getContext())
                .load(adapterList.get(position).getUser_image())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.imgListComment);

        holder.txtListCommentName.setText(adapterList.get(position).getUser_name().trim());
        holder.txtListCommentDate.setText(adapterList.get(position).getDate().trim());
        holder.txtListComments.setText(adapterList.get(position).getComment().trim());

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtListCommentName, txtListCommentDate, txtListComments;
        ImageView imgListComment;

        public ServiceListViewHolder(View itemView) {
            super(itemView);

            txtListCommentName = (TextView) itemView.findViewById(R.id.txtListCommentName);
            txtListCommentDate = (TextView) itemView.findViewById(R.id.txtListCommentDate);
            txtListComments = (TextView) itemView.findViewById(R.id.txtListComments);
            imgListComment = (ImageView) itemView.findViewById(R.id.imgListComment);

            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            //v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), NewsBrowser.class).putExtra("MAIN_URL", adapterList.get(getAdapterPosition()).getTitle_link()).putExtra("TITLE", adapterList.get(getAdapterPosition()).getTitle()));
        }



    }
}
