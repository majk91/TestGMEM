package tk.yumini.genesistestapp_.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tk.yumini.genesistestapp_.model.repository.GitRepository;
import tk.yumini.genesistestapp_.model.room.FavRepo;

public class FavoriresViewModel extends AndroidViewModel {
    private GitRepository repository;
    private LiveData<List<FavRepo>> allRepo;

    public FavoriresViewModel(@NonNull Application application) {
        super(application);
        repository = new GitRepository(application);
        allRepo = repository.getAll();
    }

    public void  insert(FavRepo note){
        repository.insert(note);
    }

    public void update(FavRepo note){
        repository.update(note);
    }

    public void delete(FavRepo note){
        repository.delete(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<FavRepo>> getAllNotes(){
        return allRepo;
    }
}
