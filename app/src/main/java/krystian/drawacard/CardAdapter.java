package krystian.drawacard;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import krystian.drawacard.http.data.Card;

public class CardAdapter extends BaseAdapter {

    private DisplayImageOptions displayImage = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.default_card)
            .showImageForEmptyUri(R.drawable.default_card)
            .showImageOnFail(R.drawable.default_card)
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .build();

    private Context context;
    private ArrayList<Card> cardArrayList;

    private static class ViewHolder {
        ImageView image;
        ProgressBar loading;
    }

    public CardAdapter(Context context, ArrayList<Card> cardArrayList) {
        this.context = context;
        this.cardArrayList = cardArrayList;
    }

    @Override
    public int getCount() {
        return cardArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            rowView = inflater.inflate(R.layout.card_adapter, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.card_image);

            viewHolder.loading = (ProgressBar) rowView
                    .findViewById(R.id.loading);
            rowView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)rowView.getTag();
        viewHolder.loading.setVisibility(View.GONE);
        setCardImage(viewHolder, cardArrayList.get(position));

        return rowView;
    }

    private void setCardImage(final ViewHolder viewHolder, Card card){
        ImageLoader.getInstance().displayImage(card.getImage(), viewHolder.image, displayImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                viewHolder.loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                viewHolder.loading.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                viewHolder.loading.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                viewHolder.loading.setVisibility(View.GONE);
            }
        });
    }

}
