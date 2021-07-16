package com.practicaltest.pageinitiation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicaltest.pageinitiation.databinding.ListItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.progress_loading.view.*

class DataAdapter(private var itemsCells: ArrayList<DemoData?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var mcontext: Context

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<DemoData?>) {
        this.itemsCells.clear()
        this.itemsCells.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): String {
        return itemsCells.get(position)?.charId.toString()
    }
    fun setItems(items: ArrayList<DemoData?>) {
        this.itemsCells.clear()
        this.itemsCells.addAll(items)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            itemsCells.add(null)
            notifyItemInserted(itemsCells.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (itemsCells.size != 0) {
            itemsCells.removeAt(itemsCells.size - 1)
            notifyItemRemoved(itemsCells.size)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val binding: ListItemBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserListViewHolder(binding, mcontext)
        } else {
            val view =
                LayoutInflater.from(mcontext).inflate(R.layout.progress_loading, parent, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.progressbar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.GREEN, BlendMode.SRC_ATOP)
            } else {
                view.progressbar.indeterminateDrawable.setColorFilter(
                    Color.GREEN,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsCells.get(position) == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            itemsCells.get(position)?.let { (holder as UserListViewHolder).bind(it) }
        }
    }

    class UserListViewHolder(
        private val itemBinding: ListItemBinding,
        private val mContext: Context
    ) : RecyclerView.ViewHolder(itemBinding.root){
        private lateinit var demoData: DemoData
        @SuppressLint("SetTextI18n", "CheckResult", "UseCompatLoadingForDrawables")
        fun bind(item: DemoData) {
            this.demoData = item
            itemBinding.tvName.text = item.name
            itemBinding.tvDate.text = item.birthday
            Picasso.get().load(item.img).into(itemBinding.ivProfile);

        }


    }
}
/*class DataAdapter(private var mcontext:Context,private var demoData: ArrayList<DemoData?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val binding: ListItemBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserListViewHolder(binding, mcontext)
        } else {
            val view =
                LayoutInflater.from(mcontext).inflate(R.layout.progress_loading, parent, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.progressbar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.GREEN, BlendMode.SRC_ATOP)
            } else {
                view.progressbar.indeterminateDrawable.setColorFilter(
                    Color.GREEN,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            LoadingViewHolder(view)
        }
    }
    fun setItems(dataViews: ArrayList<DemoData?>) {
        this.demoData.clear()
        this.demoData.addAll(dataViews)
        notifyDataSetChanged()
    }
    fun addData(dataViews: ArrayList<DemoData?>) {
        this.demoData.clear()
        this.demoData.addAll(dataViews)
        notifyDataSetChanged()
    }
    fun addLoadingView() {
        //add loading item
        Handler().post {
            demoData.add(null)
            notifyItemInserted(demoData.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (demoData.size != 0) {
            demoData.removeAt(demoData.size - 1)
            notifyItemRemoved(demoData.size)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            demoData.get(position)?.let { (holder as UserListViewHolder).bind(it) }
        }
    }

    override fun getItemCount(): Int {
        return demoData.size
    }
    override fun getItemViewType(position: Int): Int {
        return if (demoData.get(position) == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }
    class UserListViewHolder(
        private val itemBinding: ListItemBinding,
        private val mContext: Context
    ) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: DemoData) {
            itemBinding.tvName.text = item.name
            itemBinding.tvDate.text = item.birthday
            Picasso.get().load(item.img).into(itemBinding.ivProfile);
        }
    }
}*/