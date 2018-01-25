package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Contexto;

/**
 * Created by Pichau on 04/01/2018.
 */

public interface ContextoService {

    @GET("contextos")
    Call<List<Contexto>> getContextos();

    @POST("contextos")
    Call<Boolean> insertContexto(@Body Contexto contexto);
}
