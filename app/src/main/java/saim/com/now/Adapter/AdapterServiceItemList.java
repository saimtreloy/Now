package saim.com.now.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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

import saim.com.now.Database.DatabaseHandler;
import saim.com.now.Model.ModelItemList;
import saim.com.now.Model.ModelShopMenu;
import saim.com.now.R;
import saim.com.now.Shop.ShopCategory;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterServiceItemList extends RecyclerView.Adapter<AdapterServiceItemList.ServiceListViewHolder>{

    ArrayList<ModelItemList> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterServiceItemList(ArrayList<ModelItemList> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterServiceItemList(ArrayList<ModelItemList> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_service_item_list, parent, false);
        ServiceListViewHolder serviceListViewHolder = new ServiceListViewHolder(view);
        return serviceListViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {

        Picasso.with(holder.listImageView.getContext())
                .load(adapterList.get(position).getItem_icon())
                .placeholder(R.drawable.ic_placeholder_icon)
                .error(R.drawable.ic_placeholder_icon)
                .into(holder.listImageView);

        Picasso.with(holder.listVendor.getContext())
                .load(adapterList.get(position).getItem_vendor_icon())
                .placeholder(R.drawable.ic_placeholder_icon)
                .error(R.drawable.ic_placeholder_icon)
                .into(holder.listVendor);
        //holder.listImageView.setColorFilter(Color.parseColor(adapterList.get(position).getService_shop_color()));

        holder.listName.setText(adapterList.get(position).getItem_name());
        holder.listQuentity.setText(adapterList.get(position).getItem_quantity());
        holder.listPrice.setText(adapterList.get(position).getItem_price() + "tk");
        holder.listPriceD.setText(adapterList.get(position).getItem_d_price() + "tk");

        DatabaseHandler db = new DatabaseHandler(holder.listItemQ.getContext());
        if (db.getAllContacts().size() > 0){
            for (int i=0; i<db.getAllContacts().size(); i++){
                if (db.getAllContacts().get(i).getId().equals(adapterList.get(position).getId())){
                    holder.listItemQ.setVisibility(View.VISIBLE);
                    holder.listItemQ.setText(db.getAllContacts().get(i).getCartQ() + "");
                    holder.imgButtonSubtract.setVisibility(View.VISIBLE);
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int itemQ = 0;

        ImageView listImageView, listVendor, imgButtonAdd, imgButtonSubtract;
        TextView listName, listQuentity, listPrice, listPriceD, listItemQ;

        public ServiceListViewHolder(View itemView) {
            super(itemView);

            listImageView = (ImageView) itemView.findViewById(R.id.listImageView);
            listVendor = (ImageView) itemView.findViewById(R.id.listVendor);
            imgButtonAdd = (ImageView) itemView.findViewById(R.id.imgButtonAdd);
            imgButtonSubtract = (ImageView) itemView.findViewById(R.id.imgButtonSubtract);

            listName = (TextView) itemView.findViewById(R.id.listName);
            listQuentity = (TextView) itemView.findViewById(R.id.listQuentity);
            listPrice = (TextView) itemView.findViewById(R.id.listPrice);
            listPrice.setPaintFlags(listPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            listPriceD = (TextView) itemView.findViewById(R.id.listPriceD);
            listItemQ = (TextView) itemView.findViewById(R.id.listItemQ);

            if (itemQ > 0){
                listItemQ.setVisibility(View.VISIBLE);
                imgButtonSubtract.setVisibility(View.VISIBLE);
            } else {
                listItemQ.setVisibility(View.GONE);
                imgButtonSubtract.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(this);


            imgButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemQ = Integer.parseInt(listItemQ.getText().toString());
                    itemQ++;

                    Intent intent = new Intent("com.moodybugs.banglamusic.StopService");
                    intent.putExtra("KEY_ID", adapterList.get(getAdapterPosition()).getId());
                    intent.putExtra("KEY_ITEM_ID", adapterList.get(getAdapterPosition()).getItem_id());
                    intent.putExtra("KEY_NAME", adapterList.get(getAdapterPosition()).getItem_name());
                    intent.putExtra("KEY_PRICE", adapterList.get(getAdapterPosition()).getItem_price());
                    intent.putExtra("KEY_PRICE_D", adapterList.get(getAdapterPosition()).getItem_d_price());
                    intent.putExtra("KEY_QUANTITY", adapterList.get(getAdapterPosition()).getItem_quantity());
                    intent.putExtra("KEY_ICON", adapterList.get(getAdapterPosition()).getItem_icon());
                    intent.putExtra("KEY_VENDOR", adapterList.get(getAdapterPosition()).getItem_vendor());
                    intent.putExtra("KEY_VENDOR_ICON", adapterList.get(getAdapterPosition()).getItem_vendor_icon());
                    intent.putExtra("KEY_CART_Q", itemQ + "");

                    listItemQ.setVisibility(View.VISIBLE);
                    listItemQ.setText(itemQ + "");
                    imgButtonSubtract.setVisibility(View.VISIBLE);

                    v.getContext().sendBroadcast(intent);
                }
            });

            imgButtonSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemQ = Integer.parseInt(listItemQ.getText().toString());
                    itemQ--;

                    Intent intent = new Intent("com.moodybugs.banglamusic.StopService");
                    intent.putExtra("KEY_ID", adapterList.get(getAdapterPosition()).getId());
                    intent.putExtra("KEY_ITEM_ID", adapterList.get(getAdapterPosition()).getItem_id());
                    intent.putExtra("KEY_NAME", adapterList.get(getAdapterPosition()).getItem_name());
                    intent.putExtra("KEY_PRICE", adapterList.get(getAdapterPosition()).getItem_price());
                    intent.putExtra("KEY_PRICE_D", adapterList.get(getAdapterPosition()).getItem_d_price());
                    intent.putExtra("KEY_QUANTITY", adapterList.get(getAdapterPosition()).getItem_quantity());
                    intent.putExtra("KEY_ICON", adapterList.get(getAdapterPosition()).getItem_icon());
                    intent.putExtra("KEY_VENDOR", adapterList.get(getAdapterPosition()).getItem_vendor());
                    intent.putExtra("KEY_VENDOR_ICON", adapterList.get(getAdapterPosition()).getItem_vendor_icon());
                    intent.putExtra("KEY_CART_Q", itemQ + "");

                    listItemQ.setVisibility(View.VISIBLE);
                    listItemQ.setText(itemQ + "");
                    imgButtonSubtract.setVisibility(View.VISIBLE);

                    v.getContext().sendBroadcast(intent);

                    if (itemQ > 0){
                        listItemQ.setVisibility(View.VISIBLE);
                        imgButtonSubtract.setVisibility(View.VISIBLE);
                    } else {
                        listItemQ.setVisibility(View.GONE);
                        imgButtonSubtract.setVisibility(View.GONE);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {


        }
    }
}
