package demo.app.cryptocurrencies.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import demo.app.cryptocurrencies.R;
import demo.app.cryptocurrencies.model.Coins;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.ViewHolder> {
    private Context mContext;
    private List<Coins> mCouponsList;
    private Activity activity;

    public CoinsAdapter(Context mContext, List<Coins> resultsList, Activity activity) {
        this.mContext = mContext;
        this.mCouponsList = resultsList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        android.view.View view = LayoutInflater.from(mContext).inflate(R.layout.item_coins_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull final CoinsAdapter.ViewHolder viewHolder, int i) {
        final Coins coins = mCouponsList.get(viewHolder.getAdapterPosition());

        viewHolder.txtNumber.setText(String.valueOf(i + 1));

        if (!TextUtils.isEmpty(coins.getName()))
            viewHolder.txtName.setText(coins.getName());

        if (!TextUtils.isEmpty(coins.getSymbol()))
            viewHolder.txtSymbol.setText(coins.getSymbol());

        if (!TextUtils.isEmpty(coins.getPrice()))
            viewHolder.txtPrice.setText("$ ".concat(String.format(Locale.getDefault(), "%.2f", Float.parseFloat(coins.getPrice()))));

        if (!TextUtils.isEmpty(coins.getBtcPrice()))
            viewHolder.txtBitPrice.setText("$ ".concat(String.format(Locale.getDefault(), "%.2f", Float.parseFloat(coins.getBtcPrice())).concat("B")));

        if (!TextUtils.isEmpty(coins.getChange())) {
            if (coins.getChange().contains("-")) {
                viewHolder.txtChanges.setText(String.format(Locale.getDefault(), "%.2f", Float.parseFloat(coins.getChange())).concat("%"));
                viewHolder.txtChanges.setTextColor(mContext.getResources().getColor(R.color.red));
                //for spark line
                viewHolder.sparkview.setLineColor(mContext.getResources().getColor(R.color.red));
            } else {
                viewHolder.txtChanges.setText("+".concat(String.format(Locale.getDefault(), "%.2f", Float.parseFloat(coins.getChange())).concat("%")));
                viewHolder.txtChanges.setTextColor(mContext.getResources().getColor(R.color.green));
                //for spark line
                viewHolder.sparkview.setLineColor(mContext.getResources().getColor(R.color.green));
            }
        }

        if (!coins.getSparkline().isEmpty()) {
            viewHolder.sparkview.setVisibility(View.VISIBLE);
            viewHolder.sparkview.setAdapter(new MyAdapter(coins.getSparkline()));
        } else
            viewHolder.sparkview.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(coins.getIconUrl())) {
            viewHolder.progress_bar.setVisibility(android.view.View.VISIBLE);
            // this method will load svg
            //Utils.fetchSvg(mContext, coins.getIconUrl(), viewHolder.imgCoins, viewHolder.progress_bar);
            SvgLoader.pluck()
                    .with(activity)
                    .setPlaceHolder(R.color.grey_20, R.color.grey_20)
                    .load(coins.getIconUrl(), viewHolder.imgCoins);
            viewHolder.progress_bar.setVisibility(View.GONE);
        } else
            viewHolder.progress_bar.setVisibility(android.view.View.GONE);
    }

    @Override
    public int getItemCount() {
        return mCouponsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNumber, txtName, txtSymbol;
        private TextView txtPrice, txtBitPrice, txtChanges;
        private RoundedImageView imgCoins;
        private ProgressBar progress_bar;
        private SparkView sparkview;

        public ViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
            super(itemView);

            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtName = itemView.findViewById(R.id.txtName);
            txtSymbol = itemView.findViewById(R.id.txtSymbol);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtBitPrice = itemView.findViewById(R.id.txtBitPrice);
            txtChanges = itemView.findViewById(R.id.txtChanges);
            imgCoins = itemView.findViewById(R.id.imgCoins);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            sparkview = itemView.findViewById(R.id.sparkview);
        }
    }

    public static class MyAdapter extends SparkAdapter {
        private List<String> yData;

        public MyAdapter(List<String> yData) {
            this.yData = yData;
        }

        @Override
        public int getCount() {
            return yData.size();
        }

        @Override
        public Object getItem(int index) {
            return yData.get(index);
        }

        @Override
        public float getY(int index) {
            Log.e("getY: ", yData.get(index));
            return Float.parseFloat(yData.get(index));
        }
    }
}
