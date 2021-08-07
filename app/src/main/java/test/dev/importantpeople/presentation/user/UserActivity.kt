package test.dev.importantpeople.presentation.user

import android.content.res.Configuration
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dev.importantpeople.R
import test.dev.importantpeople.presentation.BaseActivity
import test.dev.importantpeople.presentation.callNumber
import test.dev.importantpeople.presentation.displayMap
import test.dev.importantpeople.presentation.sendEmail
import test.dev.importantpeople.presentation.user.details.UserDetailsFragment
import test.dev.importantpeople.presentation.user.list.UserListFragment

class UserActivity : BaseActivity(R.layout.user_activity) {
    private val userViewModel: UserViewModel by viewModel()
    private val listFragment: UserListFragment by lazy { UserListFragment() }
    private val detailsFragment: UserDetailsFragment by lazy { UserDetailsFragment() }

    override fun initUI() = Unit

    override fun initObserver() {
        userViewModel.liveDataNavigation.observe(this) { event ->
            event.getContentIfNotHandled()?.let { nav ->
                when (nav) {
                    is UserNavigation.LIST -> supportFragmentManager.beginTransaction()
                        .replace(R.id.user_list_fragment, listFragment)
                        .commit()
                    is UserNavigation.DETAILS -> supportFragmentManager.beginTransaction()
                        .replace(R.id.user_details_fragment, detailsFragment).apply {
                            if (supportFragmentManager.findFragmentById(R.id.user_details_fragment) == null)
                                addToBackStack(detailsFragment::class.java.toString())
                        }.commit()
                    is UserNavigation.EMAIL -> sendEmail(receiver = nav.email)
                    is UserNavigation.PHONE -> callNumber(number = nav.phone)
                    is UserNavigation.NAVIGATION -> displayMap(street = nav.street, city = nav.city, country = nav.country)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}