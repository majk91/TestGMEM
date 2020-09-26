package tk.yumini.genesistestapp_.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tk.yumini.genesistestapp_.model.network.model.GitRepoModel;
import tk.yumini.genesistestapp_.model.repository.GitFromApiRepository;
import tk.yumini.genesistestapp_.model.room.FavRepo;

public class RepositoryListViewModel extends AndroidViewModel {
    private GitFromApiRepository repository;
    private LiveData<List<GitRepoModel>> allRepo;
    private MutableLiveData<Boolean> showProgress;


    public RepositoryListViewModel(@NonNull Application application) {
        super(application);
        repository = new GitFromApiRepository(application);
        this.allRepo = repository.allFavRepo;
        this.showProgress = repository.showProgress;
    }
    public LiveData<List<GitRepoModel>> getAll(){
        return  allRepo;
    }

    public MutableLiveData<Boolean> showProgress(){
        return showProgress;
    }

    public void searchGitRepository( String searchString){
        repository.searchGitRepository(searchString);
    }

    public void updareFavorite(GitRepoModel model, Boolean setFavorite){
        repository.updareFavorite(model, setFavorite);
    }

    public void checkFavorite(FavRepo model){
        repository.checkFavorite(model);
    }

    public void scrolled(RecyclerView.LayoutManager manager){
        repository.startScrolled(manager);
    }

}
