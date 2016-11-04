package cmpe295.project.com.healthmonitor;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by savani on 10/16/16.
 */

public class NotificationsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<NotificationsModel> notificationsList;
    private static final String TAG="Notifications Adapter";
    NotificationsAdapter(ArrayList<NotificationsModel> list){
        notificationsList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NotificationsViewHolder(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.notifications_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NotificationsViewHolder nvh = (NotificationsViewHolder) holder;
        Log.d(TAG, "fetched data "+notificationsList.size());
        NotificationsModel  notification = notificationsList.get(position);
        if(notification.getNotificationType() == 1){   //For notification
            nvh.cardRow.setBackgroundColor(Color.parseColor("#7FFFA500"));
            nvh.prioView.setText(notification.getSubject());
            nvh.subjectView.setText(notification.getMessage());
            nvh.notifTypeView.setText(R.string.notification);
        }
        else {
            nvh.cardRow.setBackgroundColor(Color.CYAN);
            nvh.prioView.setText(notification.getPriority());
            nvh.subjectView.setText(notification.getMessage());
            nvh.notifTypeView.setText(R.string.alert);
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



    class NotificationsViewHolder extends RecyclerView.ViewHolder{
        TextView dateView;
        TextView subjectView;
        CardView cardRow;
        TextView prioView;
        TextView notifTypeView;

        public NotificationsViewHolder(View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.date);
            subjectView = (TextView)itemView.findViewById(R.id.subject);
            cardRow = (CardView) itemView.findViewById(R.id.notifications_row);
            prioView = (TextView) itemView.findViewById(R.id.prio);
            notifTypeView = (TextView) itemView.findViewById(R.id.notificationType);

        }
    }
}
