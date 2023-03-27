package com.advancedfinance.core.platform


abstract class BaseAdapter


//TODO: Usar Paging3 e selectionTracker

/*
abstract class BaseAdapter<DATA, VIEW_HOLDER : BaseViewHolder<DATA>>(val viewModel: BaseViewModel? = null) :
    RecyclerView.Adapter<VIEW_HOLDER>()*/
/*,
    OnRecyclerDataBindingAdapter<DATA>*//*
 {

    val TAG = javaClass.simpleName

    private val itemsList = ArrayList<DATA>()

    var items: List<DATA>
        get() = itemsList
        set(value) {
            setDate(value)
        }

    fun remove(position: Int) {
        if (itemsList.isNotEmpty()) {
            itemsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        itemsList.clear()
        notifyDataSetChanged()
    }

    override fun setDate(data: DATA, position: Int) {
        if (data is Collections) return

        itemsList.add(position, data)
        notifyItemInserted(position)
    }

    override fun setDate(data: DATA) {
        if (data != null) {
            if (data is Collection<*>) {
                itemsList.clear()
                itemsList.addAll(data as Collection<DATA>)
                notifyDataSetChanged()
            } else {
                itemsList.add(data)
                notifyItemInserted(itemsList.size - 1)
            }
        }
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIEW_HOLDER
    override fun onBindViewHolder(viewHolder: VIEW_HOLDER, position: Int) {
        viewHolder.bind(getItem(position))
    }

    override fun getItemCount(): Int = items.size
    private fun getItem(position: Int): DATA = itemsList[position]
}

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: T)
}*/
