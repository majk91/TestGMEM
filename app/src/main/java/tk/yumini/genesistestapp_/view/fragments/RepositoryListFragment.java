package tk.yumini.genesistestapp_.view.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tk.yumini.genesistestapp_.R;
import tk.yumini.genesistestapp_.adapter.RepoAdapter;
import tk.yumini.genesistestapp_.model.network.NetworkService;
import tk.yumini.genesistestapp_.model.network.model.GitRepoModel;
import tk.yumini.genesistestapp_.model.network.model.GitResponseModel;
import tk.yumini.genesistestapp_.model.room.FavRepo;
import tk.yumini.genesistestapp_.view.MainActivity;
import tk.yumini.genesistestapp_.viewmodel.FavoriresViewModel;
import tk.yumini.genesistestapp_.viewmodel.RepositoryListViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RepositoryListFragment extends Fragment implements MainActivity.OnAboutDataReceivedListener, RepoAdapter.OnAboutDataReceivedListener {

    private RepositoryListViewModel mViewModel;
    private Context context;
    View mView;
    private ProgressBar progress;
    RepoAdapter adapter;

    public static RepositoryListFragment newInstance() {
        return new RepositoryListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getActivity();
        mView =  inflater.inflate(R.layout.repository_list_fragment, container, false);
        progress = mView.findViewById(R.id.progressBar);
        return  mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView =  mView.findViewById(R.id.myRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RepoAdapter(context);
        recyclerView.setAdapter(adapter);
        mViewModel = new ViewModelProvider(this).get(RepositoryListViewModel.class);
        mViewModel.showProgress().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //TODO: убрать фокус у поиска
                if(aBoolean){
                    progress.setVisibility( VISIBLE);
                    adapter.setList(new ArrayList<GitRepoModel>());
                }
                else progress.setVisibility(GONE);
            }
        });

        mViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<GitRepoModel>>() {
            @Override
            public void onChanged(List<GitRepoModel> gitRepoModels) {
                adapter.setList(gitRepoModels);
            }
        });

        MainActivity mActivity = (MainActivity) getActivity();
        mActivity.setAboutDataListener(this);

        adapter.setAboutDataListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mViewModel.scrolled(recyclerView.getLayoutManager());
            }
        });


    }

    @Override
    public void onDataReceived(String search) {
        Toast.makeText(getContext(), search, Toast.LENGTH_SHORT).show();
        //viewModel.changeState()
        if(search != "")
            mViewModel.searchGitRepository(search);
    }

    @Override
    public void onupdateList(FavRepo model) {
        mViewModel.checkFavorite(model);
    }

    @Override
    public void onDataReceived(GitRepoModel model, Boolean setFavorite) {
        mViewModel.updareFavorite(model, setFavorite);
    }
}
