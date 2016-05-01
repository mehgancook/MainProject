package tcss450.uw.edu.mainproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.mainproject.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {
    public static String USER_ITEM_SELECTED = "UserDetailFragment";
    private TextView mUsername;
    private TextView mEmailAddress;
    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public void updateView(User user) {
        if (user != null) {
            mUsername.setText(user.getUsername());
            mEmailAddress.setText(user.getEmail());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        mUsername = (TextView) view.findViewById(R.id.username);
        mEmailAddress = (TextView) view.findViewById(R.id.email);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((User) args.getSerializable(USER_ITEM_SELECTED));
        }
    }



}
