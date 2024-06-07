import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var totalQuestionsTextView: TextView? = null
    var questionTextView: TextView? = null
    var ansA: Button? = null
    var ansB: Button? = null
    var ansC: Button? = null
    var ansD: Button? = null
    var submitBtn: Button? = null

    var score: Int = 0
    var totalQuestion: Int = QuestionAnswer.question.length
    var currentQuestionIndex: Int = 0
    var selectionAnswer: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalQuestionsTextView = findViewById<TextView>(R.id.total_question)
        questionTextView = findViewById<TextView>(R.id.question)
        ansA = findViewById<Button>(R.id.ans_A)
        ansB = findViewById<Button>(R.id.ans_B)
        ansC = findViewById<Button>(R.id.ans_C)
        ansD = findViewById<Button>(R.id.ans_D)
        submitBtn = findViewById<Button>(R.id.submit_btn)

        ansA.setOnClickListener(this)
        ansB.setOnClickListener(this)
        ansC.setOnClickListener(this)
        ansD.setOnClickListener(this)
        submitBtn.setOnClickListener(this)

        totalQuestionsTextView.setText("Total questions:$totalQuestion")

        loadNewQuestion()
    }

    override fun onClick(view: View) {
        ansA!!.setBackgroundColor(Color.WHITE)
        ansB!!.setBackgroundColor(Color.WHITE)
        ansC!!.setBackgroundColor(Color.WHITE)
        ansD!!.setBackgroundColor(Color.WHITE)

        val clickedButton = view as Button
        if (clickedButton.id == R.id.submit_btn) {
            if (selectionAnswer == QuestionAnswer.correctAnswers.get(currentQuestionIndex)) {
                score++
            }
            currentQuestionIndex++
            loadNewQuestion()
        } else {
            selectionAnswer = clickedButton.text.toString()
            clickedButton.setBackgroundColor(Color.MAGENTA)
        }
    }

    fun loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz()
            return
        }
        questionTextView.setText(QuestionAnswer.question.get(currentQuestionIndex))
        ansA.setText(QuestionAnswer.choices.get(currentQuestionIndex).get(0))
        ansB.setText(QuestionAnswer.choices.get(currentQuestionIndex).get(1))
        ansC.setText(QuestionAnswer.choices.get(currentQuestionIndex).get(2))
        ansD.setText(QuestionAnswer.choices.get(currentQuestionIndex).get(3))
    }

    fun finishQuiz() {
        var passStatus = ""
        passStatus = if (score > totalQuestion * 0.60) {
            "Passed"
        } else {
            "Failed"
        }

        AlertDialog.Builder(this)
            .setTitle(passStatus)
            .setMessage("Score is $score out of $totalQuestion")
            .setPositiveButton(
                "Restart"
            ) { dialogInterface: DialogInterface?, i: Int -> restartQuiz() }
            .setCancelable(false)
            .show()
    }

    fun restartQuiz() {
        score = 0
        currentQuestionIndex = 0
        loadNewQuestion()
    }
}