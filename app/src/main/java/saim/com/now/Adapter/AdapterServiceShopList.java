package saim.com.now.Adapter;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saim.com.now.Model.ModelServiceList;
import saim.com.now.Model.ModelShopMenu;
import saim.com.now.R;
import saim.com.now.Shop.ShopCategory;
import saim.com.now.Shop.ShopItemList;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterServiceShopList extends RecyclerView.Adapter<AdapterServiceShopList.ServiceListViewHolder>{

    ArrayList<ModelShopMenu> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterServiceShopList(ArrayList<ModelShopMenu> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterServiceShopList(ArrayList<ModelShopMenu> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_service_list, parent, false);
        ServiceListViewHolder serviceListViewHolder = new ServiceListViewHolder(view);
        return serviceListViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {

        Picasso.with(holder.listImageView.getContext())
                .load(adapterList.get(position).getService_shop_icon())
                .placeholder(R.drawable.ic_placeholder_icon)
                .error(R.drawable.ic_placeholder_icon)
                .into(holder.listImageView);
        holder.listImageView.setColorFilter(Color.parseColor(adapterList.get(position).getService_shop_color()));
        holder.listName.setText(adapterList.get(position).getService_shop_name());
        holder.listName.setTextColor(Color.parseColor("#666666"));
        Log.d("SHOP NAME", adapterList.get(position).getService_shop_name());

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView listImageView;
        TextView listName;

        public ServiceListViewHolder(View itemView) {
            super(itemView);

            listImageView = (ImageView) itemView.findViewById(R.id.listImageView);
            listName = (TextView) itemView.findViewById(R.id.listName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (adapterList.get(getAdapterPosition()).getService_shop_type().equals("category")){
                Intent intent = new Intent(v.getContext(), ShopCategory.class);
                intent.putExtra("SERVICE_SHOP_ID", adapterList.get(getAdapterPosition()).getService_shop_id());
                intent.putExtra("SERVICE_SHOP_NAME", adapterList.get(getAdapterPosition()).getService_shop_name());
                v.getContext().startActivity(intent);
            } else if(adapterList.get(getAdapterPosition()).getService_shop_type().equals("non_category")){
                Log.d("SAIM SAIM", adapterList.get(getAdapterPosition()).getService_shop_id());
                Intent intent = new Intent(v.getContext(), ShopItemList.class);
                intent.putExtra("service_shop_id", adapterList.get(getAdapterPosition()).getService_shop_id());
                intent.putExtra("service_shop_type", adapterList.get(getAdapterPosition()).getService_shop_type());
                v.getContext(). startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "We are sorry something wrong!!!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
