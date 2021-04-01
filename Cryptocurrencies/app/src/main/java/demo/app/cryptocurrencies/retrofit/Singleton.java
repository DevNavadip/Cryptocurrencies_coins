package demo.app.cryptocurrencies.retrofit;

public class Singleton {
    private final static String TAG = Singleton.class.getSimpleName();
    private static Singleton mInstance;
    private static ServiceGenerator mRestGenerator;

    public static Singleton getInstance() {
        //if (mInstance == null) {
        mInstance = new Singleton();
        // }
        return mInstance;
    }

    public Singleton() {
        mRestGenerator = new ServiceGenerator();
    }

    public RestClient getRestClient() {
        return mRestGenerator.getRestClient();
    }
}
