package com.example.saitowapp.ui_classes

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.saitowapp.R
import com.example.saitowapp.viewModel.MainActivityViewModel
import java.util.*

class Custom_Dialog : AppCompatDialogFragment() ,View.OnClickListener {
    private lateinit var filterListener: View.OnClickListener
    private lateinit var btn_cancel: Button
    private lateinit var btn_filter: Button
    private lateinit var btn_startDate: Button
    private lateinit var btn_endDate: Button
    private lateinit var edtTxt_startDate: EditText
    private lateinit var edtTxt_endDate: EditText
    private val viewModel by activityViewModels<MainActivityViewModel>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.custom_dialog, null)
        builder.setView(view)

        btn_cancel = view.findViewById<Button>(R.id.btn_cancel)
        btn_cancel.setOnClickListener(this)
        btn_filter = view.findViewById<Button>(R.id.btn_filter)
        btn_filter.setOnClickListener(filterListener as View.OnClickListener?)
        btn_startDate = view.findViewById<Button>(R.id.btn_date)
        btn_endDate = view.findViewById<Button>(R.id.btn_enddate)
        btn_startDate.setOnClickListener(this)
        btn_endDate.setOnClickListener(this)
        edtTxt_startDate = view.findViewById<EditText>(R.id.edt_startDate)
        edtTxt_endDate = view.findViewById<EditText>(R.id.edt_endDate)
        return builder.create()
    }

    fun showDialogWithAction(okListener: View.OnClickListener?,fragManager:FragmentManager) {
       filterListener = okListener!!
        this.show(fragManager, "custom dialog")
    }



    override fun onClick(v: View?) {
        when (v) {
            btn_cancel->{
                dismiss()
            }
            btn_startDate -> {
                getDate_TimePicker(edtTxt_startDate,true)
            }
            btn_endDate -> {
                getDate_TimePicker(edtTxt_endDate,false)
            }

        }
    }

    private fun getDate_TimePicker( edtTxt : EditText,isStartTime:Boolean){
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
                                startTime,isStartTime
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
    private fun getDateTimeString(day: String, month: Int, year: Int, startTime: String,isStartTime: Boolean): String {
        var stringTime = ( year.toString() + "-" + month.toString() + "-" + day + " " + startTime)
        if(isStartTime)
            viewModel.filterStartTime = stringTime
        else
            viewModel.filterEndTime = stringTime
        return stringTime
    }
}