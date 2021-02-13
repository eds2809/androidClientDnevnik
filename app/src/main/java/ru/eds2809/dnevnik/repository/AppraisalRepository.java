package ru.eds2809.dnevnik.repository;

import java.util.List;

import ru.eds2809.dnevnik.models.Appraisal;
import ru.eds2809.dnevnik.service.NetworkService;
import ru.eds2809.dnevnik.service.common.HttpResponse;

public class AppraisalRepository {

    private static AppraisalRepository mInstance;

    private AppraisalRepository() {
    }

    public static AppraisalRepository getInstance() {
        if (mInstance == null) {
            mInstance = new AppraisalRepository();
        }
        return mInstance;
    }



    public void getAppraisalByUserIdAndSubjectId(long userId, long subjectId, HttpResponse.HttpCallBack<List<Appraisal>> httpCallBack){
        NetworkService.getInstance()
                .appraisalApi()
                .getAppraisalByUserIdAndSubjectId(userId, subjectId)
                .enqueue(new HttpResponse<>(httpCallBack));
    }

    public void put(Appraisal appraisal, HttpResponse.HttpCallBack<Appraisal> httpCallBack){
        NetworkService.getInstance()
                .appraisalApi()
                .put(appraisal)
                .enqueue(new HttpResponse<>(httpCallBack));
    }

    public void delete(long id, HttpResponse.HttpCallBack<String> httpCallBack) {
        NetworkService.getInstance()
                .appraisalApi()
                .delete(id)
                .enqueue(new HttpResponse<>(httpCallBack));
    }
}
