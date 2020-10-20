package com.hedgehog.gdzietabiedra.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.hedgehog.gdzietabiedra.R
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel

class ListFragment : Fragment() {

  private val listViewModel: ListViewModel by viewModel(this, ListViewModel::class.java)

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.fragment_list, container, false)
    val textView: TextView = root.findViewById(R.id.text_list)
    listViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

    listViewModel.loadData()
    return root
  }
}