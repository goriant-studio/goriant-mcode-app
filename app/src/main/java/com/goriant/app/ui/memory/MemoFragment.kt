package com.goriant.app.ui.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goriant.app.R
import com.goriant.app.databinding.FragmentMemoryBinding
import com.goriant.app.databinding.ItemTransformBinding

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
class MemoFragment : Fragment() {

    private var _binding: FragmentMemoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val memoViewModel = ViewModelProvider(this).get(MemoViewModel::class.java)
        _binding = FragmentMemoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerviewMemory
        val adapter = MemoAdapter()
        recyclerView.adapter = adapter
        memoViewModel.texts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MemoAdapter :
        ListAdapter<String, MemoViewHolder>(object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }) {

        private val memoDrawables = listOf(
            R.mipmap.dragon_green,
            R.drawable.avatar_10,
            R.drawable.avatar_11,
            R.drawable.avatar_12,
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_3,
            R.drawable.avatar_4,
            R.drawable.avatar_5,
            R.drawable.avatar_6,
            R.drawable.avatar_7,
            R.drawable.avatar_8,
            R.drawable.avatar_9,
            R.drawable.avatar_13,
            R.drawable.avatar_14,
            R.drawable.avatar_15,
            R.drawable.avatar_16,
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
            val binding = ItemTransformBinding.inflate(LayoutInflater.from(parent.context))
            return MemoViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
            holder.textView.text = getItem(position)
            holder.memoImageView.setImageDrawable(
                ResourcesCompat.getDrawable(holder.memoImageView.resources, memoDrawables[position], null)
            )
        }
    }

    class MemoViewHolder(binding: ItemTransformBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val memoImageView: ImageView = binding.imageViewItemTransform
        val textView: TextView = binding.textViewItemTransform
    }
}