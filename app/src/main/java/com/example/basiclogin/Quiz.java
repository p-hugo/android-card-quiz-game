package com.example.basiclogin;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Quiz extends AppCompatActivity {

  // All sub-indexes [x][0] are questions, [x][y] where y > 0, are answers
  private final String[][] QUESTIONS = {
      {"What is the color of the sky?", "Yellow", "Transparent", "Blue"},
      {"How old is Sponge Bob?", "28", "32", "5"},
      {"Who is Satoshi Nakamoto?", "Creator of Bitcoin", "No one knows",
          "Someone who is really smart"},
      {"What is React?", "A JavaScript framework made by facebook",
          "A JavaScript library made by facebook", "An app"},
      {"What does the fox say?", "Woof", "Meow", "Ring-ding-ding-ding-dingeringeding"}
  };

  // Array of answers
  private final int[] ANSWERS = {2, 1, 1, 2, 2};

  // Internal handlers
  private int qNum = 0;
  private int score = 0;
  private String[] ans = new String [5];

  // View stuff
  TextView questionNumber;
  TextView currentQuestion;
  ProgressBar progressBar;
  Button[] choices = new Button[3];



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    // Set the references
    questionNumber = findViewById(R.id.question_number);
    currentQuestion = findViewById(R.id.current_question);
    progressBar = findViewById(R.id.progressBar);
    choices[0] = findViewById(R.id.choice_1);
    choices[1]  = findViewById(R.id.choice_2);
    choices[2]  = findViewById(R.id.choice_3);
    setQuestion(qNum);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // I'm making sure this progress bar resets
    progressBar.setProgress(0);
  }

  /**
   * Returns a question given its index
   *
   * @param index: the question number
   * @return a string denoting the question requested
   */
  public String getQuestion(int index) {
    return QUESTIONS[index][0];
  }

  /**
   * Get a answer given its domain question and index
   * @param q index of the question for which the answers belongs to
   * @param a index of the answer trying to be accessed
   * @return a string which is the question trying to be retrieved
   */
  public String getChoice(int q, int a) {
    if (q > 4 || q < 0) {
      return "Question does not exist";
    }
    if (a > 3 || a < 1) {
      return "Answer does not exist";
    }
    return QUESTIONS[q][a];
  }

  /**
   * Get the answer for a given question
   * @param q index of the question
   * @return the answer for the index question
   */
  public String correctAnswer(int q) {
    if (q > 4 || q < 0) {
      return "error";
    }
    return QUESTIONS[q][ANSWERS[q]];
  }

  /**
   * Increases the current question and calls function to perform updates
   * @param v view object
   */
  public void nextQuestion(View v) {
    this.qNum++;
    resetBtnColors();
    setQuestion(qNum);
  }

  /**
   * Performs updates in the view layer according to a given index
   * @param qNum the index of the question at hand
   */
  public void setQuestion(int qNum) {
    if (qNum > 4) {
      // TODO: show results
      finish();
    } else {
      int n = qNum + 1;
      int percentage = (qNum*100 / 5);
      String s = "Question: " + n;
      questionNumber.setText(s);
      progressBar.setProgress(percentage);
      currentQuestion.setText(getQuestion(qNum));
      for(int i = 0; i < choices.length; i++){
        choices[i].setText(getChoice(qNum, i+1));
      }
    }
  }

  public void clickHandler(View view) {
    Button ref = findViewById(view.getId());
    // record the answer
    ans[this.qNum] = ref.getText().toString();
    resetBtnColors();
    ref.setBackgroundResource(R.drawable.white_fill_box);
    ref.setTextColor( getResources().getColor(R.color.retro_blue) );
  }

  public void resetBtnColors(){
    for (Button btn : choices){
      btn.setBackgroundResource(R.drawable.white_outline_box);
      btn.setTextColor( getResources().getColor(R.color.pure_white) );
    }
  }
}