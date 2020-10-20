package com.eliong92.kopii

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eliong92.kopii.viewModel.MainViewModel
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentPermissionNotGrantedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity> = ActivityScenarioRule(TestActivity::class.java)

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
    fun pageLoaded_whenNotHaveLocationPermission_shouldNotFetchData() {
        executeFragment()
        every {
            viewModel.getState()
        } returns MutableLiveData()

        verify(inverse = true) {
            viewModel.showVenues(any())
        }
    }

    private fun executeFragment() {
        activityRule.scenario.onActivity {
            it.setFragment(fragment)
        }
    }
}