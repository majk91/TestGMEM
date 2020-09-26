package tk.yumini.genesistestapp_.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tk.yumini.genesistestapp_.R;
import tk.yumini.genesistestapp_.model.network.model.GitRepoModel;
import tk.yumini.genesistestapp_.model.room.FavRepo;

public class FavRepoAdapter extends RecyclerView.Adapter<FavRepoAdapter.FavRepoHolder> {

    private List<FavRepo> list =  new ArrayList<>();
    private Context mContext;
    public FavRepoAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<FavRepo> newlist ){
        list = newlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavRepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repository_fav,parent,false);
        return new FavRepoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavRepoHolder holder, int position) {
        final FavRepo favRepo = list.get(position);
        holder.setfavRepos(favRepo);
    }
    public class FavRepoHolder extends RecyclerView.ViewHolder{
        private TextView name, desc;
        private ImageButton delete;


        public FavRepoHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_title);
            desc = itemView.findViewById(R.id.text_view_description);
            delete = itemView.findViewById(R.id.dell);
        }
        public void setfavRepos(final FavRepo favRepo){
            name.setText(favRepo.getName());
            desc.setText(favRepo.getDescription());

            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"click on item: ",Toast.LENGTH_LONG).show();
                    mAboutDataListener.onDataReceived(favRepo);
                }
            });

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
        void onDataReceived(FavRepo model);
    }

    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }

}
