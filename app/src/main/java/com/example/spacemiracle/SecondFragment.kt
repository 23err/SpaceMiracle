package com.example.spacemiracle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.second_fragment.*

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arrayListOf(
            Data("Earth") to false,

            )
        data.add(0, Data("Header") to false)

        val adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Pair<Data, Boolean>) {
                    Toast.makeText(
                        this@SecondFragment.context,
                        data.first.someText,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, data,
            object : RecyclerActivityAdapter.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )

        recyclerView.adapter = adapter

        fab.setOnClickListener {
            adapter.appendItem()
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


}