package com.apaul9.colorbuster.ui.main

import android.content.SharedPreferences
import android.graphics.Color.parseColor
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apaul9.colorbuster.R
import com.apaul9.colorbuster.ui.model.ColorValues

class MainFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var prefs: SharedPreferences

    companion object {
        const val POSITION = "adapter_position"
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        prefs = PreferenceManager.getDefaultSharedPreferences(view.context)

        recycler = view.findViewById(R.id.colorScroll_recyclerView)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.smoothScrollToPosition(prefs.getInt(POSITION, 0))
        Handler(Looper.getMainLooper()).postDelayed({
            recycler.findViewHolderForAdapterPosition(
                prefs.getInt(
                    POSITION,
                    0
                )
            )?.itemView?.performClick()
        }, 200L)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.colors.observe(viewLifecycleOwner) {
            recycler.adapter = ColorAdapter(it)
        }
    }

    private inner class ColorBusterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var color: ColorValues
        private val colorCard: CardView = itemView.findViewById(R.id.colorCard_cardView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
        }

        fun bind(color: ColorValues) {
            this.color = color
            colorCard.setCardBackgroundColor(parseColor(color.hexString))
        }

    }

    private inner class ColorAdapter(private val colors: List<ColorValues>) : RecyclerView.Adapter<ColorBusterViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorBusterViewHolder {
            val view = layoutInflater.inflate(R.layout.recycler_item, parent, false)
            return ColorBusterViewHolder(view)
        }

        override fun onBindViewHolder(holder: ColorBusterViewHolder, position: Int) {
            val color = colors[position]
            holder.bind(color)
        }

        override fun getItemCount(): Int = colors.size

    }
}