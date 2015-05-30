package tscanner.msquared.hr.travelscanner.adapters;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.squareup.picasso.Picasso;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.activities.DestinationInfoActivity;
import tscanner.msquared.hr.travelscanner.customViews.TravelPointsCardIndicatorView;
import tscanner.msquared.hr.travelscanner.models.restModels.TravelDestination;

/**
 * Created by Mihael on 30.5.2015..
 */
public class TravelDestinationsAdapter extends RecyclerView.Adapter<TravelDestinationsAdapter.DestinationViewHolder> {

    private TravelDestination[] destinations;
    private Activity activity;

    public TravelDestinationsAdapter(Activity activity, TravelDestination[] destinations) {
        this.activity = activity;
        this.destinations = destinations;
    }

    @Override
    public DestinationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_destination, parent, false);
        return new DestinationViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DestinationViewHolder holder, final int position) {
        Picasso.with(activity).load(destinations[position].getPicture()).into(holder.destinationPicture);
        holder.destinationTitle.setText(destinations[position].getName());
        holder.destinationPrice.setText("E"+destinations[position].getPrice());
        holder.travelPointsView.setTravelPointsText(destinations[position].getTravelPoints());
        holder.travelPointsView.setTravelByIcon(destinations[position].getTravelBy());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(activity.getBaseContext(), DestinationInfoActivity.class);
                intent.putExtra(DestinationInfoActivity.DESTINATION_IMAGE_URL_EXTRA, destinations[position].getPicture());
                ActivityTransitionLauncher.with(activity).from(view).launch(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinations.length;
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder{

        protected TextView destinationTitle;
        protected TextView destinationPrice;
        protected ImageView destinationPicture;
        protected TravelPointsCardIndicatorView travelPointsView;

        public DestinationViewHolder(View itemView) {
            super(itemView);
            this.destinationTitle = (TextView) itemView.findViewById(R.id.txtCardDestinationTitle);
            this.destinationPrice = (TextView) itemView.findViewById(R.id.txtCardDestinationPrice);
            this.destinationPicture = (ImageView) itemView.findViewById(R.id.imgCardDestinationPicture);
            this.travelPointsView = (TravelPointsCardIndicatorView) itemView.findViewById(R.id.travelPointsView);
        }
    }
}
