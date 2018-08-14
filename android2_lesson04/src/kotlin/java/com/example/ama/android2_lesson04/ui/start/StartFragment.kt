package com.example.ama.android2_lesson04.ui.start

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ui.MainActivity
import com.example.ama.android2_lesson04.ui.viewer.PVFragmentIntentService
import com.example.ama.android2_lesson04.ui.viewer.PVFragmentStartedService
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_start_service.setOnClickListener { changeFragment(PVFragmentStartedService.newInstance()) }
        btn_intent_service.setOnClickListener { changeFragment(PVFragmentIntentService.newInstance()) }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun changeFragment(fragment: Fragment) {
        (activity as MainActivity).runFragment(fragment, true)
    }
}