package test.dev.importantpeople.presentation.user

import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dev.importantpeople.R
import test.dev.importantpeople.presentation.BaseActivity
import test.dev.importantpeople.presentation.user.list.UserListFragment

class UserActivity : BaseActivity(R.layout.user_activity) {
    private val userViewModel: UserViewModel by viewModel()

    override fun initUI() = Unit

    override fun initObserver() {
        userViewModel.liveDataNavigation.observe(this, { event ->
            event.getContentIfNotHandled()?.let { nav ->
                when (nav) {
                    UserNavigation.LIST -> commitFragment(UserListFragment(), R.id.user_fragment_container, addToBackStack = false)
                    UserNavigation.DETAILS -> Unit
                }
            }
        })
    }
}