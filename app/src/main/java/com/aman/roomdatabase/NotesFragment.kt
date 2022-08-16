package com.aman.roomdatabase

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.roomdatabase.databinding.FragmentNotesBinding
import com.aman.roomdatabase.databinding.LayoutItemBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment(), NotesClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentNotesBinding
    private var notesArray: ArrayList<Notes> = ArrayList()
    private lateinit var adapter: NotesAdapter
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(layoutInflater)
        adapter = NotesAdapter(notesArray, this)
        binding.recycler.layoutManager = LinearLayoutManager(mainActivity)
        binding.recycler.adapter = adapter
        binding.fabAdd.setOnClickListener {
            mainActivity.navController.navigate(R.id.addUpdateNotesFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesArray.clear()
        getNotes()
    }

    private fun getNotes() {
        class getData : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                notesArray.addAll(mainActivity.userRoomDatabase.notesDao().getAll() as ArrayList<Notes>)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapter.notifyDataSetChanged()
            }
        }
        getData().execute()
    }

    private fun deleteNotes(notes:Notes) {
        class getData : AsyncTask<Notes, Void, Void>(){
            override fun doInBackground(vararg p0: Notes?): Void? {
                mainActivity.userRoomDatabase.notesDao().delete(notes)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                notesArray.clear()
                getNotes()
            }
        }
        getData().execute(notes)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun NotesClicked(notes: Notes, holder: NotesAdapter.ViewHolder) {
        var popupMenu = PopupMenu(mainActivity,holder.binding.ivMenu)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete->{
                   AlertDialog.Builder(mainActivity).apply {
                       setTitle(resources.getString(R.string.delete_notes))
                       setMessage(resources.getString(R.string.delete_message))
                       setPositiveButton(resources.getString(R.string.yes)){_,_->
                           deleteNotes(notes)
                       }
                       setNegativeButton(resources.getString(R.string.no)){_,_-> }
                   }.show()
                }
                R.id.update->{
                    var bundle = Bundle()
                    bundle.putInt(mainActivity.id, notes.id)
                    mainActivity.navController.navigate(R.id.addUpdateNotesFragment, bundle)
                }
            }
            return@setOnMenuItemClickListener true
        }

    }
}