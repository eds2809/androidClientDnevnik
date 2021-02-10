package ru.eds2809.dnevnik.service.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.eds2809.dnevnik.models.User;

public interface UserApiPlaceholder {
    @FormUrlEncoded
    @POST("/user/auth")
    Call<User> auth(@Field("login") String login,
                    @Field("password") String password);

    @GET("/user/{userId}")
    Call<User> getUserById(@Path("userId") long userId);

    @GET("/user/all")
    Call<List<User>> getAllUsers();
}
