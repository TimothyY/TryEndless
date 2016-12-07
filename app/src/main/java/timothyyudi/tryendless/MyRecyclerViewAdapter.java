package timothyyudi.tryendless;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 11/22/2016.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    Context mCtx;
    ArrayList<DummyModel> mList;

    public MyRecyclerViewAdapter(Context mCtx, ArrayList<DummyModel> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_template_xx,parent,false);
        ViewHolder vh = new ViewHolder(v, new RVItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(mCtx, "Item "+mList.get(position).title+" clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemButtonClick(View v, int position) {
                Toast.makeText(mCtx, "Button "+mList.get(position).content+" clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View v, int position) {
                Toast.makeText(mCtx, "Item "+mList.get(position).title+" long clicked", Toast.LENGTH_SHORT).show();
            }

        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivCardImage.setImageResource(R.mipmap.ic_launcher);
        holder.tvTitle.setText(mList.get(position).title);
        holder.btnContent.setText(mList.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public CardView mCardView;
        public TextView tvTitle;
        public Button btnContent;
        public ImageView ivCardImage;
        public RVItemClickListener rvListener;

        public ViewHolder(View itemView, RVItemClickListener listener) {
            super(itemView);
            this.rvListener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            ivCardImage = (ImageView) itemView.findViewById(R.id.ivCardImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            btnContent = (Button) itemView.findViewById(R.id.btnContent);
            btnContent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view instanceof Button){ //currently handled by view type. can also by id.
                rvListener.onItemButtonClick(view,getAdapterPosition()); //to handle button only
            }else{
                rvListener.onItemClick(view, getAdapterPosition()); //to handle whole layout
            }
        }

        @Override
        public boolean onLongClick(View view) {
            rvListener.onLongItemClick(view, getAdapterPosition());
            return false;
        }
    }

}
