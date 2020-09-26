package tk.yumini.genesistestapp_.model.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FavRepo.class}, version = 1)
public abstract class FavRepoDatabase extends RoomDatabase {

    private static FavRepoDatabase instance;

    public abstract FavRepoDao favRepoDao();

    public static synchronized FavRepoDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavRepoDatabase.class, "fav_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopularDbAsyncTask(instance).execute();
        }
    };

    private static  class PopularDbAsyncTask extends AsyncTask<Void, Void, Void> {
        FavRepoDao repoDao;
        private PopularDbAsyncTask(FavRepoDatabase db){
            repoDao = db.favRepoDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //repoDao.insert(new FavRepo(25L, "Description 1", "ttt", "jjj", "lklk", true));
            //repoDao.insert(new FavRepo(26L, "Description 2", "ttt", "jjj", "lklk", true));
            //repoDao.insert(new FavRepo(27L, "Description 3", "ttt", "jjj", "lklk", true));
            return null;
        }

    }
}
