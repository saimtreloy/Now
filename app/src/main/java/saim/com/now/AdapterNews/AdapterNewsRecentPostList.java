package saim.com.now.AdapterNews;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saim.com.now.ModelNews.ModelMenu;
import saim.com.now.ModelNews.ModelRecentPost;
import saim.com.now.News.NewsBrowser;
import saim.com.now.R;

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

        Log.d("SAIM NEW RESPONSE", adapterList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNewsTitle, txtNewsDetail;
        ImageView imgNewsRecentPost, imgNewsLike, imgNewsComment;

        public ServiceListViewHolder(View itemView) {
            super(itemView);

            txtNewsTitle = (TextView) itemView.findViewById(R.id.txtNewsTitle);
            txtNewsDetail = (TextView) itemView.findViewById(R.id.txtNewsDetail);
            imgNewsRecentPost = (ImageView) itemView.findViewById(R.id.imgNewsRecentPost);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), NewsBrowser.class).putExtra("MAIN_URL", adapterList.get(getAdapterPosition()).getTitle_link()).putExtra("TITLE", adapterList.get(getAdapterPosition()).getTitle()));
        }
    }
}
