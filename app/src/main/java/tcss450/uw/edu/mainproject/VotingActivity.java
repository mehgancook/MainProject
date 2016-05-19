package tcss450.uw.edu.mainproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import tcss450.uw.edu.mainproject.model.QuestionWithDetail;

public class VotingActivity extends AppCompatActivity implements AnswerQuestionsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        AnswerQuestionsFragment answerQuestionsFragment = new AnswerQuestionsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, answerQuestionsFragment)
                .commit();

    }

    public void toAnswerQuestions(View v) {

    }

    public void toVotingResults(View v) {

    }

    @Override
    public void onListFragmentInteraction(QuestionWithDetail questionWithDetail) {
        Vote voteFragment = new Vote();
        Bundle args = new Bundle();
        String name = questionWithDetail.getQuestionName();
        List<QuestionWithDetail> questions = ((myApplication) getApplication()).getQuestionList();
        for (int i = 0; i < questions.size(); i++) {
            if (!questions.get(i).getQuestionName().equals(name)) {
                questions.remove(i);
                i--;
            }
        }
        ((myApplication) getApplication()).setCurrentQuestion(questions);
        args.putSerializable(voteFragment.QUESTION_SELECTED, questionWithDetail);
        voteFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, voteFragment)
                .addToBackStack(null)
                .commit();
    }
}
