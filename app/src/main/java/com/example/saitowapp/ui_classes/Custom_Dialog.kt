package com.example.saitowapp.ui_classes

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.saitowapp.databinding.CustomDialogBinding
import com.example.saitowapp.viewModel.MainActivityViewModel
import java.util.*

class Custom_Dialog : AppCompatDialogFragment(), View.OnClickListener {

    private lateinit var binding: CustomDialogBinding
    private lateinit var filterListener: View.OnClickListener
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        binding = CustomDialogBinding.inflate(layoutInflater)
        val view = binding.root
        builder.setView(view)

        binding.btnCancel.setOnClickListener(this)
        binding.btnFilter.setOnClickListener(filterListener as View.OnClickListener?)
        binding.btnDate.setOnClickListener(this)
        binding.btnEnddate.setOnClickListener(this)
        return builder.create()
    }

    fun showDialogWithAction(okListener: View.OnClickListener?, fragManager: FragmentManager) {
        if (okListener != null) {
            filterListener = okListener
        }
        this.show(fragManager, "custom dialog")
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.btnCancel -> {
                dismiss()
            }
            binding.btnDate -> {
                getDate_TimePicker(binding.edtStartDate, true)
            }
            binding.btnEnddate -> {
                getDate_TimePicker(binding.edtEndDate, false)
            }

        }
    }

    private fun getDate_TimePicker(edtTxt: EditText, isStartTime: Boolean) {
        var startTime = ""
        val c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var mHour = c[Calendar.HOUR_OF_DAY]
                var mMinute = c[Calendar.MINUTE]
                var seconds = c[Calendar.SECOND]
                val timePickerDialog = TimePickerDialog(
                    context,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        startTime = "$hourOfDay:$minute:00"
                        edtTxt.setText(
                            getDateTimeString(
                                dayOfMonth.toString(),
                                (monthOfYear + 1),
                                year,
                                startTime, isStartTime
                            )
                        )
                    }, mHour,
                    mMinute,
                    true
                )
                timePickerDialog.show()


            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun getDateTimeString(
        day: String,
        month: Int,
        year: Int,
        startTime: String,
        isStartTime: Boolean
    ): String {
        var stringTime = ("$year-$month-$day $startTime")
        if (isStartTime)
            viewModel.filterStartTime = stringTime
        else
            viewModel.filterEndTime = stringTime
        return stringTime
    }
}