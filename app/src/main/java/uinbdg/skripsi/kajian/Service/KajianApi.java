package uinbdg.skripsi.kajian.Service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.POST;
import uinbdg.skripsi.kajian.Model.KajianResponse;
import uinbdg.skripsi.kajian.Model.MosqueResponse;


public interface KajianApi {

    @GET("kajian")
    Call<KajianResponse> tampilKajian();

    @FormUrlEncoded
    @POST("kajian")
    Call<KajianResponse> tampilKajianByCurrentDate(@Field("date")String date);

    @GET("mosques")
    Call<MosqueResponse> tampilMosque();


}
