package uinbdg.skirpsi.kajian.Service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.POST;
import uinbdg.skirpsi.kajian.Model.KajianResponse;


public interface KajianApi {

    @GET("kajian")
    Call<KajianResponse> tampilKajian();

    @FormUrlEncoded
    @POST("kajian")
    Call<KajianResponse> tampilKajianByCurrentDate(@Field("date")String date);


//    @POST("pemain")
//    Call<PemainDetailResponse> postPemain(@Body DataItemPemain pemain);
//
//    @PATCH("pemain/{id}")
//    Call<PemainDetailResponse> putPemain(@Path("id")int id,@Body DataItemPemain pemain);


}
