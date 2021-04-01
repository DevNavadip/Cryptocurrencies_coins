package demo.app.cryptocurrencies.retrofit;

import demo.app.cryptocurrencies.model.GetCoins;
import demo.app.cryptocurrencies.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestClient {

    @GET(Constants.API_URL_COINS_LIST)
    Call<ServerResponse<GetCoins>>
    callApiAndGetCoins(@Query("orderBy") String orderBy,
                       @Query("orderDirection") String orderDirection,
                       @Query("offset") String offset);

}
