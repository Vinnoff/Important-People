package test.dev.importantpeople.presentation.user.list

import kotlinx.android.synthetic.main.user_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import test.dev.importantpeople.R
import test.dev.importantpeople.presentation.BaseFragment
import test.dev.importantpeople.presentation.user.UserViewModel

class UserListFragment : BaseFragment(R.layout.user_list_fragment) {
    private val userViewModel: UserViewModel by sharedViewModel()

    override fun initUI() {

    }

    override fun initObserver() {
        userViewModel.liveDataUserInfo.observe(this, {
            user_list_temp_text.text = it.toString()
        })
    }
}