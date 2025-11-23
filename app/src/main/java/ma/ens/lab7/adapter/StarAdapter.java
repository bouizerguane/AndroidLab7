package ma.ens.lab7.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ma.ens.lab7.R;
import ma.ens.lab7.bean.Star;
import ma.ens.lab7.service.StarService;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private static final String TAG = "StarAdapter";
    private List<Star> stars;
    private List<Star> starsFilter;
    private Context context;
    private NewFilter mfilter;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.stars = stars;
        this.starsFilter = new ArrayList<>(stars);
        this.mfilter = new NewFilter(this);
    }

    @Override
    public StarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false);
        return new StarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StarViewHolder holder, int position) {
        Star s = starsFilter.get(position);
        holder.name.setText(s.getName());
        holder.rating.setRating(s.getRating());
        Glide.with(context).load(s.getImg()).into(holder.img);

        holder.rating.setIsIndicator(false);
        holder.rating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                s.setRating(rating);
                StarService.getInstance().update(s);
                Toast.makeText(context, s.getName() + ": " + rating + " étoiles", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return starsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    static class StarViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name;
        RatingBar rating;

        StarViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgStar);
            name = itemView.findViewById(R.id.tvName);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;

        public NewFilter(RecyclerView.Adapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Star> filtered = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filtered.addAll(stars);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Star p : stars) {
                    if (p.getName().toLowerCase().contains(filterPattern)) {
                        filtered.add(p);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            starsFilter = (List<Star>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    // Méthode pour mettre à jour la liste complète si besoin
    public void updateOriginalList(List<Star> newStars) {
        stars = new ArrayList<>(newStars);
        starsFilter = new ArrayList<>(newStars);
        notifyDataSetChanged();
    }
}