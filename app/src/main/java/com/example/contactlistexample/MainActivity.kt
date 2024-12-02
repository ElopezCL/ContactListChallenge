package com.example.contactlistexample

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()
    private lateinit var etNombre: EditText
    private lateinit var etTelefono: EditText
    private lateinit var boton: Button
    private lateinit var cbAvailable: CheckBox
    private lateinit var tabla: TableLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etNombre = findViewById(R.id.etNombre)
        etTelefono = findViewById(R.id.etTelefono)
        cbAvailable = findViewById(R.id.cbAvailable)
        tabla = findViewById(R.id.tabla)
        boton = findViewById(R.id.boton)


        boton.setOnClickListener {

            val name = etNombre.text.toString()
            val phono = etTelefono.text.toString()
            val available = cbAvailable.isChecked

            if (name.isEmpty() || phono.isEmpty()) {
                Toast.makeText(
                    this,
                    "** Campos Vacíos, por favor ingrese todos los valores **",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val contact = Contact(
                name = name,
                phone = phono,
                isAvailable = available
            )
            contactList.add(contact)
            etNombre.text.clear()
            etTelefono.text.clear()
            cbAvailable.isChecked = false
            Toast.makeText(this, "*** Se ha agregado nuevo contacto**", Toast.LENGTH_SHORT).show()
            refreshTable()

        }
        refreshTable()
    }

    private fun refreshTable() {
        tabla.removeAllViews()
        val filtradoContacto = contactList.filter {it.isAvailable}

        val headerRow = TableRow(this)
        val nameHeader = TextView(this).apply {
            text = "Nombre"
            setTypeface(null, Typeface.BOLD)
        }
        val phoneHeader = TextView(this).apply {
            text = "Telefono"
            setTypeface(null, Typeface.BOLD)
        }
        val availableHeader = TextView(this).apply {
            text = "Habilitado"
            setTypeface(null, Typeface.BOLD)
        }
        headerRow.addView(nameHeader)
        headerRow.addView(phoneHeader)
        headerRow.addView(availableHeader)
        tabla.addView(headerRow)
        filtradoContacto.forEach { contact ->
            val row = TableRow(this)

            val nameTextView = TextView(this).apply {
                text = contact.name
            }
            val phoneTextView = TextView(this).apply {
                text = contact.phone
            }
            val availableTextView = TextView(this).apply {
                text = if (contact.isAvailable) "Disponible" else "No disponible"
            }


            row.addView(nameTextView)
            row.addView(phoneTextView)
            row.addView(availableTextView)
            tabla.addView(row)
        }


    }


    // RecyclerView
    private fun setRecyclerViewAdapter(contactList: List<Contact>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}
