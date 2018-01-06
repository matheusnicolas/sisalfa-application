package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.User;

/**
 * Created by Pichau on 04/01/2018.
 */

public interface UsuarioService {

    @GET("profiles/{id}")
    Call<User> getUsuario(@Path("id") int id);

    @POST("profiles")
    Call<Boolean> insertUser(@Body User user);
}
