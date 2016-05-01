package tcss450.uw.edu.mainproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.model.User;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AskerListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<User> mAskers;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private static final String ASKER_LIST_URL
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=" +
            "select+username%2CUser.email%2CUser.password%2CUser.userid+from+Askers%2CUser+where+" +
            "User.userid+%3D+Askers.askerid+and+Askers.userid+%3D+%0D%0A%28select+userid+" +
            "from+User+where+email+%3D+%27";



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AskerListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FollowListFragment newInstance(int columnCount) {
        FollowListFragment fragment = new FollowListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_askerlist_list, container, false);
        String url = buildURL(view);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            DownloadAskersTask task = new DownloadAskersTask();
            task.execute(new String[]{url});
            // recyclerView.setAdapter(new MyFollowListRecyclerViewAdapter(mFollowers, mListener));
        }
        //   DownloadFollowersTask task = new DownloadFollowersTask();
        //   task.execute(new String[]{url});
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String buildURL(View v) {
        StringBuilder sb = new StringBuilder(ASKER_LIST_URL);
        UserDB userDB = new UserDB(getActivity());
        String email = userDB.getUsers().get(0).getEmail();
        try {
            sb.append(URLEncoder.encode(email, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("%27%29%3B");
        return sb.toString();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(User item);
    }
    private class DownloadAskersTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            mAskers = new ArrayList<User>();
            result = User.parseUserJSON(result, mAskers);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.



            if (!mAskers.isEmpty()) {

                mRecyclerView.setAdapter(new MyAskerListRecyclerViewAdapter(mAskers, mListener));
//                if (mCourseDB == null) {
//                    mCourseDB = new CourseDB(getActivity());
//                }
//                // Delete old data so that you can refresh the local
//                // database with the network data.
//                mCourseDB.deleteCourses();
//
//                // Also, add to the local database
//                for (int i=0; i<mCourseList.size(); i++) {
//                    Course course = mCourseList.get(i);
//                    mCourseDB.insertCourse(course.getCourseId(),
//                            course.getShortDescription(),
//                            course.getLongDescription(),
//                            course.getPrereqs());
//                }
            }
        }


    }
}
