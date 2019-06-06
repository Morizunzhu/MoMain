package mo.hyit.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mo.hyit.entity.ChatterUIMessage;
import mo.hyit.momain.R;

public class MineRecyclerViewAdapter extends RecyclerView.Adapter<MineRecyclerViewAdapter.ViewHolder> {
    private List<ChatterUIMessage> data;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;
        LinearLayout rightLayout;

        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout=(LinearLayout) itemView.findViewById(R.id.left_layout);
            rightLayout=(LinearLayout)itemView.findViewById(R.id.right_layout);
            leftMsg=(TextView) itemView.findViewById(R.id.left_msg);
            rightMsg=(TextView) itemView.findViewById(R.id.right_msg);
        }
    }

    public MineRecyclerViewAdapter(List<ChatterUIMessage> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MineRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatter_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MineRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        ChatterUIMessage msg=data.get(i);
        switch (msg.getType()){
            case RECEIVED://接收的消息
                viewHolder.leftLayout.setVisibility(View.VISIBLE);
                viewHolder.rightLayout.setVisibility(View.GONE);
                viewHolder.leftMsg.setText(msg.getContent());
                break;
            case SENT://发出的消息
                viewHolder.leftLayout.setVisibility(View.GONE);
                viewHolder.rightLayout.setVisibility(View.VISIBLE);
                viewHolder.rightMsg.setText(msg.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
