package com.eliong92.kopii

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eliong92.kopii.adapter.VenueAdapter
import com.eliong92.kopii.network.IScheduleProvider
import com.eliong92.kopii.viewModel.IMainViewModel
import com.eliong92.kopii.viewModel.MainViewModel
import com.eliong92.kopii.viewModel.MainViewModelProvider
import com.eliong92.kopii.viewModel.MainViewState
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    companion object {

        const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val RC_LOCATION_PERM = 123

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    @Inject
    lateinit var viewModelProvider: MainViewModelProvider

    @Inject
    lateinit var scheduler: IScheduleProvider

    @VisibleForTesting
    lateinit var viewModel: IMainViewModel

    private val disposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        try {
            AndroidSupportInjection.inject(this)
        } catch (e: IllegalArgumentException) {
            // do nothing
        }

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        disposable.clear()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider).get(MainViewModel::class.java)
        viewModel.getState().observe(viewLifecycleOwner, { state ->
            when(state) {
                is MainViewState.OnLoading -> {
                    Toast.makeText(activity, "Loading", Toast.LENGTH_SHORT).show()
                }

                is MainViewState.OnSuccess -> {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = VenueAdapter(state.items)
                    }
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                }

                is MainViewState.OnError -> {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        checkLocationPermission()
        observeSearchBox()
    }

    private fun observeSearchBox() {
        Observable.create<String> {

            searchBox.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    it.onNext(s.toString())
                }
            })
        }
            .debounce(500, TimeUnit.MILLISECONDS, scheduler.io())
            .filter { it.length > 2 }
            .observeOn(scheduler.mainThread())
            .subscribe {
                viewModel.showVenues(it)
            }
            .let {
                disposable.add(it)
            }
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    fun checkLocationPermission() {
        if(!EasyPermissions.hasPermissions(activity as Context, LOCATION)) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_ask),
                RC_LOCATION_PERM,
                LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(activity, getString(R.string.rationale_ask), Toast.LENGTH_LONG).show()
    }
}