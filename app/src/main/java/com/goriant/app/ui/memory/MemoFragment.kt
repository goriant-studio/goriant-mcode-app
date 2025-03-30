package com.goriant.app.ui.memory

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goriant.app.databinding.FragmentMemoryBinding
import com.goriant.app.databinding.ItemTransformBinding
import android.util.Log

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
        val adapter = MemoAdapter(requireContext())
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

    class FoodDrawablesGenerator {
        companion object {
            fun generateFoodDrawables(context: Context, count: Int): List<Int> {
                return (1..count).map { index ->
                    val resourceName = "fruit_$index"
                    context.resources.getIdentifier(
                        resourceName,
                        "drawable",
                        context.packageName
                    )
                }.filter { it != 0 } // Remove any drawables that weren't found
            }
        }
    }

    class MemoAdapter(private val context: Context) :
        ListAdapter<String, MemoViewHolder>(object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }) {

//        private val mapemoDrawables = listOf(
//            R.mipmap.dragon_green,
//            R.drawable.fruit_1,
//            R.drawable.fruit_2,
//            R.drawable.avatar_12,
//            R.drawable.avatar_1,
//            R.drawable.avatar_2,
//            R.drawable.avatar_3,
//            R.drawable.avatar_4,
//            R.drawable.avatar_5,
//            R.drawable.avatar_6,
//            R.drawable.avatar_7,
//            R.drawable.avatar_8,
//            R.drawable.avatar_9,
//            R.drawable.avatar_13,
//            R.drawable.avatar_14,
//            R.drawable.avatar_15,
//            R.drawable.avatar_16,
//        )

        private val memoDrawables by lazy {
            FoodDrawablesGenerator.generateFoodDrawables(context, 100);
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
            val binding = ItemTransformBinding.inflate(LayoutInflater.from(parent.context))
            return MemoViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
            Log.i("TAG", "onBindViewHolder $position")

            val item = getItem(position)

            holder.textView.text = getItem(position)
            holder.memoImageView.setImageDrawable(
                ResourcesCompat.getDrawable(holder.memoImageView.resources, memoDrawables[0], null)
            )
            holder.memoImageView.setOnClickListener{
                Log.i("TAG", "Clicked $position clicked $memoDrawables[position]")
                Toast.makeText(holder.memoImageView.context, "Clicked $position clicked", Toast.LENGTH_SHORT).show()

                // 🔥 Scale animation with bounce
                val view = it

                // Scale up
                val scaleXUp = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.2f)
                val scaleYUp = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.2f)

                // Scale down
                val scaleXDown = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.2f, 1f)
                val scaleYDown = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.2f, 1f)

                // Shake (left-right movement)
                val shake = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, -10f, 10f, -10f, 10f, 0f)

                // Duration for each
                scaleXUp.duration = 100
                scaleYUp.duration = 100
                scaleXDown.duration = 100
                scaleYDown.duration = 100
                shake.duration = 300

                // Play scale up → down → shake
                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(
                    AnimatorSet().apply { playTogether(scaleXUp, scaleYUp) },
                    AnimatorSet().apply { playTogether(scaleXDown, scaleYDown) },
                    shake
                )

                animatorSet.start()
            }
        }
    }

    class MemoViewHolder(binding: ItemTransformBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val memoImageView: ImageView = binding.imageViewItemTransform
        val textView: TextView = binding.textViewItemTransform
    }
}