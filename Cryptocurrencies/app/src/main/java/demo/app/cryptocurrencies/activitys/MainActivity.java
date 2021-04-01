package demo.app.cryptocurrencies.activitys;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import demo.app.cryptocurrencies.R;
import demo.app.cryptocurrencies.adapter.CoinsAdapter;
import demo.app.cryptocurrencies.model.Coins;
import demo.app.cryptocurrencies.model.GetCoins;
import demo.app.cryptocurrencies.retrofit.ServerResponse;
import demo.app.cryptocurrencies.retrofit.Singleton;
import demo.app.cryptocurrencies.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ShimmerFrameLayout mShimmerFrameLayout;
    private RecyclerView mRecyclerView;
    private TextView mTextViewNoServices;
    private List<Coins> mResultsList;
    private CoinsAdapter mCoinsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progress_bar;
    private TextView txtCryptocurrency, txtPrice, txt_24_h;

    private boolean isLoadMore = false;
    private int offset = 50, totalItemCount;
    private String orderBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityContext(this);
        initView();
    }

    // initialize view
    private void initView() {
        progress_bar = findViewById(R.id.progress_bar);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mShimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        mRecyclerView = findViewById(R.id.rlServices);
        mTextViewNoServices = findViewById(R.id.textViewServices);
        txtCryptocurrency = findViewById(R.id.txtCryptocurrency);
        txtPrice = findViewById(R.id.txtPrice);
        txt_24_h = findViewById(R.id.txt_24_h);

        txtCryptocurrency.setOnClickListener(this);
        txtPrice.setOnClickListener(this);
        txt_24_h.setOnClickListener(this);

        mResultsList = new ArrayList<>();
        //set list adapter
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCoinsAdapter = new CoinsAdapter(this, mResultsList, this);
        //mMoviesAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mCoinsAdapter);

        mShimmerFrameLayout.startShimmerAnimation();
        mShimmerFrameLayout.setVisibility(View.VISIBLE);

        orderBy = Constants.COINS_ORDER_BY_MARKETCAP;
        callApiAndGetCoinsList(true, orderBy);
        //for pagination
        loadMore();
    }

    /*
     * api call to get coins List
     * */
    private void callApiAndGetCoinsList(boolean showDialog, String coinsOrderBy) {
        hideKeyboard();
        if (!isInternetConnected()) {
            if (showDialog)
                makeToast(getString(R.string.internet_error));

            //check if network not available
            progress_bar.setVisibility(View.GONE);
            mShimmerFrameLayout.stopShimmerAnimation();
            mShimmerFrameLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mTextViewNoServices.setVisibility(View.VISIBLE);
            return;
        }

        if (showDialog)
            showProgress();

        Call<ServerResponse<GetCoins>> responseCall = Singleton.getInstance().getRestClient()
                .callApiAndGetCoins(coinsOrderBy, "asc", String.valueOf(offset));
        responseCall.enqueue(new Callback<ServerResponse<GetCoins>>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse<GetCoins>> call, @NonNull Response<ServerResponse<GetCoins>> response) {
                if (response.isSuccessful()) {
                    progress_bar.setVisibility(View.GONE);
                    mShimmerFrameLayout.stopShimmerAnimation();
                    mShimmerFrameLayout.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    hideProgress();
                    ServerResponse serverResponse = response.body();
                    if (serverResponse != null) {
                        GetCoins getCoins = (GetCoins) serverResponse.getData();
                        if (getCoins.getCoins() != null && !getCoins.getCoins().isEmpty()) {
                            offset += 50;

                            mResultsList.addAll(getCoins.getCoins());
                            notifyAdapter();
                        }
                    }
                } else {
                    progress_bar.setVisibility(View.GONE);
                    hideProgress();
                    if (response.body() != null && response.body().getMessage() != null)
                        makeToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse<GetCoins>> call, @NonNull Throwable t) {
                progress_bar.setVisibility(View.GONE);
                hideProgress();
                if (t instanceof IOException) {
                    makeToast(getResources().getString(R.string.internet_error));
                } else {
                    makeToast(getResources().getString(R.string.something_went_wrong));
                }
            }
        });
    }

    private void notifyAdapter() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

        mCoinsAdapter.notifyDataSetChanged();
        // for load more data
        isLoadMore = false;

        if (mResultsList.size() > 0) {
            mTextViewNoServices.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mTextViewNoServices.setVisibility(View.VISIBLE);
        }
    }

    private void clearListAdapter() {
        offset = 50;
        mResultsList.clear();
        mCoinsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        clearListAdapter();

        callApiAndGetCoinsList(false, orderBy);
    }

    /**
     * load more items
     */
    private void loadMore() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) > totalItemCount - 1) {

                    if (!isLoadMore) {
                        isLoadMore = true;
                        progress_bar.setVisibility(View.VISIBLE);
                        // callback API to loadmoredata
                        callApiAndGetCoinsList(false, orderBy);
                    }

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txtCryptocurrency) {
            clearListAdapter();
            orderBy = Constants.COINS_ORDER_BY_MARKETCAP;
            callApiAndGetCoinsList(true, orderBy);
        } else if (id == R.id.txtPrice) {
            clearListAdapter();
            orderBy = Constants.COINS_ORDER_BY_PRICE;
            callApiAndGetCoinsList(true, orderBy);
        } else if (id == R.id.txt_24_h) {
            clearListAdapter();
            orderBy = Constants.COINS_ORDER_BY_24H_VOLUME;
            callApiAndGetCoinsList(true, orderBy);
        }
    }
}