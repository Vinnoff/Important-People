package test.dev.importantpeople.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import test.dev.importantpeople.BuildConfig
import test.dev.importantpeople.R

abstract class BaseActivity(@LayoutRes val layoutRes: Int) : AppCompatActivity(), LifecycleOwner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onStart() {
        super.onStart()
        initUI()
        initObserver()
    }

    abstract fun initUI()
    abstract fun initObserver()
    open fun showLoader(isLoading: Boolean) {
        if (BuildConfig.DEBUG) Toast.makeText(this, "Loading : $isLoading", Toast.LENGTH_SHORT).show()
    }

    open fun showError(errorText: String? = null) {
        if (BuildConfig.DEBUG) Toast.makeText(this, errorText ?: "Error", Toast.LENGTH_SHORT).show()
    }

    fun commitFragment(fragment: Fragment, @IdRes fragmentId: Int, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            add(fragmentId, fragment)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            if (addToBackStack) addToBackStack(fragment::class.java.simpleName)
            commit()
        }
    }
}

fun BaseActivity.sendEmail(receiver: String) {
    val uri = "mailto:$receiver".toUri()
    startActivity(Intent(Intent.ACTION_SENDTO, uri))
}

fun BaseActivity.callNumber(number: String) {
    val uri = "tel:$number".toUri()
    startActivity(Intent(Intent.ACTION_DIAL, uri))
}