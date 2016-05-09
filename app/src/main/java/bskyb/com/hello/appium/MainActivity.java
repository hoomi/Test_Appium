package bskyb.com.hello.appium;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Integer> stringList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
//        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringList));
        listView.setAdapter(new MyArrayAdapter(this, android.R.layout.simple_list_item_1, stringList));
    }


    private static class MyArrayAdapter extends ArrayAdapter<Integer> {

        private Random random;

        public MyArrayAdapter(Context context, int resource,List<Integer> objects) {
            super(context, resource, objects);
            random = new Random();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            if (position % 3 == 0) {
                view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, random.nextInt(600)));
            } else if (position % 5 == 0) {
                view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, random.nextInt(600)));
            }
            return view;
        }
    }

}
