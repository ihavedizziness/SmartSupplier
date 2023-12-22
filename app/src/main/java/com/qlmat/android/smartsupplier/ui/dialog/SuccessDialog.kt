package com.qlmat.android.smartsupplier.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.databinding.DialogSuccessBinding
import com.qlmat.android.smartsupplier.ui.home.HomeFragment

class SuccessDialog : DialogFragment(R.layout.dialog_success) {

    private val viewBinding: DialogSuccessBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActions()
    }

    private fun initActions() = with(viewBinding) {
        buttonDialogSuccess.setOnClickListener {
            dismiss()
            requireActivity().supportFragmentManager.commit {
                replace(R.id.containerView, HomeFragment())
            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction()
            .add(this, tag)
            .commitAllowingStateLoss()
    }

}