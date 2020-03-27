package com.canny.no_touch_elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.webapi.response.GroupMsgBean;
import com.canny.no_touch_elevator.webapi.response.GroupResultBean;
import com.canny.no_touch_elevator.webapi.response.UserBuildResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1006177 on 2020/2/20.
 */

public class GroupResultAdapter extends BaseAdapter {

    private Context context;
    private List<GroupMsgBean.MsgBean> list;

    public GroupResultAdapter(Context context, List<GroupMsgBean.MsgBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BuildViewHolder viewHolder;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_group_result,null);
            viewHolder=new BuildViewHolder();
            viewHolder.tv_ladder= (TextView) view.findViewById(R.id.tv_ladder);
            viewHolder.tv_direction= (TextView) view.findViewById(R.id.tv_direction);
            viewHolder.tv_current_floor= (TextView) view.findViewById(R.id.tv_current_floor);
            view.setTag(viewHolder);
        }else {
            viewHolder= (BuildViewHolder) view.getTag();
        }
        viewHolder.tv_ladder.setText(list.get(i).getIndex()+"");
        viewHolder.tv_direction.setText(list.get(i).getDirection()+"");
        viewHolder.tv_current_floor.setText(list.get(i).getFloor());
        return view;
    }

    class BuildViewHolder{
        TextView tv_ladder;
        TextView tv_direction;
        TextView tv_current_floor;
    }
}
