package tk.yumini.genesistestapp_.view.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import tk.yumini.genesistestapp_.R;
import tk.yumini.genesistestapp_.adapter.FavRepoAdapter;
import tk.yumini.genesistestapp_.adapter.RepoAdapter;
import tk.yumini.genesistestapp_.model.network.model.GitRepoModel;
import tk.yumini.genesistestapp_.model.room.FavRepo;
import tk.yumini.genesistestapp_.viewmodel.FavoriresViewModel;

public class FavoriresFragment extends Fragment implements FavRepoAdapter.OnAboutDataReceivedListener {

    private Context context;
    private FavoriresViewModel mViewModel;
    View mView;

    public static FavoriresFragment newInstance() {
        return new FavoriresFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getActivity();
        mView =  inflater.inflate(R.layout.repository_list_fragment, container, false);
        return  mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView =  mView.findViewById(R.id.myRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        final FavRepoAdapter adapter = new FavRepoAdapter(context);
        recyclerView.setAdapter(adapter);
        mViewModel =  new ViewModelProvider(this).get(FavoriresViewModel.class);
        mViewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<FavRepo>>() {
            @Override
            public void onChanged(List<FavRepo> list) {
                adapter.setList(list);
            }
        });

        adapter.setAboutDataListener(this);
    }

    @Override
    public void onDataReceived(FavRepo model) {
        mViewModel.delete(model);
        someEventListener.someEvent(model);
    }

    public interface onSomeEventListener {
        public void someEvent(FavRepo model);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

}
