package com.apaul9.colorbuster.ui.otherfrags

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.apaul9.colorbuster.R
import com.apaul9.colorbuster.ui.main.MainViewModel


class DetailFragment : Fragment() {


    private lateinit var colorName: TextView
    private lateinit var colorHex: TextView
    private lateinit var backBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        colorName = view.findViewById(R.id.detailName_textView)
        colorHex = view.findViewById(R.id.detailHexVal_textView)
        backBtn = view.findViewById(R.id.back_button)

        val args = requireArguments()
        val name = args.getString("colorName")
        val hex = args.getString("colorHex")

        colorName.text = name
        colorHex.text = hex
        view.setBackgroundColor(parseColor(hex))


        backBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
        }

        return view

    }

}