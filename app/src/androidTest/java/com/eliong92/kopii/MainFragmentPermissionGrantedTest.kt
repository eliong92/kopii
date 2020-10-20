package com.eliong92.kopii

import android.Manifest
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.eliong92.kopii.network.IScheduleProvider
import com.eliong92.kopii.viewModel.MainViewModel
import io.mockk.*
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainFragmentPermissionGrantedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity> = ActivityScenarioRule(TestActivity::class.java)

    @get:Rule
    var permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var viewModel: MainViewModel

    private lateinit var fragment: MainFragment

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)
        testScheduler = TestScheduler()
        val testSchedulerProvider: IScheduleProvider = mockk(relaxed = true)
        every { testSchedulerProvider.io() } returns testScheduler
        every { testSchedulerProvider.mainThread() } returns testScheduler
        fragment = MainFragment.newInstance()
        fragment.viewModelProvider = mockk(relaxed = true)
        fragment.scheduler = testSchedulerProvider
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

        verify(inverse = true) {
            viewModel.showVenues(any())
        }
    }

    @Test
    fun performSearch_shouldSearchForVenue() {
        val query = "kopii"
        executeFragment()
        every {
            viewModel.getState()
        } returns MutableLiveData()

        onView(withId(R.id.searchBox)).perform(typeText(query))

        testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS)
        verify(exactly = 0) {
            viewModel.showVenues(query)
        }
        testScheduler.advanceTimeBy(301, TimeUnit.MILLISECONDS)
        verify(exactly = 1) {
            viewModel.showVenues(query)
        }
    }

    @Test
    fun performSearch_whenSearchQueryLengthBelow3Char_shouldNotPerformSearch() {
        val query = "ko"
        executeFragment()
        every {
            viewModel.getState()
        } returns MutableLiveData()

        onView(withId(R.id.searchBox)).perform(typeText(query))

        testScheduler.advanceTimeBy(501, TimeUnit.MILLISECONDS)
        verify(exactly = 0) {
            viewModel.showVenues(any())
        }
    }

    private fun executeFragment() {
        activityRule.scenario.onActivity {
            it.setFragment(fragment)
        }
    }
}