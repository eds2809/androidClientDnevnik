package ru.eds2809.dnevnik.service.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.eds2809.dnevnik.models.Appraisal;

public interface AppraisalApi {
    @GET("/appraisal/{userId}/{subjectId}")
    Call<List<Appraisal>> getAppraisalByUserIdAndSubjectId(@Path("userId") long userId, @Path("subjectId") long subjectId);
}
