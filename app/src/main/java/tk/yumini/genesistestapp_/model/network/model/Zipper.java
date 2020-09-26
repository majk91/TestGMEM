package tk.yumini.genesistestapp_.model.network.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tk.yumini.genesistestapp_.model.room.FavRepo;
import tk.yumini.genesistestapp_.model.room.FavRepoDao;
import tk.yumini.genesistestapp_.model.room.FavRepoDatabase;

public class Zipper {

    private List<GitRepoModel> list;
    private String error ="";

    public Zipper(Context context, GitResponseModel one, GitResponseModel two) {
        //FavRepoDatabase database = FavRepoDatabase.getInstance(context);
        //FavRepoDao favRepoDao = database.favRepoDao();
        //allFavRepo = favRepoDao.getById();
        List<GitRepoModel> list1 = one.getItems();// = favoriteSerch(favRepoDao, one.getItems());
        List<GitRepoModel> list2 = two.getItems();// = favoriteSerch(favRepoDao, two.getItems());
        list1.addAll(list2);
        this.list =list1;

        if(one.getMessage()!= null && !one.getMessage().isEmpty())
            this.error = one.getMessage();
        if(one.getMessage()!= null && !two.getMessage().isEmpty())
            this.error = two.getMessage();


    }

    /*private List<GitRepoModel> favoriteSerch(FavRepoDao favRepoDao, List<GitRepoModel> list){
        List<GitRepoModel> res  = new ArrayList<>();
        for (Iterator<GitRepoModel> i = list.iterator(); i.hasNext();) {
            GitRepoModel item = i.next();
            FavRepo dbRes = favRepoDao.getById(item.id);
            if(dbRes !=null && dbRes.getId()!= null && dbRes.getIsfavorite()){
                item.favorite = true  ;
            }
            res.add(item);
        }
        return  res;
    }*/

    public List<GitRepoModel> getList() {
        return list;
    }

    public String getError() {
        return error;
    }
}
