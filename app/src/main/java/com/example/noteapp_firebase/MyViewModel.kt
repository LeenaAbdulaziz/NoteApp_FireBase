package com.example.noteapp_firebase

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyViewModel (activity: Application): AndroidViewModel(activity) {

    private val notes: MutableLiveData<List<Note>> = MutableLiveData()

    val db = Firebase.firestore


    fun getNotes(): LiveData<List<Note>> = notes


    fun getNotesFromDB() {
        val list = arrayListOf<Note>()
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result)
                    document.data.map { (_, value) ->
                        list.add(Note(document.id, value.toString()))
                    }
                notes.postValue(list)
            }
            .addOnFailureListener {
                notes.postValue(list)
            }

    }


    fun addNotes(n: Note) {

        GlobalScope.launch(Main) {

            val notes = hashMapOf(
                "note" to n.note
            )
            db.collection("notes")
                .add(notes)
                .addOnSuccessListener { }
                .addOnFailureListener { }
        }

        getNotesFromDB()

    }

    fun updatesNotes(n: Note) {
        GlobalScope.launch(Main)
        {
            db.collection("notes").document(n.id)
                .update("note", n.note)
                .addOnSuccessListener { }
                .addOnFailureListener { }

        }
        getNotesFromDB()
    }

    fun deleteNotes(n: Note) {
        GlobalScope.launch(Main)
        {
            db.collection("notes").document(n.id)
                .delete()
                .addOnSuccessListener { }
                .addOnFailureListener { }


        }
        getNotesFromDB()
    }
}