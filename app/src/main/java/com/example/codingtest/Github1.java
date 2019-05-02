package com.example.codingtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Github1 {
    @GET("/repos/{owner}/{repo}/issues")
    Call<List<Issue>> contributors(
            @Path("owner") String owner, @Path("repo") String repo, @Query("sort") String sort);
}