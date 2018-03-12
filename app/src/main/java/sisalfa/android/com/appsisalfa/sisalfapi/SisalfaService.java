package sisalfa.android.com.appsisalfa.sisalfapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sisalfa.android.com.appsisalfa.model.Contexto;
import sisalfa.android.com.appsisalfa.model.Desafio;
import sisalfa.android.com.appsisalfa.model.User;

/**
 * Created by Pichau on 04/01/2018.
 */

public interface SisalfaService {

    @GET("contextos")
    Call<List<Contexto>> getContextos();

    @POST("contextos")
    Call<Boolean> insertContexto(@Body Contexto contexto);




    @GET("desafios")
    Call<List<Desafio>> getDesafios();

    @POST("desafios")
    Call<Boolean> insertDesafio(@Body Desafio desafio);





    @GET("profiles/{id}")
    Call<User> getUsuario(@Path("id") int id);

    @POST("profiles")
    Call<Boolean> insertUser(@Body User user);
}
