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
        sortUpButton.setOnClickListener {
            viewModelSortedAndFilteredHabits?.changeSortDirection(SortedAndFilteredHabitsListViewModel.SortDirection.Forward)
        }
        sortDownButton.setOnClickListener {
            viewModelSortedAndFilteredHabits?.changeSortDirection(SortedAndFilteredHabitsListViewModel.SortDirection.Backward)
        }

        filterTextView.doAfterTextChanged { text ->
            viewModelSortedAndFilteredHabits?.changeFilter(text.toString())
        }
    }
}