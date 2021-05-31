package com.rsschool.android2021

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlin.random.Random

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var minValue: EditText
    private lateinit var maxValue: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"


        generateButton?.setOnClickListener {
            val min = minValue.text.toString()
            val max = maxValue.text.toString()
            if(max.isBlank() || min.isBlank()) {
                Toast.makeText(requireContext(), "Value can't be empty", Toast.LENGTH_SHORT).show()
                generateIfUserMistakes()
            }
            else if(max == min){
                Toast.makeText(requireContext(), "The values are equal", Toast.LENGTH_SHORT).show()
                generateIfUserMistakes()
            }
            else if(Integer.parseInt(min) > Integer.parseInt(max)) {
                Toast.makeText(requireContext(), "Min > Max", Toast.LENGTH_SHORT).show()
                generateIfUserMistakes()
            }
            else{
                val secondFragment: Fragment = SecondFragment.newInstance(Integer.parseInt(min), Integer.parseInt(max))
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.container, secondFragment)
                transaction.commit()
            }
            // TODO: send min and max to the SecondFragment
        }
    }

    private fun generateIfUserMistakes(){
        val minInt: Int = Random.nextInt(0, 33)
        val maxInt: Int = Random.nextInt(minInt, 100)
        minValue.text = minInt.toString().toEditable()
        maxValue.text = maxInt.toString().toEditable()

    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}