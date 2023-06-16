package com.example.wordy

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.random.Random


val imgarray= arrayOf(R.drawable.cat,R.drawable.dog,R.drawable.hat,
    R.drawable.key,R.drawable.map,R.drawable.pig,R.drawable.rabbit,R.drawable.sun,R.drawable.water)
val engarray=arrayOf("cat","dog","hat","key","map","pig","rabbit","sun","water")
val korarray= arrayOf("고양이","개","모자","열쇠","지도","돼지","토끼","해","물")
// step 2, 3 용
val imgarray2= arrayOf(R.drawable.bell,R.drawable.cat,R.drawable.dog,R.drawable.hat,
    R.drawable.key,R.drawable.map,R.drawable.pig,R.drawable.rabbit,R.drawable.sun,R.drawable.water)
val engarray2=arrayOf("bell","cat","dog","hat","key","map","pig","rabbit","sun","water")
val korarray2= arrayOf("종","고양이","개","모자","열쇠","지도","돼지","토끼","해","물")
val audioFiles = arrayOf(R.raw.cat, R.raw.dog, R.raw.hat, R.raw.key, R.raw.map, R.raw.pig, R.raw.rabbit, R.raw.sun, R.raw.water)
val audioFiles2 = arrayOf(R.raw.bell, R.raw.cat, R.raw.dog, R.raw.hat, R.raw.key, R.raw.map, R.raw.pig, R.raw.rabbit, R.raw.sun, R.raw.water)


class FirstScreen: Fragment(R.layout.first_screen){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.start_btn).setOnClickListener{
            findNavController().navigate(R.id.action_firstScreen_to_mainScreen)
        }
    }
}
class MainScreen: Fragment(R.layout.main_screen){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.step1).setOnClickListener{
            findNavController().navigate(R.id.action_mainScreen_to_lv1Main)
        }
        view.findViewById<Button>(R.id.step2).setOnClickListener{
            findNavController().navigate(R.id.action_mainScreen_to_lv2Main)
        }
        view.findViewById<Button>(R.id.step3).setOnClickListener{
            findNavController().navigate(R.id.action_mainScreen_to_lv3Main)
        }
    }
}
class Lv1Main: Fragment(R.layout.lv1_main){
    private lateinit var mediaPlayer: MediaPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.lv1_step1_btn).setOnClickListener{
            findNavController().navigate(R.id.action_lv1Main_to_step1)
            playAudio()
        }
        view.findViewById<Button>(R.id.lv1_step2_btn).setOnClickListener{
            findNavController().navigate(R.id.action_lv1Main_to_step2)
        }
        view.findViewById<Button>(R.id.lv1_step3_btn).setOnClickListener{
            findNavController().navigate(R.id.action_lv1Main_to_step3)
        }
        view.findViewById<Button>(R.id.lv1_step4_btn).setOnClickListener{
            findNavController().navigate(R.id.action_lv1Main_to_step4)
        }
    }
    private fun playAudio() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bell)
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
class Lv2Main: Fragment(R.layout.lv2_main)
class Lv3Main: Fragment(R.layout.lv3_main)

class Step1:Fragment(R.layout.step1){
    private lateinit var mediaPlayer: MediaPlayer
    var j = 1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var i=1
        val img=view.findViewById<ImageView>(R.id.imageView3)
        val eng=view.findViewById<TextView>(R.id.textView)
        val kor=view.findViewById<TextView>(R.id.textView2)
        val progress=view.findViewById<ProgressBar>(R.id.progressBar)
        val btn=view.findViewById<Button>(R.id.next_btn)
        btn.setOnClickListener{
            img.setImageResource(imgarray[i-1])
            eng.setText(engarray[i-1])
            kor.setText(korarray[i-1])
            progress.setProgress(i+1)
            playAudio()
            i++
            if(i==10){
                btn.setText("next step")
                btn.setOnClickListener {
                    findNavController().navigate(R.id.action_step1_to_step2)
                }
            }
        }
        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            findNavController().navigate(R.id.action_step1_to_lv1Main)
        }
    }
    private fun playAudio() {
        mediaPlayer = MediaPlayer.create(requireContext(), audioFiles[j - 1])
        mediaPlayer.start()
        j++
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}

class Step2 : Fragment(R.layout.step2) {
    private lateinit var currentImage: ImageView
    private lateinit var answerButtons: List<Button>
    private var currentIndex: Int = -1
    private var shuffledAnswers: List<String> = emptyList()
    private lateinit var shuffledIndexes: List<Int> // lateinit 제거

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.step2, container, false)

        currentImage = view.findViewById(R.id.imageView3)
        answerButtons = listOf(
            view.findViewById(R.id.button),
            view.findViewById(R.id.button2),
            view.findViewById(R.id.button3),
            view.findViewById(R.id.button4)
        )

        // shuffledIndexes 초기화
        shuffledIndexes = (0 until imgarray2.size).shuffled()

        val btn2 = view.findViewById<Button>(R.id.next_btn2)
        btn2?.setOnClickListener {
            findNavController().navigate(R.id.action_step2_to_step3)
        }

        setNextImageAndAnswers()

        return view
    }

    private fun setNextImageAndAnswers() {
        currentIndex++
        if (currentIndex >= imgarray2.size) {
            currentIndex = 0
            shuffledIndexes = (0 until imgarray2.size).shuffled()
        }

        val shuffledIndex = shuffledIndexes[currentIndex]

        // 현재 사진 설정
        currentImage.setImageResource(imgarray2[shuffledIndex])

        // 정답 선택지 설정
        val correctAnswer = engarray2[shuffledIndex]
        shuffledAnswers = engarray2.toMutableList().apply { removeAt(shuffledIndex) }.shuffled()
        val randomIndexes = List(3) { Random.nextInt(0, shuffledAnswers.size) }
        val answerChoices = randomIndexes.map { index -> shuffledAnswers[index] }.toMutableList()
        answerChoices.add(Random.nextInt(0, answerChoices.size), correctAnswer)

        answerButtons.forEachIndexed { index, button ->
            button.text = answerChoices[index]
            button.setOnClickListener {
                if (button.text == correctAnswer) {
                    // 정답 처리 로직 작성
                    setNextImageAndAnswers()
                } else {
                    // 오답 처리 로직 작성
                }
            }
        }
    }
}

class Step3 : Fragment(R.layout.step3) {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var currentWord: TextView
    private lateinit var answerButtons: List<Button>
    private var currentIndex: Int = -1
    private var shuffledAnswers: List<String> = emptyList()
    private lateinit var shuffledIndexes: List<Int> // lateinit 제거

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.step3, container, false)

        currentWord = view.findViewById(R.id.textView4)
        answerButtons = listOf(
            view.findViewById(R.id.button),
            view.findViewById(R.id.button2),
            view.findViewById(R.id.button3),
            view.findViewById(R.id.button4)
        )

        // shuffledIndexes 초기화
        shuffledIndexes = (0 until engarray2.size).shuffled()

        val btn3 = view.findViewById<Button>(R.id.next_btn3)
        btn3?.setOnClickListener {
            findNavController().navigate(R.id.action_step3_to_step4)
        }

        setNextImageAndAnswers()

        val imageButton = view.findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener {
            playAudio()
        }

        return view
    }

    private fun setNextImageAndAnswers() {
        currentIndex++
        if (currentIndex >= engarray2.size) {
            currentIndex = 0
            shuffledIndexes = (0 until engarray2.size).shuffled()
        }

        val shuffledIndex = shuffledIndexes[currentIndex]

        // 현재 사진 설정
        currentWord.setText(engarray2[shuffledIndex])

        // 정답 선택지 설정
        val correctAnswer = korarray2[shuffledIndex]
        shuffledAnswers = korarray2.toMutableList().apply { removeAt(shuffledIndex) }.shuffled()
        val randomIndexes = List(3) { Random.nextInt(0, shuffledAnswers.size) }
        val answerChoices = randomIndexes.map { index -> shuffledAnswers[index] }.toMutableList()
        answerChoices.add(Random.nextInt(0, answerChoices.size), correctAnswer)

        answerButtons.forEachIndexed { index, button ->
            button.text = answerChoices[index]
            button.setOnClickListener {
                if (button.text == correctAnswer) {
                    // 정답 처리 로직 작성
                    setNextImageAndAnswers()
                } else {
                    // 오답 처리 로직 작성
                }
            }
        }
    }

    private fun playAudio() {
        val shuffledIndex = shuffledIndexes[currentIndex]
        val wordName = engarray2[shuffledIndex]
        val audioIndex = audioFiles2.indexOf(getAudioFileByName(wordName))
        if (audioIndex != -1) {
            mediaPlayer = MediaPlayer.create(requireContext(), audioFiles2[audioIndex]) // 대응하는 오디오 재생
            mediaPlayer.start()
        }
    }

    private fun getAudioFileByName(imageName: String): Int {
        val resourceId = resources.getIdentifier(imageName, "raw", requireContext().packageName)
        return if (resourceId != 0) resourceId else -1
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}

class Step4 : Fragment(R.layout.step4) {
    private var currentIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.homeButton).setOnClickListener{
            findNavController().navigate(R.id.action_step4_to_lv1Main)
        }

        val editText = view.findViewById<EditText>(R.id.editTextTextPersonName)
        val textView = view.findViewById<TextView>(R.id.textView3)

        showNextQuestion(textView)

        editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                val enteredText = editText.text.toString()
                val textViewText = textView.text.toString()

                val correctAnswer = engarray2[currentIndex]

                if (enteredText == correctAnswer) {
                    showNextQuestion(textView)
                    Toast.makeText(requireContext(), "정답입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "오답입니다.", Toast.LENGTH_SHORT).show()
                }

                editText.text.clear() // 입력한 내용을 지움
                true
            } else {
                false
            }
        }

    }

    private fun showNextQuestion(textView: TextView) {
        currentIndex = (currentIndex + 1) % korarray2.size
        textView.text = korarray2[currentIndex]
    }
}