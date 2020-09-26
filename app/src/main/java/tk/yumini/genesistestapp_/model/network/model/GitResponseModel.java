package tk.yumini.genesistestapp_.model.network.model;

import java.util.ArrayList;
import java.util.List;

public class GitResponseModel {
    private  int total_count;
    private Boolean incomplete_results;
    private List<GitRepoModel> items;
    private String message;

    public GitResponseModel(int total_count, Boolean incomplete_results, List<GitRepoModel> items, String message) {
        this.total_count = total_count;
        this.incomplete_results = incomplete_results;
        this.items = items;
        this.message = message;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public Boolean getIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(Boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<GitRepoModel> getItems() {
        return items;
    }

    public void setItems(List<GitRepoModel> items) {
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
