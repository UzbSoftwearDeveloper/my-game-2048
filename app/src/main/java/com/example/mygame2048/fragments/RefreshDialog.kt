package com.example.mygame2048.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mygame2048.R
import com.example.mygame2048.databinding.FragmentRefreshBinding

class RefreshDialog: DialogFragment() {
    private var listener : (() -> Unit )? = null

    fun setListener(listener: (() -> Unit)){
        this.listener = listener
    }
    private var _binding: FragmentRefreshBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRefreshBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNot.setOnClickListener {
            dismiss()
        }
        binding.btnRestart.setOnClickListener {
            listener?.invoke()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.let { window ->
            window.setBackgroundDrawableResource(android.R.color.transparent)

            val width = (resources.displayMetrics.widthPixels * 0.88).toInt()
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}