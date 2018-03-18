package sisalfa.android.com.appsisalfa.sisalfapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import sisalfa.android.com.appsisalfa.model.Contexto;
import sisalfa.android.com.appsisalfa.model.Desafio;
import sisalfa.android.com.appsisalfa.model.User;

/**
 * Created by Pichau on 04/01/2018.
 */

public interface SisalfaService {

    @POST("addContext")
    Call<Boolean> addContext(@Body Contexto contexto);

    @GET("getAllContexts")
    Call<List<Contexto>> getAllContexts();

    @GET("getContextFromUser/{idUser}")
    Call<List<Contexto>> getContextFromUser(@Path("idUser") long id);

    @GET("getContext/{id}")
    Call<Contexto> getContext(@Path("id") long id);

    @PUT("context/{id}")
    Call<Boolean> updateContext(@Path("id") long id, @Body Contexto contexto);

    @DELETE("deleteContext/{id}")
    Call<Boolean> deleteContext(@Path("id") long id);



    @POST("addChallenge")
    Call<Boolean> addChallenge(@Body Desafio desafio);

    @GET("getAllChallenges")
    Call<List<Desafio>> getAllChallenges();

    @GET("getChallenge/{id}")
    Call<Desafio> getChallenge(@Path("id") long id);

    @GET("getChallengeFromContext/{id}")
    Call<Desafio> getChallengeFromContext(@Path("id") long id);

    @GET("getChallengeFromUser/{id}")
    Call<List<Desafio>> getChallengeFromUser(@Path("id") long id);

    @PUT("challenge/{id}")
    Call<Boolean> updateChallenge(@Path("id") long id, @Body Desafio desafio);

    @DELETE("deleteChallenge/{id}")
    Call<Boolean> deleteChallenge(@Path("id") long id);



    @GET("profiles/{idUser}")
    Call<User> getUser(@Path("idUser") long id);

    @POST("profiles")
    Call<Boolean> addUser(@Body User user);

    @GET("getAllusers")
    Call<List<User>> getAllUsers();

    @PUT("user/{id}")
    Call<Boolean> updateUser(@Path("id") long id, @Body User user);

    @DELETE("deleteUser/{id}")
    Call<Boolean> deleteUser(@Path("id") long id);


}
