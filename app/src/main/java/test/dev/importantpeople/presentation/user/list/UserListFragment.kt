package test.dev.importantpeople.presentation.user.list

import kotlinx.android.synthetic.main.user_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import test.dev.importantpeople.R
import test.dev.importantpeople.common.utils.toPx
import test.dev.importantpeople.presentation.BaseFragment
import test.dev.importantpeople.presentation.SpaceItemDecoration
import test.dev.importantpeople.presentation.user.UserViewModel

class UserListFragment : BaseFragment(R.layout.user_list_fragment) {
    private val userViewModel: UserViewModel by sharedViewModel()
    private val userAdapter by lazy { UserAdapter { uuid -> userViewModel.onUserSelected(uuid) } }

    override fun initUI() {
        user_list.adapter = userAdapter
        user_list.addItemDecoration(SpaceItemDecoration(spacing = 20.toPx(requireContext()), includeEdge = true))

    }

    override fun initObserver() {
        userViewModel.liveDataUserList.observe(this) {
            userAdapter.data = it
        }
    }
}