package com.danielogbuti.spammessaging;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SpamClient {

    @POST("predict")
    Call<Spam> createAccount(@Body Spam spam);
}
