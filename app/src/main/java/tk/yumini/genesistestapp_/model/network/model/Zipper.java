package tk.yumini.genesistestapp_.model.network.model;

import android.content.Context;

import java.util.List;


public class Zipper {

    private List<GitRepoModel> list;
    private String error ="";

    public Zipper(Context context, GitResponseModel one, GitResponseModel two) {
        List<GitRepoModel> list1 = one.getItems();
        List<GitRepoModel> list2 = two.getItems();
        list1.addAll(list2);
        this.list =list1;

        if(one.getMessage()!= null && !one.getMessage().isEmpty())
            this.error = one.getMessage();
        if(one.getMessage()!= null && !two.getMessage().isEmpty())
            this.error = two.getMessage();


    }

    public List<GitRepoModel> getList() {
        return list;
    }

    public String getError() {
        return error;
    }
}
