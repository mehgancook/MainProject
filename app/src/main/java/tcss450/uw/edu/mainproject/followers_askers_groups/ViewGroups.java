package tcss450.uw.edu.mainproject.followers_askers_groups;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.followers_askers_groups.GroupListFragment;
import tcss450.uw.edu.mainproject.model.Group;

public class ViewGroups extends AppCompatActivity implements
    GroupListFragment.OnListFragmentInteractionListener {

    private GroupListFragment mGroupListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.logo_adjusted));
            setTitle("");
        }

        Toolbar tools = (Toolbar) findViewById(R.id.toolbar);
        tools.setCollapsible(false);

        mGroupListFragment = new GroupListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mGroupListFragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onListFragmentInteraction(Group group) {
        GroupListFragment groupDetailFragment = new GroupListFragment();
        Bundle args = new Bundle();
        args.putSerializable(GroupListFragment.GROUP_ITEM_SELECTED, group);
        groupDetailFragment.setArguments(args);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, groupDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
