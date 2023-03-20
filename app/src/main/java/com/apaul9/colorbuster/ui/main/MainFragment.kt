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
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apaul9.colorbuster.R
import com.apaul9.colorbuster.ui.model.ColorValues

class MainFragment : Fragment() {


    private val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph)
    private lateinit var recycler: RecyclerView
    private lateinit var prefs: SharedPreferences

    companion object {
        const val POSITION = "adapter_position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        prefs = PreferenceManager.getDefaultSharedPreferences(view.context)

        recycler = view.findViewById(R.id.colorScroll_recyclerView)
        recycler.layoutManager = LinearLayoutManager(context)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.colors.observe(viewLifecycleOwner) {
            recycler.adapter = ColorAdapter(it)
        }

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // RecyclerView was not scrolled
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(prefs.getInt(POSITION, 0))
                    if (viewHolder != null) {
                        (viewHolder as ColorBusterViewHolder) /*.setWasScrolled(false)*/
                    }
                } else {
                    // RecyclerView was scrolled
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(prefs.getInt(POSITION, 0))
                    if (viewHolder != null) {
                        (viewHolder as ColorBusterViewHolder) /*.setWasScrolled(true)*/
                    }
                }
            }
        })
    }

    private inner class ColorBusterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var color: ColorValues
        private val colorName: TextView = itemView.findViewById(R.id.colorName_textView)
        private val colorCard: CardView = itemView.findViewById(R.id.colorCard_cardView)

//        private var wasScrolled = true

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            if (!wasScrolled) {
                val bundle = Bundle().apply {
                    putString("colorName", color.name)
                    putString("colorHex", color.hexString)
                }
                // Navigate to the DetailFragment with the arguments
                v?.findNavController()?.navigate(R.id.action_mainFragment_to_detailFragment, bundle)

//            } else {
//                wasScrolled = false
//            }
        }

        fun bind(color: ColorValues) {
            this.color = color
            colorName.text = color.name
            colorCard.setCardBackgroundColor(parseColor(color.hexString))
        }

//        fun setWasScrolled(wasScrolled: Boolean) {
//            this.wasScrolled = wasScrolled
//        }

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