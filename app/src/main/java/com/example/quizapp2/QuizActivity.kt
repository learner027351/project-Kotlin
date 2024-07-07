package com.example.quizapp2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp2.databinding.ActivityLoginBinding
import com.example.quizapp2.databinding.ActivityQuizBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var list:ArrayList<Question>
    private var count:Int=0
    private var score=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuizBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        list= ArrayList<Question>()
        Firebase.firestore.collection("quiz").get().addOnSuccessListener { 
            doct->
               list.clear()
               for(i in doct.documents){

                   var question=i.toObject(Question::class.java)
                   list.add(question!!)
               }
            binding.question.setText(list.get(0).question)
            binding.option1.setText(list.get(0).option1)
            binding.option2.setText(list.get(0).option2)
            binding.option3.setText(list.get(0).option3)
        }
//        list.add(Question("who is the pm of india","Modi","Maurya","Verma","Modi"))
//        list.add(Question("who is the pm of australia","smith","warner","mazwell","warner"))
//        list.add(Question("who is the pm of england","morgan","root","buttler","morgan"))
//        list.add(Question("who is the pm of pakistan","khan","afridi","akram","khan"))
//        if(list.size>0) {
//            binding.question.setText(list.get(0).question)
//            binding.option1.setText(list.get(0).option1)
//            binding.option2.setText(list.get(0).option2)
//            binding.option3.setText(list.get(0).option3)
//        }
        binding.option1.setOnClickListener {
            nextData(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener {
            nextData(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener {
            nextData(binding.option3.text.toString())
        }

//        binding.question.setText(list.get(0).question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun nextData(i: String) {
        if(count<list.size){
            if(list.get(count).ans.equals(i))
                score++
        }
        count++
        if(count>=list.size){
            val intent=Intent(this,Scoreactivity::class.java)
            intent.putExtra("SCORE",score)
            startActivity(intent)
            finish()
        }
        else{
            binding.question.setText(list.get(count).question)
            binding.option1.setText(list.get(count).option1)
            binding.option2.setText(list.get(count).option2)
            binding.option3.setText(list.get(count).option3)
        }


    }
}