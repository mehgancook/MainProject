/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.followers_askers_groups;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.model.User;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User} and makes a call to the
 * specified {@link FollowListFragment.OnListFragmentInteractionListener}.
 *
 */
public class MyAskerListRecyclerViewAdapter extends RecyclerView.Adapter<MyAskerListRecyclerViewAdapter.ViewHolder> {
    /**List of users*/
    private final List<User> mValues;
    /**Listener*/
    private final AskerListFragment.OnListFragmentInteractionListener mListener;
    /**font*/
    private Typeface mFont;

    /**Constructor for recycler view
     * @param items the users
     * @param listener the listener*/
    public MyAskerListRecyclerViewAdapter(List<User> items, AskerListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    /**Constructor for recylcer view
     * @param listener the listener
     * @param items the users
     * @param font the font*/
    public MyAskerListRecyclerViewAdapter(List<User> items, AskerListFragment.OnListFragmentInteractionListener listener,
                                          Typeface font) {
        this(items, listener);
        mFont = font;
    }
    /**
     * on create view holder
     * @param parent the view group
     * @param viewType the view type
     * */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_askerlist, parent, false);
        return new ViewHolder(view);
    }
    /**
     * on bind view holder
     * @param holder the view holder
     * @param position the position
     * */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getUsername());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }
    /**
     * get item count
     * @return amount of users
     * */
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    /**
     * View holder class to hold the recylcer view
     * */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**the view*/
        public final View mView;
        /** the text view of id*/
        public final TextView mIdView;
        /**the text view of content*/
        public final TextView mContentView;
        /**the user*/
        public User mItem;

        /**Constructor of the viewholder
         * @param view the view*/
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            if (mFont != null) {
                mIdView.setTypeface(mFont);
            }
            mContentView = (TextView) view.findViewById(R.id.content);
            if (mFont != null) {
                mContentView.setTypeface(mFont);
            }
        }
        /**
         * tostring
         * @return string
         * */
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
