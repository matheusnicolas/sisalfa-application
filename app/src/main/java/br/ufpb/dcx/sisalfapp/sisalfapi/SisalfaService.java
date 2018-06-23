package br.ufpb.dcx.sisalfapp.sisalfapi;

import java.util.List;

import br.ufpb.dcx.sisalfapp.model.Challenge;
import br.ufpb.dcx.sisalfapp.model.ContextM;
import br.ufpb.dcx.sisalfapp.model.Login;
import br.ufpb.dcx.sisalfapp.model.Token;
import br.ufpb.dcx.sisalfapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Pichau on 04/01/2018.
 */

public interface SisalfaService {

    @POST("users/login")
    Call<Token> authenticate(@Body Login login);

    @POST("users/login")
    Call<User> getUserInformation(@Body String token);

    @POST("users/")
    Call<User> addUser(@Body User user);

    @GET("contexts/")
    Call<List<ContextM>> getAllContexts();

    @POST("contexts/")
    Call<ContextM> addContext(@Body ContextM contextM);

    @GET("challenges/")
    Call<List<Challenge>> getAllChallenges();

    @POST("challenges/")
    Call<Challenge> addChallenge(@Body Challenge challenge);

    @GET("users/{username}/")
    Call<User> getUser(@Path("username") String username);
    /*

    @GET("getChallengeFromContext/{id}")
    Call<Challenge> getChallengeFromContext(@Path("id") long id);

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
    Call<Challenge> addChallenge(@Body Challenge challenge);

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

    @POST("profiles")
    Call<User> addUser(@Body User user);

    @GET("getAllusers")
    Call<List<User>> getAllUsers();

    @PUT("user/{id}")
    Call<Boolean> updateUser(@Path("id") long id, @Body User user);

    @DELETE("deleteUser/{id}")
    Call<Boolean> deleteUser(@Path("id") long id);
    */


}
