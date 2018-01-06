package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Desafio;


public interface DesafioService {

    @GET("desafios")
    Call<List<Desafio>> getDesafios();

    @POST("desafios")
    Call<Boolean> insertDesafio(@Body Desafio desafio);




}
