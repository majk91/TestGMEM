package tk.yumini.genesistestapp_.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavRepoTable")
public class FavRepo {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long idApi;
    private String name;
    private String full_name;
    private String html_url;
    private String description;
    private Boolean isfavorite = true;

    public FavRepo(Long idApi, String name, String full_name, String html_url, String description, Boolean isfavorite) {
        this.idApi = idApi;
        this.name = name;
        this.full_name = full_name;
        this.html_url = html_url;
        this.description = description;
        this.isfavorite = isfavorite;
    }

    public Long getId() {
        return id;
    }
    public Long getIdApi() {
        return idApi;
    }
    public String getName() {
        return name;
    }
    public String getFull_name() {
        return full_name;
    }
    public String getHtml_url() {
        return html_url;
    }
    public String getDescription() {
        return description;
    }
    public Boolean getIsfavorite() {
        return isfavorite;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setIdApi(Long idApi) {
        this.idApi = idApi;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setIsfavorite(Boolean isfavorite) {
        this.isfavorite = isfavorite;
    }
}
