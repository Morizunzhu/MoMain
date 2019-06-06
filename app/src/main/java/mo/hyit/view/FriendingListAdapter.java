package mo.hyit.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mo.hyit.activity.FriendDetailActivity;
import mo.hyit.dao.UserService;
import mo.hyit.momain.R;

public class FriendingListAdapter extends BaseAdapter {
    private List<Integer> data;
    private LayoutInflater lyf;
    private String receiverId;
    private Activity context;

    public FriendingListAdapter(List<Integer> data, String receiver,Activity context) {
        lyf = LayoutInflater.from(context);
        this.data = data;
        this.receiverId = receiver;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Integer getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = lyf.inflate(R.layout.friending_item,null);
        Button af_confirm =view.findViewById(R.id.af_confirm);
        TextView friending_name=view.findViewById(R.id.friending_name);
        friending_name.setText(getItem(position)+"");
        af_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new UserService(context).updateAskForFriend(""+getItem(position),receiverId);
                    }
                }).start();
                new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE).setTitleText("成功").setContentText("添加成功").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                    }
                }).show();

            }
        });
        return view;
    }
}
