package com.example.habittracker.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.habittracker.Adapters.HabitAdapter
import com.example.domain.Models.Habit
import com.example.habittracker.R
import kotlinx.android.synthetic.main.habits_resycler_list_fragment.*
import java.lang.Exception

class ListHabitFragment() : Fragment(), HabitAdapter.OnItemClickListener {

    companion object {
        private const val HABITS_KEY = "Good habits key"
        private val LOG_KEY = ListHabitFragment::class.java.simpleName

        fun newInstance(goodHabits: ArrayList<Habit>): ListHabitFragment {
            val fragment = ListHabitFragment()
            val bundle = Bundle().apply { putSerializable(HABITS_KEY, goodHabits) }
            fragment.arguments = bundle
            return fragment
        }
    }

    private var habitAdapter: HabitAdapter? = null
    private lateinit var habits: ArrayList<Habit>
    private var callBack: IHabitItemClick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.habits_resycler_list_fragment, container, false)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        habits = if (savedInstanceState == null) {
            if (arguments == null || !requireArguments().containsKey(HABITS_KEY))
                throw IllegalArgumentException("Фрагмент был инициализирован не через метод newInstance!")
            requireArguments().getSerializable(HABITS_KEY) as ArrayList<Habit>
        } else {
            savedInstanceState.getSerializable(HABITS_KEY) as ArrayList<Habit>
        }
        habitsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                habitsRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        habitAdapter = HabitAdapter(habits)
        habitsRecyclerView.adapter = habitAdapter
        habitAdapter?.onItemClickListener = this
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null)
            onAttachToParentFragment(requireParentFragment())
    }

    private fun onAttachToParentFragment(parentFragment: Fragment) {
        try {
            callBack = parentFragment as IHabitItemClick
        } catch (e: Exception) {
            throw IllegalArgumentException("Activity ${parentFragment::class.simpleName} не реализует интерфейс интерфейс ${IHabitItemClick::class.qualifiedName}")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callBack = null
    }

    fun updateHabits(habits: List<Habit>) = habitAdapter?.updateItems(habits)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(HABITS_KEY, habits)
    }

    override fun onItemClick(habit: Habit) = callBack?.onHabitItemClick(habit) ?: Unit

    interface IHabitItemClick {
        fun onHabitItemClick(habit: Habit)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_KEY, "On destroy")
    }
}