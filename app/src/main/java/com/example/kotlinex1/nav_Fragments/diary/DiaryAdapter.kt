package com.example.kotlinex1.nav_Fragments.diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinex1.R
import com.example.kotlinex1.databinding.RowCardTemplateBinding


class DiaryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<DiaryVO>()
        set(value) {
            val size = this.data.size
            this.data.clear()
            notifyItemRangeRemoved(0, size)
            this.data.addAll(value)
            notifyItemRangeInserted(0, value.size)
        }

    fun remove(position: Int) {
        this.data.removeAt(position)
        notifyItemRemoved(position)
    }

    private var layoutType = 0

    var onCardClickListener: ((_id: Int, position: Int) -> Unit)? = null
    var onCardLongClickListener: ((_id: Int, position: Int) -> Unit)? = null

    // ViewHolder = Row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiaryViewHolder(
            RowCardTemplateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    // RecyclerView
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DiaryViewHolder) {
            holder.bind(data[position])

            holder.onCardClickListener = {
                onCardClickListener?.let { it(data[position]._id, holder.adapterPosition) }
            }

            holder.onCardLongClickListener = {
                onCardLongClickListener?.let { it(data[position]._id, holder.adapterPosition) }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }//리사이클러 뷰 에서 로우를 몇개를 보여줄지. ㅇㅇ

    // 어떤 뷰홀더를 쓸거냐
    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return position
    }


}