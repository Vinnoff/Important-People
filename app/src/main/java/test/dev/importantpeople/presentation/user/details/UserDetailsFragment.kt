package test.dev.importantpeople.presentation.user.details

import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.user_details_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import test.dev.importantpeople.R
import test.dev.importantpeople.common.utils.load
import test.dev.importantpeople.domain.entity.user.ContactsEntity
import test.dev.importantpeople.domain.entity.user.LocationEntity
import test.dev.importantpeople.presentation.BaseFragment
import test.dev.importantpeople.presentation.user.UserViewModel

class UserDetailsFragment : BaseFragment(R.layout.user_details_fragment) {
    private val userViewModel: UserViewModel by sharedViewModel()
    override fun initUI() {
        user_details_phone.setOnClickListener {
            userViewModel.onClickPhone(user_details_phone.text.toString())
        }
        user_details_cell.setOnClickListener {
            userViewModel.onClickPhone(user_details_cell.text.toString())
        }
        user_details_email.setOnClickListener {
            userViewModel.onClickEmail(user_details_email.text.toString())
        }
    }

    override fun initObserver() {
        userViewModel.liveDataUserInfo.observe(this) { entity ->
            user_details_avatar.load(entity.pictures?.normal)
            user_details_name.text = "${if (entity.title != null) entity.title + " " else ""}${entity.firstname} ${entity.lastname}"
            handleContactInfo(entity.contacts)
            handleLocationInfo(entity.username, entity.location)
        }
    }

    private fun handleContactInfo(contacts: ContactsEntity?) {
        user_details_contact_card.isVisible = contacts != null
        if (contacts != null) {
            user_details_phone.apply {
                isVisible = contacts.phone != null
                text = contacts.phone
            }
            user_details_cell.apply {
                isVisible = contacts.cell != null
                text = contacts.cell
            }
            user_details_email.apply {
                isVisible = contacts.email != null
                text = contacts.email
            }
        }
    }


    private fun handleLocationInfo(username: String, location: LocationEntity?) {
        user_details_location.isVisible = location != null
        if (location != null) {
            location.coordinates?.let { coordinates ->
                user_details_navigation.setOnClickListener {
                    userViewModel.onClickNavigation(username, coordinates.latitude, coordinates.longitude)
                }
            }
            user_details_street.text = location.street
            user_details_city.text = "${location.city} (${location.postalCode})"
            user_details_country.text = "${location.state} - ${location.country}"
        }
    }
}