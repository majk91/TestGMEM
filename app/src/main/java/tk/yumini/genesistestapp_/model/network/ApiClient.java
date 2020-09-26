package tk.yumini.genesistestapp_.model.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tk.yumini.genesistestapp_.model.network.model.GitResponseModel;

public interface ApiClient {
    @GET("search/repositories")
    Observable<GitResponseModel> getGitRepo(
            @Query("q") String q,
            @Query("page")int page,
            @Query("per_page")int size
    );
}
