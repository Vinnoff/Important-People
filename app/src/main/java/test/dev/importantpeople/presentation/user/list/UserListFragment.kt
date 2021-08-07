package test.dev.importantpeople.presentation.user.list

import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import test.dev.importantpeople.R
import test.dev.importantpeople.common.utils.toPx
import test.dev.importantpeople.domain.entity.user.UserData
import test.dev.importantpeople.presentation.BaseFragment
import test.dev.importantpeople.presentation.SpaceItemDecoration
import test.dev.importantpeople.presentation.user.UserViewModel
import timber.log.Timber


class UserListFragment : BaseFragment(R.layout.user_list_fragment) {
    private val userViewModel: UserViewModel by sharedViewModel()
    private val userAdapter by lazy { UserAdapter { uuid -> userViewModel.onUserSelected(uuid) } }

    override fun initUI() {
        user_list_rv.adapter = userAdapter
        user_list_rv.addItemDecoration(SpaceItemDecoration(spacing = 20.toPx(requireContext()), includeEdge = true))
        user_list_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisiblesItems = (recyclerView.layoutManager!! as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: (recyclerView.layoutManager!! as GridLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        Timber.d("Reach End of Scrollview")
                        userViewModel.onListEnded()
                    }
                }
            }
        })
        Glide
            .with(requireContext())
            .load(R.raw.spin_me_round)
            .into(user_list_loader)
    }

    override fun initObserver() {
        userViewModel.liveDataUserList.observe(this) { viewState ->
            showLoader(viewState is UserViewState.LOADER)
            when (viewState) {
                is UserViewState.EMPTY_DATA -> showError("Empty data")
                is UserViewState.ERROR -> showError()
                is UserViewState.SUCCESS -> handleData(viewState.data)
                is UserViewState.EMPTY_OLD_DATA -> {
                    showError("Empty data")
                    handleData(viewState.data)
                }
                is UserViewState.ERROR_OLD_DATA -> {
                    showError()
                    handleData(viewState.data)
                }
            }
        }
    }

    private fun handleData(data: List<UserData>) {
        user_list_rv.isVisible = true
        userAdapter.submitList(data)
    }

    override fun showLoader(isLoading: Boolean) {
        user_list_loader.isVisible = isLoading
    }
}