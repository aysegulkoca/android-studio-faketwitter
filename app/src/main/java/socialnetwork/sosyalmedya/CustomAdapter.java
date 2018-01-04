package socialnetwork.sosyalmedya;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 22.12.2017.
 */

public class CustomAdapter extends BaseAdapter {
    List<Post> postList;
    LayoutInflater layoutInflater;

    public CustomAdapter(Activity activity, List<Post> postList) {
        this.layoutInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = layoutInflater.inflate(R.layout.row_layout,null);
        TextView userPost = (TextView) view.findViewById(R.id.post);
        TextView userName = (TextView) view.findViewById(R.id.userName);

        Post post = postList.get(position);

        userName.setText(post.getAuthor());
        userPost.setText(post.getPost());

        return view;
    }
}
