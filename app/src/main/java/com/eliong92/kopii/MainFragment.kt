package com.eliong92.kopii

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eliong92.kopii.adapter.VenueAdapter
import com.eliong92.kopii.viewModel.MainViewModel
import com.eliong92.kopii.viewModel.MainViewModelProvider
import com.eliong92.kopii.viewModel.MainViewState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    @Inject
    lateinit var viewModelProvider: MainViewModelProvider

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(this, viewModelProvider).get(MainViewModel::class.java)
        viewModel.showVenues()
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
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
    }
}