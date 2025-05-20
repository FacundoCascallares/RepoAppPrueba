package com.example.traveldreamsapp.network;
import com.google.gson.annotations.SerializedName;


import com.example.traveldreamsapp.models.Destinos;
import com.example.traveldreamsapp.models.RegisterRequest;
import com.example.traveldreamsapp.models.RegisterResponse;
import com.example.traveldreamsapp.models.PasswordResetRequest;
import com.example.traveldreamsapp.models.UserProfileResponse;

import java.util.List;
import retrofit2.http.Header;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;

public interface ApiService {

    @GET("api/v1/destinos/")
    Call<List<Destinos>> getDestinos();

    @GET("api/v1/destinos/{id_destino}")
    Call<Destinos> getDestinoById(@Path("id_destino") int id);

    @POST("api/v1/auth/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/v1/auth/register/")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("password-reset/")
    Call<Void> sendPasswordResetEmail(@Body PasswordResetRequest request);

    @GET("api/v1/usuarios/")
    Call<List<User>> getUsuarios();

    @GET("api/profile/")
    Call<UserProfileResponse> getUserProfile(@Header("Authorization") String authHeader);

    @PUT("api/v1/profiles/{id}")
    Call<User> updateUser(@Header("Authorization") String token, @Path("id") int id, @Body User user);
}
