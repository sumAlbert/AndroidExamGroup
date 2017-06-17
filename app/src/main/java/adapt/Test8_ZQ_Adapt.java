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

/**
 * Created by dell2 on 2017/6/17.
 */

public class Test8_ZQ_Adapt extends ArrayAdapter<String> {
    private int resourceId;
    public Test8_ZQ_Adapt(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String str=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else{
            view=convertView;
        }
        TextView test8_ZQ_textView=(TextView)view.findViewById(R.id.test8_ZQ_textView);
        test8_ZQ_textView.setText(str);
        return view;
    }
}
