package test.dev.importantpeople.presentation.user.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item.view.*
import test.dev.importantpeople.R
import test.dev.importantpeople.common.utils.load
import test.dev.importantpeople.domain.entity.user.UserEntity

class UserAdapter(val onClick: (id: String) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val _data = arrayListOf<UserEntity>()
    var data: List<UserEntity>
        get() = _data
        set(value) {
            _data.clear()
            _data.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = _data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        _data[position].let { data ->
            holder.bind(data)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(content: UserEntity) = with(itemView) {
            user_item_avatar.load(content.pictures?.thumbnail)
            user_item_text.text = "${content.firstname}\n${content.lastname}"
            user_item_card.setOnClickListener { onClick.invoke(content.uuid) }
        }
    }
}

