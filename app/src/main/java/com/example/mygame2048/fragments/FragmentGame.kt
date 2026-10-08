package com.example.mygame2048.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.mygame2048.R
import com.example.mygame2048.databinding.FargmentGameBinding
import com.example.mygame2048.detector.MyTouchListener
import com.example.mygame2048.model.AppRepository
import com.example.mygame2048.model.BackgroundUtil
import com.example.mygame2048.model.SideEnum

class FragmentGame: Fragment() {
    private var _binding : FargmentGameBinding? = null
    private val binding get() = _binding!!
    private val list = mutableListOf<TextView>()
    private val repository = AppRepository()
    private lateinit var prefrens: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FargmentGameBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefrens = requireActivity().getSharedPreferences("game2048", Context.MODE_PRIVATE)
        binding.records.text = prefrens.getInt("records",0).toString()
        if (prefrens.getBoolean("saved", false)) {
            val savedString = prefrens.getString("matrix", "")
            val savedStringOld = prefrens.getString("oldMatrix", "")

            if (!savedString.isNullOrEmpty()) {
                val numbers = savedString.split(" ").filter { it.isNotEmpty() }
                var index = 0
                for (i in 0 until 4) {
                    for (j in 0 until 4) {
                        repository.matrix[i][j] = numbers[index++].toInt()
                    }
                }
                val count = prefrens.getInt("score", 0).toString()
                binding.currentState.text = count
                repository.setSum(count.toInt())
            }
            if (!savedStringOld.isNullOrEmpty()){
                val numbers = savedStringOld.split(" ").filter { it.isNotEmpty() }
                var index = 0
                for (i in 0 until 4) {
                    for (j in 0 until 4) {
                        repository.oldMatrix[i][j] = numbers[index++].toInt()
                    }
                }
            }
        }
        loadViews()
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setMovedSideListener {
            when(it){
                SideEnum.UP -> {
                    val oldState = binding.currentState.text.toString().toInt()
                    repository.setCloneMatrix()
                    repository.moveUp()
                    showMatrix()
                    if (!repository.check()){
                        val dialog = FragmentDialog()

                        dialog.show(parentFragmentManager,"dialog")
                    }
                    if (oldState != binding.currentState.text.toString().toInt()){
                        binding.btnBack.visibility = View.VISIBLE
                    }
                }

                SideEnum.DOWN ->{
                    val oldState = binding.currentState.text.toString().toInt()
                    repository.setCloneMatrix()
                    repository.moveDown()
                    showMatrix()
                    if (!repository.check()){
                        val dialog = FragmentDialog()
                        dialog.show(parentFragmentManager,"dialog")
                    }
                    if (oldState != binding.currentState.text.toString().toInt()){
                        binding.btnBack.visibility = View.VISIBLE
                    }
                }

                SideEnum.LEFT ->{
                    val oldState = binding.currentState.text.toString().toInt()
                    repository.setCloneMatrix()
                    repository.moveLeft()
                    showMatrix()
                    if (!repository.check()){
                        val dialog = FragmentDialog()
                        dialog.show(parentFragmentManager,"dialog")
                    }
                    if (oldState != binding.currentState.text.toString().toInt()){
                        binding.btnBack.visibility = View.VISIBLE
                    }
                }

                SideEnum.RIGHT ->{
                    val oldState = binding.currentState.text.toString().toInt()
                    repository.setCloneMatrix()
                    repository.moveRight()
                    showMatrix()
                    if (!repository.check()){
                        val dialog = FragmentDialog()
                        dialog.show(parentFragmentManager,"dialog")
                    }
                    if (oldState != binding.currentState.text.toString().toInt()){
                        binding.btnBack.visibility = View.VISIBLE
                    }
                }
            }
        }
        binding.container.setOnTouchListener(myTouchListener)
        showMatrix()
        binding.btnHome.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnRestart.setOnClickListener {
//            prefrens.edit().putBoolean("saved",false)
//            repository.newMatrix()
//            showMatrix()
            val dialog = RefreshDialog()
            dialog.setListener {
                prefrens.edit(){putBoolean("saved",false)}
                repository.newMatrix()
                showMatrix()
            }
            dialog.show(parentFragmentManager,"dialog")
        }
        if (repository.areaMatrixEqual() || repository.getSumMatrix() == 4){
            binding.btnBack.visibility = View.INVISIBLE
        }
        binding.btnBack.setOnClickListener {
            if (binding.currentState.text.toString().toInt() != 0){
                repository.getCloneMatrix()
                showMatrix()
                binding.btnBack.visibility = View.INVISIBLE
            }
        }
    }
    fun loadViews(){
        for (i in 0 until 4){
            list.add(binding.line1.getChildAt(i) as TextView)
        }
        for (i in 0 until 4){
            list.add(binding.line2.getChildAt(i) as TextView)
        }
        for (i in 0 until 4){
            list.add(binding.line3.getChildAt(i) as TextView)
        }
        for (i in 0 until 4){
            list.add(binding.line4.getChildAt(i) as TextView)
        }
    }
    fun showMatrix(){
        for (i in 0 until list.size){
            list[i].text = repository.matrix[i/4][i%4].toString()
            if (list[i].text.toString() == "0") list[i].text = ""
            list[i].setBackgroundResource(BackgroundUtil.getBackground(repository.matrix[i/4][i%4]))
        }
        binding.currentState.text = repository.getSum().toString()
        if (repository.getSum() > binding.records.text.toString().toInt()){
            binding.records.text = repository.getSum().toString()
        }
        if (repository.areaMatrixEqual() || repository.getSumMatrix() == 4){
            binding.btnBack.visibility = View.INVISIBLE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        val sb = StringBuilder()
        val oldMatrix = StringBuilder()
        for (i in 0 until 4){
            for (j in 0 until 4){
                sb.append(repository.matrix[i][j])
                sb.append(" ")
            }
        }
        for (i in 0 until 4){
            for (j in 0 until 4){
                oldMatrix.append(repository.oldMatrix[i][j])
                oldMatrix.append(" ")
            }
        }
        prefrens.edit { putString("matrix", sb.toString()) }
        prefrens.edit { putString("oldMatrix", oldMatrix.toString()) }
        prefrens.edit { putInt("records", binding.records.text.toString().toInt()) }
        prefrens.edit { putBoolean("saved", true) }
        prefrens.edit { putInt("score", binding.currentState.text.toString().toInt()) }
        if (!repository.check() || repository.getSumMatrix() == 4){
            prefrens.edit { putBoolean("saved", false) }
        }
    }
}