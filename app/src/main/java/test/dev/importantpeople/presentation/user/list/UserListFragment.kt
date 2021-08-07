package test.dev.importantpeople.presentation.user.list

import kotlinx.android.synthetic.main.user_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import test.dev.importantpeople.R
import test.dev.importantpeople.common.FIRST_PAGE
import test.dev.importantpeople.common.utils.toPx
import test.dev.importantpeople.domain.entity.user.UserData
import test.dev.importantpeople.presentation.BaseFragment
import test.dev.importantpeople.presentation.SpaceItemDecoration
import test.dev.importantpeople.presentation.user.UserViewModel

class UserListFragment : BaseFragment(R.layout.user_list_fragment) {
    private val userViewModel: UserViewModel by sharedViewModel()
    private val userAdapter by lazy { UserAdapter { uuid -> userViewModel.onUserSelected(uuid) } }

    override fun initUI() {
        user_list_rv.adapter = userAdapter
        user_list_rv.addItemDecoration(SpaceItemDecoration(spacing = 20.toPx(requireContext()), includeEdge = true))
        user_list_previous.setOnClickListener { userViewModel.onClickPrevious() }
        user_list_next.setOnClickListener { userViewModel.onClickNext() }
    }

    override fun initObserver() {
        userViewModel.liveDataUserList.observe(this) { viewState ->
            showLoader(viewState is UserViewState.LOADER)
            when (viewState) {
                is UserViewState.EMPTY_DATA -> showError("Empty data")
                is UserViewState.ERROR -> showError()
                is UserViewState.SUCCESS -> handleData(viewState.pagination, viewState.data)
            }
        }
    }

    private fun handleData(pagination: Int, data: List<UserData>) {
        user_list_previous.isEnabled = pagination > FIRST_PAGE
        userAdapter.data = data
    }

    override fun showLoader(isLoading: Boolean) {
        user_list_previous.isEnabled = !isLoading
        user_list_next.isEnabled = !isLoading
    }
}