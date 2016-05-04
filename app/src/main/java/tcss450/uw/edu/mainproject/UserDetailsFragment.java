/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.mainproject.model.User;


/**
 * Displays the user deatails when a user is clicked
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {
    /**user item selected*/
    public static String USER_ITEM_SELECTED = "UserDetailFragment";
    /**the username*/
    private TextView mUsername;
    /**the email*/
    private TextView mEmailAddress;
    /**Empty Constructor*/
    public UserDetailsFragment() {
        // Required empty public constructor
    }
    /**
     * update the view with current information
     * @param user the user
     * */
    public void updateView(User user) {
        if (user != null) {
            mUsername.setText(user.getUsername());
            mEmailAddress.setText(user.getEmail());
        }
    }

    /**
     * on create view sets the username and password
     * @param inflater inflater
     * @param savedInstanceState the saved instance state
     * @param container a container
     * @return the view
     *
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Typeface oswald = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Regular.ttf");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        mUsername = (TextView) view.findViewById(R.id.username);
        mUsername.setTypeface(oswald);
        mEmailAddress = (TextView) view.findViewById(R.id.email);
        mEmailAddress.setTypeface(oswald);
        return view;
    }

    /**
     * onStart
     * */
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
