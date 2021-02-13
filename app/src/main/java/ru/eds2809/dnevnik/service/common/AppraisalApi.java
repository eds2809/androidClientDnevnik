package ru.eds2809.dnevnik.service.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.eds2809.dnevnik.models.Appraisal;

public interface AppraisalApi {
    @GET("/appraisal/{userId}/{subjectId}")
    Call<List<Appraisal>> getAppraisalByUserIdAndSubjectId(@Path("userId") long userId, @Path("subjectId") long subjectId);

    @POST("/appraisal/")
    Call<Appraisal> put(@Body Appraisal appraisal);

    @DELETE("/appraisal/{id}")
    Call<String> delete(@Path("id") long id);
}
