package ru.eds2809.dnevnik.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.eds2809.dnevnik.Conf;
import ru.eds2809.dnevnik.service.common.AppraisalApi;
import ru.eds2809.dnevnik.service.common.UserApiPlaceholder;

public class NetworkService {

    private static NetworkService mInstance;
    private Retrofit mRetrofit;
    private UserApiPlaceholder userApiPlaceholder;
    private AppraisalApi appraisalApi;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Conf.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public UserApiPlaceholder UserApi() {
        if (userApiPlaceholder == null){
            userApiPlaceholder = mRetrofit.create(UserApiPlaceholder.class);
        }
        return userApiPlaceholder;
    }

    public AppraisalApi appraisalApi(){
        if (appraisalApi == null){
            appraisalApi = mRetrofit.create(AppraisalApi.class);
        }

        return appraisalApi;
    }

}
