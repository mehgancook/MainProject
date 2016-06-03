/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.voting_reviewing_questions;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;
import tcss450.uw.edu.mainproject.voting_reviewing_questions.AskedQuestionResultFragment.OnListFragmentInteractionListener;

/**
 * Recycler view used to display the asked questions by the user
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyAskedQuestionResultRecyclerViewAdapter extends RecyclerView.Adapter<MyAskedQuestionResultRecyclerViewAdapter.ViewHolder> {
    /**List of questions with details*/
    private final List<QuestionWithDetail> mValues;
    /**Listener*/
    private final OnListFragmentInteractionListener mListener;
    /**font*/
    private Typeface mFont;
    /**Constructor for recycler view
     * @param items the questions with details
     * @param listener the listener*/
    public MyAskedQuestionResultRecyclerViewAdapter(List<QuestionWithDetail> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    /**Constructor for recylcer view
     * @param listener the listener
     * @param items the users
     * @param font the font*/
    public MyAskedQuestionResultRecyclerViewAdapter(List<QuestionWithDetail> items, OnListFragmentInteractionListener listener,
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
                .inflate(R.layout.fragment_askedquestionresult, parent, false);
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
        holder.mIdView.setText(mValues.get(position).getQuestionName());
       // holder.mContentView.setText(mValues.get(position).content);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        /**the view*/
        public final View mView;
        /** the text view of id*/
        public final TextView mIdView;
        /**the text view of content*/
        public final TextView mContentView;
        /**Question with detail item*/
        public QuestionWithDetail mItem;

        /**Constructor of the viewholder
         * @param view the view*/
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            if (mFont != null) {
                mIdView.setTypeface(mFont);
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
