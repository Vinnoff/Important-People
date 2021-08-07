package test.dev.importantpeople.presentation.user.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item.view.*
import test.dev.importantpeople.R
import test.dev.importantpeople.common.utils.load
import test.dev.importantpeople.domain.entity.user.UserData

class UserAdapter(val onClick: (id: String) -> Unit) : ListAdapter<UserData, UserAdapter.ViewHolder>(ItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].let { data ->
            holder.bind(data)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(content: UserData) = with(itemView) {
            user_item_avatar.load(content.pictures?.thumbnail)
            user_item_text.text = "${content.firstname}\n${content.lastname}"
            user_item_card.setOnClickListener { onClick.invoke(content.uuid) }
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData) = oldItem.uuid == newItem.uuid
        override fun areContentsTheSame(oldItem: UserData, newItem: UserData) = oldItem == newItem
    }
}

