package tk.yumini.genesistestapp_.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tk.yumini.genesistestapp_.R;
import tk.yumini.genesistestapp_.model.network.model.GitRepoModel;
import tk.yumini.genesistestapp_.model.room.FavRepo;
import tk.yumini.genesistestapp_.view.MainActivity;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.FavRepoHolder> {

    private List<GitRepoModel> list =  new ArrayList<>();
    //private LayoutInflater mInflater;
    private Context mContext;
    public RepoAdapter(Context context/*, List<FavRepo> favRepos*/) {
        mContext = context;
        //list = favRepos;
        //mInflater = LayoutInflater.from(context);
    }

    public void setList(List<GitRepoModel> newlist ){
        list = newlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavRepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repository,parent,false);
        return new FavRepoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavRepoHolder holder, int position) {
        final GitRepoModel favRepo = list.get(position);
        holder.setfavRepos(favRepo, position);
    }
    public class FavRepoHolder extends RecyclerView.ViewHolder{
        private TextView name, desc;
        private ImageView fav;
        private ImageView unfav;


        public FavRepoHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_title);
            desc = itemView.findViewById(R.id.text_view_description);
            fav = itemView.findViewById(R.id.imageButtonOn);
            unfav = itemView.findViewById(R.id.imageButtonOff);
        }
        public void setfavRepos(final GitRepoModel favRepo, final int position){
            name.setText(favRepo.getName());
            desc.setText(favRepo.getDescription());

            fav.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    list.get(position).setFavorite(false);
                    mAboutDataListener.onDataReceived(favRepo, false);
                    fav.setVisibility(View.GONE);
                    unfav.setVisibility(View.VISIBLE);
                }
            });

            unfav.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    mAboutDataListener.onDataReceived(favRepo, true);
                    fav.setVisibility(View.VISIBLE);
                    unfav.setVisibility(View.GONE);
                    list.get(position).setFavorite(true);
                }
            });

            if(favRepo.getFavorite()!= null && favRepo.getFavorite()){
                fav.setVisibility(View.VISIBLE);
                unfav.setVisibility(View.GONE);
            }else{
                unfav.setVisibility(View.VISIBLE);
                fav.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(favRepo.getHtml_url())));
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    private OnAboutDataReceivedListener mAboutDataListener;

    public interface OnAboutDataReceivedListener {
        void onDataReceived(GitRepoModel model, Boolean setFavorite);
    }

    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }
}
