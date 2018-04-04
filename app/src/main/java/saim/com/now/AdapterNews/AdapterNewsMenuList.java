package saim.com.now.AdapterNews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saim.com.now.Model.ModelServiceList;
import saim.com.now.ModelNews.ModelMenu;
import saim.com.now.News.NewsHome;
import saim.com.now.R;
import saim.com.now.Shop.ShopVendor;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterNewsMenuList extends RecyclerView.Adapter<AdapterNewsMenuList.ServiceListViewHolder>{

    ArrayList<ModelMenu> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterNewsMenuList(ArrayList<ModelMenu> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterNewsMenuList(ArrayList<ModelMenu> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_menu_list, parent, false);
        ServiceListViewHolder serviceListViewHolder = new ServiceListViewHolder(view);
        return serviceListViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {

        /*Picasso.with(holder.listImageView.getContext())
                .load(adapterList.get(position).getService_icon())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.listImageView);
        holder.listImageView.setColorFilter(Color.parseColor(adapterList.get(position).getService_color()));
        holder.listName.setText(adapterList.get(position).getService_name());*/
        holder.txtNewsMenu.setText(adapterList.get(position).getMenu());

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNewsMenu;

        public ServiceListViewHolder(View itemView) {
            super(itemView);

            txtNewsMenu = (TextView) itemView.findViewById(R.id.txtNewsMenu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
