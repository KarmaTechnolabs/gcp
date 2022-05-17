package com.app.gcp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.gcp.R
import com.app.gcp.adapter.ExpandableListViewAdapter
import com.app.gcp.base.BaseFragment
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.FragmentFaqsBinding
import com.app.gcp.viewmodel.DashBoardViewModel


class FAQsFragment : BaseFragment() {

    private lateinit var fAQsViewModel: DashBoardViewModel
    private var _binding: FragmentFaqsBinding? = null

    private var expandableListViewAdapter: ExpandableListViewAdapter? = null

    private val listDataGroup = mutableListOf<String>()
    private var listDataChild: HashMap<String, List<String>>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fAQsViewModel =
            ViewModelProvider(this)[DashBoardViewModel::class.java]

        _binding = FragmentFaqsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner


        // initializing the listeners
        initListeners()

        // initializing the objects
        initObjects()

        // preparing list data
        initListData()


    }

    /**
     * method to initialize the listeners
     */
    private fun initListeners() {

        // ExpandableListView on child click listener
        binding.expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
           showToast(listDataGroup!![groupPosition] + " : "
                   + listDataChild!![listDataGroup!![groupPosition]]!![childPosition])
            false
        }

        // ExpandableListView Group expanded listener
        binding.expandableListView.setOnGroupExpandListener { groupPosition ->
           showToast(listDataGroup!![groupPosition] + " " + getString(R.string.text_collapsed))
        }

        // ExpandableListView Group collapsed listener
        binding.expandableListView.setOnGroupCollapseListener { groupPosition ->
           showToast(listDataGroup!![groupPosition] + " " + getString(R.string.text_collapsed))
        }
    }

    /**
     * method to initialize the objects
     */
    private fun initObjects() {



        // initializing the list of child
        listDataChild = HashMap()

        // initializing the adapter object
        expandableListViewAdapter = activity?.let {
            ExpandableListViewAdapter(
                listDataGroup, listDataChild!!
            )
        }

        // setting list adapter
        binding.expandableListView.setAdapter(expandableListViewAdapter)
    }

    /*
     * Preparing the list data
     *
     * Dummy Items
     */
    private fun initListData() {

        // Adding group data
        listDataGroup.add(resources.getString(R.string.text_alcohol))
        listDataGroup.add(resources.getString(R.string.text_coffee))
        listDataGroup.add(resources.getString(R.string.text_pasta))
        listDataGroup.add(resources.getString(R.string.text_cold_drinks))

        // array of strings

        // list of alcohol
        val alcoholList: MutableList<String> = ArrayList()
        var array: Array<String> = resources.getStringArray(R.array.string_array_alcohol)
        for (item in array) {
            alcoholList.add(item)
        }

        // list of coffee
        val coffeeList: MutableList<String> = ArrayList()
        array = resources.getStringArray(R.array.string_array_coffee)
        for (item in array) {
            coffeeList.add(item)
        }

        // list of pasta
        val pastaList: MutableList<String> = ArrayList()
        array = resources.getStringArray(R.array.string_array_pasta)
        for (item in array) {
            pastaList.add(item)
        }

        // list of cold drinks
        val coldDrinkList: MutableList<String> = ArrayList()
        array = resources.getStringArray(R.array.string_array_cold_drinks)
        for (item in array) {
            coldDrinkList.add(item)
        }

        // Adding child data
        listDataChild!![listDataGroup!![0]] = alcoholList
        listDataChild!![listDataGroup!![1]] = coffeeList
        listDataChild!![listDataGroup!![2]] = pastaList
        listDataChild!![listDataGroup!![3]] = coldDrinkList

        // notify the adapter
        expandableListViewAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}