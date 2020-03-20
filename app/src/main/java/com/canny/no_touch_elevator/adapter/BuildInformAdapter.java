package com.canny.no_touch_elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.webapi.response.UserBuildResponse;

import java.util.List;

/**
 * Created by 1006177 on 2020/2/20.
 */

public class BuildInformAdapter extends BaseAdapter {
    private Context context;
    private List<UserBuildResponse.MsgBean> list;
    public BuildInformAdapter(Context context, List<UserBuildResponse.MsgBean> list) {
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
            view=LayoutInflater.from(context).inflate(R.layout.item_build,null);
            viewHolder=new BuildViewHolder();
            viewHolder.tv_buidname= (TextView) view.findViewById(R.id.tv_buidname);
            viewHolder.tv_bianhao= (TextView) view.findViewById(R.id.tv_bianhao);
            viewHolder.tv_buidnumber= (TextView) view.findViewById(R.id.tv_buidnumber);
            viewHolder.tv_build_etorindex= (TextView) view.findViewById(R.id.tv_build_etorindex);
            view.setTag(viewHolder);
        }else {
            viewHolder= (BuildViewHolder) view.getTag();
        }
        UserBuildResponse.MsgBean etorListBean=list.get(i);
        viewHolder.tv_buidname.setText(etorListBean.getBuildName());
        viewHolder.tv_bianhao.setText(etorListBean.getEtorList().get(0).getBianhao());
        viewHolder.tv_buidnumber.setText(etorListBean.getEtorList().get(0).getBuild_number()+", ");
        viewHolder.tv_build_etorindex.setText(etorListBean.getEtorList().get(0).getBuild_etorindex());
        return view;
    }

    class BuildViewHolder{
        TextView tv_buidname;
        TextView tv_bianhao;
        TextView tv_buidnumber;
        TextView tv_build_etorindex;
    }
}
