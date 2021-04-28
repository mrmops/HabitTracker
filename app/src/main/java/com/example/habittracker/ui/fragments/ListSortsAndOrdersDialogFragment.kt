package com.example.habittracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.viewModels.SortedAndFilteredHabitsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_item_list_dialog_list_dialog.*

class ListSortsAndOrdersDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val FILTER_KEY = "Filter Key!"
        private const val SORT_DIRECTION_KEY = "Sort direction Key!"
    }

    private var sortDirection: SortedAndFilteredHabitsListViewModel.SortDirection =
        SortedAndFilteredHabitsListViewModel.SortDirection.Forward

    private var viewModelSortedAndFilteredHabits: SortedAndFilteredHabitsListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelSortedAndFilteredHabits = ViewModelProvider(requireActivity())
            .get(SortedAndFilteredHabitsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setFiltersAndSorts(savedInstanceState)
        sortUpButton.setOnClickListener {
            viewModelSortedAndFilteredHabits?.changeSortDirection(
                SortedAndFilteredHabitsListViewModel.SortDirection.Forward
            )
        }
        sortDownButton.setOnClickListener {
            viewModelSortedAndFilteredHabits?.changeSortDirection(
                SortedAndFilteredHabitsListViewModel.SortDirection.Backward
            )
        }

        filterTextView.doAfterTextChanged { text ->
            viewModelSortedAndFilteredHabits?.changeFilter(text.toString())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setFiltersAndSorts(savedInstanceState)
    }

    private fun setFiltersAndSorts(savedInstanceState: Bundle?){
        if (savedInstanceState != null) {
            val filter = savedInstanceState.getString(FILTER_KEY)
            viewModelSortedAndFilteredHabits?.changeFilter(filter.toString())
            filterTextView.setText(filter)

            sortDirection =
                savedInstanceState.getSerializable(SORT_DIRECTION_KEY) as SortedAndFilteredHabitsListViewModel.SortDirection
            viewModelSortedAndFilteredHabits?.changeSortDirection(sortDirection)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FILTER_KEY, filterTextView?.text.toString())
        outState.putSerializable(SORT_DIRECTION_KEY, sortDirection)
    }
}