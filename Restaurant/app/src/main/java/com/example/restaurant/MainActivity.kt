package com.example.restaurant

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.restaurant.Modelo.CuentaMesa
import com.example.restaurant.Modelo.ItemMenu
import com.example.restaurant.Modelo.ItemMesa
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cantidadPastel = findViewById<EditText>(R.id.etPastel)
        val cantidadCazuela = findViewById<EditText>(R.id.etCazuela)
        val itemPastel = findViewById<TextView>(R.id.tvCantPasteles)
        val itemCazuela = findViewById<TextView>(R.id.tvCantCazuelas)

        val montoComida = findViewById<TextView>(R.id.montoComida)
        val cuentaMesa = CuentaMesa()
        val montoPropina = findViewById<TextView>(R.id.montoPropina)
        val montoTotal = findViewById<TextView>(R.id.montoTotal)

        val swPropina = findViewById<Switch>(R.id.swPropina) as Switch


        val precioPastel = 12000
        val precioCazuela = 10000
        var totalMesa = 0
        var totalAlmuerzo = 0

        fun formatoMoneda(cambio: Int): String {
            var formato = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            return formato.format(cambio)
        }

        fun totalMesa(platos: Int) {
            montoComida.setText(formatoMoneda(platos))
        }

        fun propina(valor:Int) {
            if(valor ==1){
                montoPropina.setText(formatoMoneda(cuentaMesa.calcularPropina()))
                } else {
                    montoPropina.setText(formatoMoneda(valor))
                }
        }

        fun modificaTotal(){
            montoTotal.setText(formatoMoneda(cuentaMesa.calcularTotalConPropina()))
        }

        fun recalculaPropina() {
            if (swPropina.isChecked) {
                cuentaMesa.aceptaPropina = true
                propina(1)
            } else {
                cuentaMesa.aceptaPropina = false
                propina(0)
            }
            modificaTotal()
        }

        cantidadPastel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var cantidad = s.toString()
                var cantidadInicial = 0
                if (cantidad != "") {
                    cantidadInicial = cantidad.toInt()
                }
                var menu = ItemMenu("Pastel", "12000")
                var mesa = ItemMesa(cantidadInicial, menu)
                itemPastel.setText(formatoMoneda(mesa.calcularSubtotal()))

                cuentaMesa.agregarItem(mesa)
                recalculaPropina()

                totalMesa(cuentaMesa.calcularTotalSinPropina())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        cantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var cantidad = s.toString()
                var cantidadInicial = 0
                if (cantidad != "") {
                    cantidadInicial = cantidad.toInt()
                }
                var menu = ItemMenu("Cazuela", "10000")
                var mesa = ItemMesa(cantidadInicial, menu)
                itemCazuela.setText(formatoMoneda(mesa.calcularSubtotal()))

                cuentaMesa.agregarItem(mesa)
                recalculaPropina()

                totalMesa(cuentaMesa.calcularTotalSinPropina())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        swPropina.setOnClickListener {
            recalculaPropina()
        }
    }
}