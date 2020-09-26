package tk.yumini.genesistestapp_.model.repository;

import android.app.Application;
import android.content.Context;
import android.nfc.tech.MifareUltralight;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import tk.yumini.genesistestapp_.model.network.NetworkService;
import tk.yumini.genesistestapp_.model.network.model.GitRepoModel;
import tk.yumini.genesistestapp_.model.network.model.GitResponseModel;
import tk.yumini.genesistestapp_.model.network.model.Zipper;
import tk.yumini.genesistestapp_.model.room.FavRepo;
import tk.yumini.genesistestapp_.model.room.FavRepoDao;
import tk.yumini.genesistestapp_.model.room.FavRepoDatabase;

import static android.widget.Toast.LENGTH_LONG;

public class GitFromApiRepository {
    private FavRepoDao favRepoDao;
    public MutableLiveData<List<GitRepoModel>>allFavRepo = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgress = new MutableLiveData<>();
    private int page = 1;
    private Application mApplication;
    private String mString = "";
    Boolean isLoading = false;

    public GitFromApiRepository(Application application) {
        mApplication = application;
        FavRepoDatabase database = FavRepoDatabase.getInstance(application);
        favRepoDao = database.favRepoDao();
        showProgress.setValue(false);
        //allFavRepo = getAll();
        //startFirst();
    }

    public void updareFavorite(GitRepoModel model, Boolean setFavorite){
        if(setFavorite){
            new InsertNoteAsyncTask(favRepoDao).execute(new FavRepo(model.getId(),model.getName(),model.getFull_name(),model.getHtml_url(),model.getDescription(),true));
        }else{
            new DeleteAsyncTask(favRepoDao).execute(model.getId());
        }
    }

    public void searchGitRepository( String searchString){
        mString = searchString;
        page = 1;
        showProgress.setValue(true);
        //repository.searchGitRepository(searchString)
        allFavRepo.setValue(new ArrayList<GitRepoModel>());
        startRequest();
    }
    public void startScrolled(RecyclerView.LayoutManager manager){
        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int firstVisibleItemPosition = ((LinearLayoutManager)manager).findFirstVisibleItemPosition();

        if (!isLoading) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= MifareUltralight.PAGE_SIZE
            ) {
                startRequest();
                isLoading = true;
            }
        }
    }

    public void checkFavorite(FavRepo model){
        List<GitRepoModel> curentlist =  allFavRepo.getValue();
        List<GitRepoModel> newList = new ArrayList<>();

        if(curentlist!=null){
            for (Iterator<GitRepoModel> i = curentlist.iterator(); i.hasNext();) {
                GitRepoModel item = i.next();
                if(item.getId()!=null && model.getIdApi() !=null && item.getId().equals(model.getIdApi())){
                    item.setFavorite(false);
                }
                newList.add(item);
            }
            allFavRepo.setValue(newList);
        }
    }



    public void startRequest(){
        Observable<GitResponseModel> one = NetworkService.getservice().getGitRepo(mString, page, 15)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());;
        Observable<GitResponseModel> two = NetworkService.getservice().getGitRepo(mString, page+1, 15)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());;

        Observable combined = Observable.zip(one, two, new BiFunction() {
            @Override
            public Zipper apply(Object o, Object o2) {
                return new Zipper(mApplication, (GitResponseModel)o, (GitResponseModel)o2);
            }
        });

        combined.subscribe(new Observer<Zipper>() {
            @Override
            public void onSubscribe(Disposable d) {
                //Toast.makeText(mApplication, "onSubscribe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Zipper zipper) {
                //Toast.makeText(mApplication, "onNext", Toast.LENGTH_SHORT).show();
                Observable.fromCallable(new CallableLongAction(zipper.getList()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<GitRepoModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                               // Toast.makeText(mApplication, "onSubscribe", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(List<GitRepoModel> gitRepoModels) {
                                if(allFavRepo.getValue()!=null && allFavRepo.getValue().size()>0){
                                    List<GitRepoModel> list = allFavRepo.getValue();
                                    list.addAll(gitRepoModels );
                                    allFavRepo.setValue(list);
                                }else{
                                    allFavRepo.setValue(gitRepoModels);
                                }
                                showProgress.setValue(false);
                                isLoading = false;
                                page += 2;
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onComplete() {
                //Toast.makeText(mApplication, "onComplete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*public Observable<GitResponseModel> getRepoObservable(int order){
       return NetworkService.getservice().getGitRepo(mString, page+order, 15)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

   class CallableLongAction implements Callable<List<GitRepoModel>> {

        private final List<GitRepoModel> list;

        public CallableLongAction(List<GitRepoModel> list) {
            this.list = list;
        }

       @Override
       public List<GitRepoModel> call() throws Exception {
           return getFavoriteFromDb(list);
       }
   }

    private List<GitRepoModel>  getFavoriteFromDb(List<GitRepoModel> list){
        List<GitRepoModel> res  = new ArrayList<>();
        FavRepoDatabase database = FavRepoDatabase.getInstance(mApplication);
        FavRepoDao favRepoDao = database.favRepoDao();
        for (Iterator<GitRepoModel> i = list.iterator(); i.hasNext();) {
            GitRepoModel item = i.next();
            FavRepo dbRes = favRepoDao.getById(item.getId());
            if(dbRes !=null && dbRes.getId()!= null && dbRes.getIsfavorite()){
                item.setFavorite( true);
            }
            res.add(item);
        }
        return  res;
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
