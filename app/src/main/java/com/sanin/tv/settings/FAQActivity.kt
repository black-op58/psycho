package com.sanin.tv.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.R
import com.sanin.tv.databinding.ActivityFaqBinding
import com.sanin.tv.util.customAlertDialog

class FAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding

    private data class FAQItem(val questionResId: Int, val answerResId: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.devsTitle2.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val faqItems = listOf(
            FAQItem(R.string.faq_question_1, R.string.faq_answer_1),
            FAQItem(R.string.faq_question_2, R.string.faq_answer_2),
            FAQItem(R.string.faq_question_3, R.string.faq_answer_3),
            FAQItem(R.string.faq_question_4, R.string.faq_answer_4),
            FAQItem(R.string.faq_question_5, R.string.faq_answer_5),
            FAQItem(R.string.faq_question_6, R.string.faq_answer_6),
        )

        binding.devsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FAQActivity)
            adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<FAQViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
                    val button = Button(parent.context).apply {
                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            64.dpToPx()
                        )
                        setBackgroundResource(R.drawable.ui_bg)
                        setTextColor(androidx.core.content.ContextCompat.getColorStateList(parent.context, R.color.bg_opp))
                        setAllCaps(false)
                        textAlignment = android.view.View.TEXT_ALIGNMENT_VIEW_START
                        setPadding(32.dpToPx(), 0, 64.dpToPx(), 0)
                        typeface = androidx.core.content.res.ResourcesCompat.getFont(parent.context, R.font.poppins_bold)
                    }
                    return FAQViewHolder(button)
                }
                override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
                    holder.bind(faqItems[position])
                }
                override fun getItemCount() = faqItems.size
            }
        }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}

class FAQViewHolder(itemView: android.view.View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    private var questionResId = 0
    private var answerResId = 0

    fun bind(item: FAQActivity.FAQItem) {
        questionResId = item.questionResId
        answerResId = item.answerResId
        (itemView as? Button)?.text = itemView.context.getString(item.questionResId)
        itemView.setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(itemView.context)
                .setTitle(itemView.context.getString(item.questionResId))
                .setMessage(itemView.context.getString(item.answerResId))
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }
}
