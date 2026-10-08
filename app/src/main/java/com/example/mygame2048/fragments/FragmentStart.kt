package com.example.mygame2048.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mygame2048.R
import com.example.mygame2048.databinding.FragmentStartBinding
import androidx.core.content.edit

class FragmentStart: Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefrens: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentStartBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefrens = requireContext().getSharedPreferences("game2048", Context.MODE_PRIVATE)
        if (!prefrens.getBoolean("saved",false)){
            binding.btnContinue.visibility = View.GONE
        }else{
            binding.btnContinue.visibility = View.VISIBLE
        }
        binding.btnStart.setOnClickListener {
            prefrens.edit { putBoolean("saved", false) }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentGame())
                .addToBackStack(null)
                .commit()
        }
        binding.btnContinue.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentGame())
                .addToBackStack(null)
                .commit()
        }
        binding.btnAbout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentAbout())
                .addToBackStack(null)
                .commit()
        }
        binding.share.setOnClickListener {
            val highScore = prefrens.getInt("records", 0)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "2048 O'yini")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "2048 o'yinini sinab ko'ring! 🎮\n" +
                            "Mening eng yuqori natijam: $highScore\n" +
                            "Raqamlarni birlashtiring va 2048 ga yeting!"
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Ulashish..."))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}