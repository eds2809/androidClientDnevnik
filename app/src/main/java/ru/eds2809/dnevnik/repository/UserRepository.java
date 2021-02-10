package ru.eds2809.dnevnik.repository;

import java.util.List;

import ru.eds2809.dnevnik.models.User;
import ru.eds2809.dnevnik.service.NetworkService;
import ru.eds2809.dnevnik.service.common.HttpResponse;

public class UserRepository {

    private static UserRepository mInstance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (mInstance == null) {
            mInstance = new UserRepository();
        }
        return mInstance;
    }

    public void auth(String login, String password, HttpResponse.HttpCallBack<User> httpCallBack) {
        NetworkService.getInstance()
                .UserApi()
                .auth(login, password)
                .enqueue(new HttpResponse<>(httpCallBack));
    }

    public void getUserById(long id, HttpResponse.HttpCallBack<User> httpCallBack) {
        NetworkService.getInstance()
                .UserApi()
                .getUserById(id)
                .enqueue(new HttpResponse<>(httpCallBack));
    }

    public void getAllUsers(HttpResponse.HttpCallBack<List<User>> httpCallBack) {
        NetworkService.getInstance()
                .UserApi()
                .getAllUsers()
                .enqueue(new HttpResponse<>(httpCallBack));
    }
}
