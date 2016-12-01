package timothyyudi.tryendless;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 11/22/2016.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    Context mCtx;
    ArrayList<DummyModel> mList;

    public MyRecyclerViewAdapter(Context mCtx, ArrayList<DummyModel> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_template_xx,parent,false);
        DummyViewHolder mDummyViewHolder = new DummyViewHolder(v);
        return mDummyViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DummyViewHolder)holder).ivCardImage.setImageResource(R.mipmap.ic_launcher);
        ((DummyViewHolder)holder).tvTitle.setText(mList.get(position).title);
        ((DummyViewHolder)holder).tvContent.setText(mList.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class DummyViewHolder extends RecyclerView.ViewHolder{

        CardView mCardView;
        TextView tvTitle;
        TextView tvContent;
        ImageView ivCardImage;

        public DummyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            ivCardImage = (ImageView) itemView.findViewById(R.id.ivCardImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }

}
