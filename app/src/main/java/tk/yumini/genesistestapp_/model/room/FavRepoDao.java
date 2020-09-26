package tk.yumini.genesistestapp_.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Observable;

import io.reactivex.Single;

@Dao
public interface FavRepoDao {

    public static String TABLE_NAME = "FavRepoTable";

    @Insert
    void insert(FavRepo fav);

    @Update
    void update(FavRepo fav);

    @Delete
    void delete(FavRepo fav);

    @Query("DELETE from "+TABLE_NAME+" where idApi=:id")
    void deleteById(Long id);

    @Query("DELETE FROM "+TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM "+TABLE_NAME+" ORDER BY id DESC")
    LiveData<List<FavRepo>> getAll();

    @Query("SELECT * FROM "+TABLE_NAME+" WHERE idApi=:id ") FavRepo getById(Long id);
}
