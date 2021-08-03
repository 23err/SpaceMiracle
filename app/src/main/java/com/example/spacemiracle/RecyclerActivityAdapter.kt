package com.example.spacemiracle

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.actiivity_recycler_item_earth.view.*
import kotlinx.android.synthetic.main.activity_recycler_item_mars.view.*

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    fun appendItem() {
        data.add(generateItem())
        notifyDataSetChanged()
    }

    private fun generateItem() = Data("Mars", "") to false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EARTH -> EarthViewHolder(
                inflater.inflate(
                    R.layout.actiivity_recycler_item_earth, parent, false
                ) as View
            )
            TYPE_MARS -> MarsViewHolder(
                inflater.inflate(
                    R.layout.activity_recycler_item_mars,
                    parent,
                    false
                ) as View
            )
            else -> HeaderViewHolder(
                inflater.inflate(
                    R.layout.rv_item_header,
                    parent,
                    false
                ) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if(toPosition > fromPosition) toPosition-1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position  )
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }


    inner class EarthViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<Data, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.descriptionTextView.text = data.first.someDescription
                itemView.wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }

        override fun onItemSelected() {

        }

        override fun onItemClear() {
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

        override fun bind(data: Pair<Data, Boolean>) = with(itemView) {
            marsImageView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            addItemImageView.setOnClickListener { addItem() }
            removeItemImageView.setOnClickListener { removeItem() }
            moveItemUp.setOnClickListener{moveUp()}
            moveItemDown.setOnClickListener{moveDown()}

            marsDescriptionTextView.visibility = if (data.second) View.VISIBLE else View.GONE
            marsTextView.setOnClickListener{
                toggleText()
            }

            itemView.dragHandleImageView.setOnTouchListener{_,event->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this@MarsViewHolder)
                }
                false
            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also {
                data.removeAt(layoutPosition).apply {
                    data.add(it - 1, this)
                }
                notifyItemMoved(it, it - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also {
                data.removeAt(layoutPosition).apply {
                    data.add(layoutPosition + 1, this)
                }
                notifyItemMoved(it, it + 1)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }

    }



    inner class HeaderViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<Data, Boolean>) {
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(data)
            }
        }

        override fun onItemSelected() {
        }

        override fun onItemClear() {
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Pair<Data, Boolean>)
    }

    interface ItemTouchHelperViewHolder {

        fun onItemSelected()

        fun onItemClear()
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }


}