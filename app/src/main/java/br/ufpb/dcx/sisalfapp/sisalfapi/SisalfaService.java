package br.ufpb.dcx.sisalfapp.sisalfapi;

import java.util.List;

import br.ufpb.dcx.sisalfapp.model.Challenge;
import br.ufpb.dcx.sisalfapp.model.ContextM;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import br.ufpb.dcx.sisalfapp.model.User;

/**
 * Created by Pichau on 04/01/2018.
 */

public interface SisalfaService {

    @POST("addContext")
    Call<Boolean> addContext(@Body ContextM contextM);

    @GET("getAllContexts")
    Call<List<ContextM>> getAllContexts();

    @GET("getContextFromUser/{idUser}")
    Call<List<ContextM>> getContextFromUser(@Path("idUser") long id);

    @GET("getContext/{id}")
    Call<ContextM> getContext(@Path("id") long id);

    @PUT("context/{id}")
    Call<Boolean> updateContext(@Path("id") long id, @Body ContextM contextM);

    @DELETE("deleteContext/{id}")
    Call<Boolean> deleteContext(@Path("id") long id);

    @POST("addChallenge")
    Call<Boolean> addChallenge(@Body Challenge challenge);

    @GET("getAllChallenges")
    Call<List<Challenge>> getAllChallenges();

    @GET("getChallenge/{id}")
    Call<Challenge> getChallenge(@Path("id") long id);

    @GET("getChallengeFromContext/{id}")
    Call<Challenge> getChallengeFromContext(@Path("id") long id);

    @GET("getChallengeFromUser/{id}")
    Call<List<Challenge>> getChallengeFromUser(@Path("id") long id);

    @PUT("challenge/{id}")
    Call<Boolean> updateChallenge(@Path("id") long id, @Body Challenge challenge);

    @DELETE("deleteChallenge/{id}")
    Call<Boolean> deleteChallenge(@Path("id") long id);

    @GET("profiles/{idUser}")
    Call<User> getUser(@Path("idUser") long id);

    @POST("profileS")
    Call<Boolean> addUser(@Body User user);

    @GET("getAllusers")
    Call<List<User>> getAllUsers();

    @PUT("user/{id}")
    Call<Boolean> updateUser(@Path("id") long id, @Body User user);

    @DELETE("deleteUser/{id}")
    Call<Boolean> deleteUser(@Path("id") long id);


}
