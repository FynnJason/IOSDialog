package com.fynnjason.library.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.fynnjason.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author：FynnJason
 * copyright：© 2017 Android.Own.
 */

public class IOSDialog extends DialogFragment {

    private View view;
    private TextView mTvTitle;
    private RecyclerView mRvOptions;
    private Button mBtnCancel;
    private optionsAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private String mTitle;
    private View.OnClickListener mCancelListener;
    private optionsAdapter.OnItemClickListener mOnItemClickListener;

    public IOSDialog(List<String> mList, String mTitle) {
        this.mList = mList;
        this.mTitle = mTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View decorView = getDialog().getWindow().getDecorView();
        decorView.setBackground(new ColorDrawable(Color.TRANSPARENT));

        //底部显示
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);

        view = inflater.inflate(R.layout.layout_ios_dialog, container, false);
        slideUp();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        mTvTitle = view.findViewById(R.id.tv_dialog_title);
        mBtnCancel = view.findViewById(R.id.btn_dialog_cancel);
        mRvOptions = view.findViewById(R.id.rv_dialog_options);

        mTvTitle.setText(mTitle);
        mAdapter = new optionsAdapter(getContext(), mList);
        mRvOptions.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOptions.setAdapter(mAdapter);

        mBtnCancel.setOnClickListener(mCancelListener);
        mAdapter.setOnItemClickListener(mOnItemClickListener);

        setCancelable(false);
    }

    public void setCancelListener(View.OnClickListener onClickListener) {
        mCancelListener = onClickListener;
    }

    public void setOptionsListener(optionsAdapter.OnItemClickListener optionsListener) {
        mOnItemClickListener = optionsListener;
    }


    private void slideUp() {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f);

        animation.setDuration(400);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        view.startAnimation(animation);
    }

    public void slideDown() {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f);

        animation.setDuration(400);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        view.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                IOSDialog.this.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 适配器
     */
    public static class optionsAdapter extends RecyclerView.Adapter<optionsAdapter.MyViewHolder> {
        private Context mContext;
        private List<String> mList;

        public optionsAdapter(Context mContext, List<String> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_options, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.mTvOptions.setText(mList.get(position));
            if (onItemClickListener != null) {
                //单击监听
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });


            if (onItemLongClickListener != null) {
                //长按监听
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onItemLongClickListener.onItemLongClick(holder.itemView, position);
                        return true;
                    }
                });
            }
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView mTvOptions;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTvOptions = itemView.findViewById(R.id.tv_item_dialog_options);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public interface OnItemLongClickListener {
            void onItemLongClick(View view, int position);
        }

        private OnItemClickListener onItemClickListener;
        private OnItemLongClickListener onItemLongClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
            this.onItemLongClickListener = onItemLongClickListener;
        }
    }
}
