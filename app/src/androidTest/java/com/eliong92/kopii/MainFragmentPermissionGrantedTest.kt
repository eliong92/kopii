package com.eliong92.kopii

import android.Manifest
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.eliong92.kopii.viewModel.MainViewModel
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentPermissionGrantedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity> = ActivityScenarioRule(TestActivity::class.java)

    @get:Rule
    var permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var viewModel: MainViewModel

    private lateinit var fragment: MainFragment

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)
        fragment = MainFragment.newInstance()
        fragment.viewModelProvider = mockk(relaxed = true)
        mockkConstructor(ViewModelProvider::class)
        every {
            anyConstructed<ViewModelProvider>().get(MainViewModel::class.java)
        } returns viewModel
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun pageLoaded_whenHaveLocationPermission_shouldFetchData() {
        executeFragment()
        every {
            viewModel.getState()
        } returns MutableLiveData()

        verify(exactly = 1) {
            viewModel.showVenues()
        }
    }

    private fun executeFragment() {
        activityRule.scenario.onActivity {
            it.setFragment(fragment)
        }
    }
}