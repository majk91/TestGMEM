package tk.yumini.genesistestapp_.model.network.model;

public class GitRepoModel {
    Long id;
    String name;
    String full_name;
    String html_url;
    String description;
    String created_at;
    String language;
    Boolean favorite = false;

    public GitRepoModel(Long id, String name, String full_name, String html_url, String description, String created_at, String language, Boolean favorite) {
        this.id = id;
        this.name = name;
        this.full_name = full_name;
        this.html_url = html_url;
        this.description = description;
        this.created_at = created_at;
        this.language = language;
        this.favorite = favorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
