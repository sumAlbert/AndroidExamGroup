package adapt;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell2.androidexamgroup.R;

import java.util.List;

import entity.Touch;

/**
 * Created by dell2 on 2017/6/18.
 */

public class Test3_ZQ_Adapt extends ArrayAdapter<Touch> {
    private int resourceId;
    public Test3_ZQ_Adapt(@NonNull Context context, @LayoutRes int resource, @NonNull List<Touch> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Touch item=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else{
            view=convertView;
        }
        TextView test3_ZQ_name=(TextView)view.findViewById(R.id.test3_ZQ_name);
        TextView test3_ZQ_tel=(TextView)view.findViewById(R.id.test3_ZQ_tel);
        test3_ZQ_name.setText(item.getName());
        test3_ZQ_tel.setText(item.getTel());
        return view;
    }
}
