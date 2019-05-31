package com.ibs.android.ibssmarthome.Adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.Object.RequestObject;
import com.ibs.android.ibssmarthome.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private Context mContext;
    private ArrayList<RequestObject> mItemList;
    private OnItemClickListener mListener;

    public RequestAdapter(Context context, ArrayList<RequestObject> itemList)
    {
        mContext=context;
        mItemList=itemList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemClick(int position,TextView requestSubject, TextView requestDate,TextView requestContent,ImageView requestStatus);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public RequestAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_request,parent,false);
        //v.getLayoutParams().height = (getScreenHeiht() / 3);
        return new RequestViewHolder(v);
    }

    public int getScreenHeiht() {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }


    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.RequestViewHolder holder, int position) {
        RequestObject requestObject =mItemList.get(position);
        holder.mTvRequestSubject.setText(requestObject.getRequestSubject());

        SimpleDateFormat spf=new SimpleDateFormat("MMM dd");
        String date= spf.format(requestObject.getRequestDate());
        holder.mTvRequestDate.setText(date);

        holder.mTvRequestContent.setText(requestObject.getRequestContent());

        switch (requestObject.getStatus()){
            case 0: Picasso.get().load(R.drawable.process_64px).fit().centerCrop().into(holder.mIvRequestStatus);
                break;
            case 1: Picasso.get().load(R.drawable.ok_64px).fit().centerCrop().into(holder.mIvRequestStatus);
                break;
            case 2: Picasso.get().load(R.drawable.cancel_64px).fit().centerCrop().into(holder.mIvRequestStatus);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvRequestSubject;
        private TextView mTvRequestDate;
        private TextView mTvRequestContent;
        private ImageView mIvRequestStatus;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvRequestSubject   = itemView.findViewById(R.id.textview_request_title);
            mTvRequestDate      = itemView.findViewById(R.id.textview_request_date);
            mTvRequestContent   = itemView.findViewById(R.id.textview_request_content);
            mIvRequestStatus    = itemView.findViewById(R.id.imageview_request_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position,mTvRequestSubject,mTvRequestDate,mTvRequestContent,mIvRequestStatus);
                        }
                    }
                }
            });
        }
    }
}
