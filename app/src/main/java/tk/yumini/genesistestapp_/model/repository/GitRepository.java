package tk.yumini.genesistestapp_.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import tk.yumini.genesistestapp_.model.room.FavRepo;
import tk.yumini.genesistestapp_.model.room.FavRepoDao;
import tk.yumini.genesistestapp_.model.room.FavRepoDatabase;

public class GitRepository {
    private FavRepoDao favRepoDao;
    private LiveData<List<FavRepo>> allFavRepo;

    public GitRepository(Application application) {
        FavRepoDatabase database = FavRepoDatabase.getInstance(application);
        favRepoDao = database.favRepoDao();
        allFavRepo = favRepoDao.getAll();
    }

    public void insert(FavRepo repo){
        new InsertNoteAsyncTask(favRepoDao).execute(repo);
    }

    public void update(FavRepo repo){
        //new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(FavRepo repo){
        new GitRepository.DeleteAsyncTask(favRepoDao).execute(repo.getIdApi());
    }

    public void deleteAllNotes(){
        //new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<FavRepo>> getAll(){
        return allFavRepo;
    }

    public void getById(Long id){
        /*favRepoDao.getById(1L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<FavRepo>() {
                    @Override
                    public void onSuccess(FavRepo employee) {
                        // ...
                    }

                    @Override
                    public void onError(Throwable e) {
                        // ...
                    }
                });
                */

    }

    private  static class InsertNoteAsyncTask extends AsyncTask<FavRepo, Void, Void> {

        private FavRepoDao favRepoDao;
        private InsertNoteAsyncTask(FavRepoDao favRepoDao){
            this.favRepoDao = favRepoDao;
        }
        @Override
        protected Void doInBackground(FavRepo... repo) {
            favRepoDao.insert(repo[0]);
            return null;
        }
    }

    private  static class DeleteAsyncTask extends AsyncTask<Long, Void, Void> {

        private FavRepoDao favRepoDao;
        private DeleteAsyncTask(FavRepoDao favRepoDao){
            this.favRepoDao = favRepoDao;
        }
        @Override
        protected Void doInBackground(Long... repo) {
            favRepoDao.deleteById(repo[0]);
            return null;
        }
    }
}
