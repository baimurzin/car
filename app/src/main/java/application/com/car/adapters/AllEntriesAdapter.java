package application.com.car.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import application.com.car.AppDelegate;
import application.com.car.R;
import application.com.car.entity.ItemRoute;

/**
 * Created by Zahit Talipov on 22.01.2016.
 */
public class AllEntriesAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<ItemRoute> itemRoutes = AppDelegate.itemRoutes;
    View rootView;
    ImageView imageView;

    public AllEntriesAdapter(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemRoutes.size();
    }

    public void add(ItemRoute itemRoute) {
        itemRoutes.add(itemRoute);
        notifyDataSetChanged();
    }

    @Override
    public ItemRoute getItem(int position) {
        return itemRoutes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = inflater.inflate(R.layout.item_all_routes, parent, false);
        ItemRoute route = getItem(position);
        imageView = (ImageView) view.findViewById(R.id.imageViewRoute);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        TextView textViewTime = (TextView) view.findViewById(R.id.textViewTime);
        TextView textViewAddress = (TextView) view.findViewById(R.id.textViewStartAddress);
        textViewDate.setText(route.getDate());
        textViewTime.setText(route.getTime());
        textViewAddress.setText(route.getAddressStart());
        Picasso.with(context).load(getURL(route.getRouteList())).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("r", "success");
            }

            @Override
            public void onError() {
                Log.d("r", "error");
            }
        });
        return view;
    }

    private String getURL(List<LatLng> latLngs) {
        StringBuilder buffer = new StringBuilder();
        StringBuilder bufferTemp = new StringBuilder();
        buffer.append("http://maps.google.com/maps/api/staticmap?");
        buffer.append("path=color:0x0000ff%7Cweight:2");
        boolean check = true;
        for (LatLng lng : latLngs) {
            bufferTemp.append("%7C" + lng.latitude + "," + lng.longitude);
        }
        int middle = bufferTemp.length() / latLngs.size();
        int step = latLngs.size() / (1800 / middle);
        if (step == 0)
            step = 1;
        int count = 0;
        step--;
        do {
            step++;
            count = 0;
            bufferTemp = new StringBuilder();
            for (LatLng lng : latLngs) {
                if (count % step == 0 || count == latLngs.size()-1)
                    bufferTemp.append("%7C" + lng.latitude + "," + lng.longitude);
                count++;
            }
        }
        while (bufferTemp.length() > 1800);
        buffer.append(bufferTemp.toString());
        buffer.append("&size=800x400");
        buffer.append("&markers=icon:http://goo.gl/Bng5kK%7C"+latLngs.get(0).latitude+","+latLngs.get(0).longitude);
        buffer.append("&markers=icon:http://goo.gl/W4s2fx%7C"+latLngs.get(latLngs.size()-1).latitude+","+latLngs.get(latLngs.size()-1).longitude);
        Log.d("url", buffer.toString());
        Log.d("size", buffer.length() + "");
        return buffer.toString();
    }

}
