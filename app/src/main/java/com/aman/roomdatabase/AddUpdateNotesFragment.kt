package com.aman.roomdatabase

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aman.roomdatabase.databinding.FragmentAddUpdateNotesBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddUpdateNotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddUpdateNotesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAddUpdateNotesBinding
    private lateinit var mainActivity: MainActivity
    private  var notesId: Int = -1
    private  var isUpdate: Boolean = false
    private  var notes: Notes = Notes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            notesId = it.getInt(mainActivity.id, -1)
            isUpdate = true
            getNotes()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddUpdateNotesBinding.inflate(layoutInflater)

        binding.btnSave.setOnClickListener {
            if(binding.etTitle.text.toString().isNullOrEmpty()){
                binding.etTitle.error = resources.getString(R.string.add_title)
                binding.etTitle.requestFocus()
            }else if(binding.etDescription.text.toString().isNullOrEmpty()){
                binding.etDescription.error = resources.getString(R.string.add_description)
                binding.etDescription.requestFocus()
            }else{
                var notes =  Notes()
                notes.title = binding.etTitle.text.toString()
                notes.description = binding.etDescription.text.toString()
                notes.isCompleted = binding.cbIsCompleted.isChecked
                notes.date = mainActivity.dateFormat.format(Calendar.getInstance().time)
                if(isUpdate) {
                    notes.date = this.notes.date
                    notes.id = notesId
                    update(notes)
                }
                else{
                    notes.date = mainActivity.dateFormat.format(Calendar.getInstance().time)

                    save(notes)
                }
            }
        }
        return binding.root
    }

    private fun getNotes() {
        class saveRoom : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {

              notes =  mainActivity.userRoomDatabase.notesDao().findNotesByID(notesId)?:Notes()
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding.etDescription.setText(notes.description)
                binding.etTitle.setText(notes.title)
                binding.cbIsCompleted.isChecked = notes.isCompleted?:false
            }
        }
        saveRoom().execute()
    }

    private fun save(notes: Notes) {
        class saveRoom : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {

                mainActivity.userRoomDatabase.notesDao().insertAll(notes)
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
               Toast.makeText(mainActivity, resources.getString(R.string.add), Toast.LENGTH_LONG).show()
                mainActivity.navController.popBackStack()
            }
        }
        saveRoom().execute()
    }

    private fun update(notes: Notes) {
        class updateDb : AsyncTask<Notes, Void, Void>(){
//            override fun doInBackground(vararg p0: Void?): Void? {
//                mainActivity.userRoomDatabase.notesDao().update(notes)
//                return null
//            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
               Toast.makeText(mainActivity, resources.getString(R.string.updated), Toast.LENGTH_LONG).show()
                mainActivity.navController.popBackStack()
            }

            override fun doInBackground(vararg p0: Notes?): Void? {
                p0.get(0)?.let { mainActivity.userRoomDatabase.notesDao().update(it) }
                return  null
            }
        }
        updateDb().execute(notes)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddUpdateNotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddUpdateNotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}