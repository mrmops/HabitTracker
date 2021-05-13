package com.example.habittracker.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.Adapters.HabitTypedListsFragmentsAdapter
import com.example.habittracker.DataBase.HabitsDataBase
import com.example.habittracker.Models.Habit
import com.example.habittracker.Networking.Repositories.Implemetations.HabitRepository
import com.example.habittracker.Networking.RetrofitHelper
import com.example.habittracker.Networking.Services.HabitNetworkService
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.viewModels.HabitsListViewModel
import kotlinx.android.synthetic.main.fragment_habits.*


class HabitsListsNavigationFragment : Fragment(), ListHabitFragment.IHabitItemClick {

    companion object {
        private val LOG_KEY = HabitsListsNavigationFragment::class.java.name
    }

    private lateinit var viewModelHabits: HabitsListViewModel;
    private lateinit var habitTypedListsFragmentsAdapter: HabitTypedListsFragmentsAdapter
    private var habitClickCallBack: ClickHabitItemCallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBase = HabitsDataBase.getInstance(requireContext())

        val habitApiService = RetrofitHelper.newInstance().create(HabitNetworkService::class.java)

        viewModelHabits =
            ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return HabitsListViewModel(
                        HabitRepository(
                            habitApiService,
                            dataBase.habitDao()
                        )
                    ) as T
                }
            }).get(HabitsListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_habits, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomFragment = ListSortsAndOrdersDialogFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.containerBottomSheet, bottomFragment)
            .commit()

        habitTypedListsFragmentsAdapter = HabitTypedListsFragmentsAdapter(
            viewModelHabits.listHabits.value,
            requireContext(),
            habitsViewPager,
            childFragmentManager
        )
        habitsViewPager.adapter = habitTypedListsFragmentsAdapter
        habitsPagerTabLayout.setupWithViewPager(habitsViewPager)

        fab.setOnClickListener { habitClickCallBack?.onNewHabitButtonClick() }
        viewModelHabits.listHabits.observe(viewLifecycleOwner,
            { habits -> habitTypedListsFragmentsAdapter.updateHabits(habits) })
    }

    override fun onHabitItemClick(habit: Habit) = habitClickCallBack?.onHabitClick(habit) ?: Unit

    override fun onAttach(context: Context) {
        super.onAttach(context)
        habitClickCallBack = if (context is ClickHabitItemCallBack)
            context
        else
            throw IllegalArgumentException("Активити не реализует интерфейс callBack ${ClickHabitItemCallBack::class.java.name}")
    }

    override fun onDetach() {
        super.onDetach()
        habitClickCallBack = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_KEY, "On destroy")
    }

    interface ClickHabitItemCallBack {

        fun onHabitClick(habit: Habit)

        fun onNewHabitButtonClick()
    }
}