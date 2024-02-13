package com.example.s_tarea_2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.s_tarea_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnCalcular.setOnClickListener{
            val seed1 = binding.edtNum1.text.toString()
            val seed2 = binding.edtNum2.text.toString()
            val iteracion = binding.edtIteracion.text.toString()
            if(seed1.isNotEmpty() && seed2.isNotEmpty()){
                binding.tvMostrar.text = ""
                if(seed1.length > 3 && seed2.length > 3){
                    val arregloCadenas = Array(999) { "" }
                    arregloCadenas[0] = seed1
                    arregloCadenas[1] = seed2

                    for (i in 0..iteracion.toInt()-1){
                        //Si se ingresa puro cero en alguno de los campos, se rompe el ciclo
                        if (isOnlyZeros(arregloCadenas[i]) || isOnlyZeros(arregloCadenas[i+1])) {
                            Toast.makeText(this, "Se encontró valor no válido", Toast.LENGTH_SHORT).show()
                            break
                        }

                        val operation = arregloCadenas[i].toLong() * arregloCadenas[i+1].toLong()
                        Log.d("operation", "$operation")
                        val digit = operation.toString().length.toString()
                        if(digit.toInt() % 2 == 0){
                            val digitMedium = digitosMedios(operation.toString(),arregloCadenas[i])
                            binding.tvMostrar.append("Y${i} = (${arregloCadenas[i]}) (${arregloCadenas[i+1]})  X${i+1} = ${digitMedium}  r${i+1} = 0.${digitMedium}\n")
                            arregloCadenas[i+2] = digitMedium
                        }else{
                            val digitMedium = digitosMedios(operation.toString(),arregloCadenas[i])
                            binding.tvMostrar.append("Y${i} = (${arregloCadenas[i]}) (${arregloCadenas[i+1]})  X${i+1} = ${digitMedium}  r${i+1} = 0.${digitMedium}\n")
                            arregloCadenas[i+2] = digitMedium
                        }

                        if (isOnlyZeros(arregloCadenas[i+1])) {
                            Toast.makeText(this, "Se encontró valor no válido", Toast.LENGTH_SHORT).show()
                            break
                        }
                    }
                }else{
                    Toast.makeText(this, "El número debe ser mayor de 3 digitos", Toast.LENGTH_SHORT).show()
                }

                //Ocultar teclado
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }

    //Lógica principal
    fun digitosMedios(cadena: String, seed: String): String {

        val longitudCadena = cadena.length
        val longitudSeed = seed.length

        var cadenaTransformada = cadena

        if (longitudSeed % 2 == 0) {
            // Seed par, cadena resultado debe ser par
            if (longitudCadena % 2 != 0) {
                // Cadena impar, le agrego 0
                cadenaTransformada = "0$cadena"
            }
        }
        else {
            // Seed impar, cadena resultado debe ser impar
            if (longitudCadena % 2 == 0){
                // Cadena par, le agrego 0
                cadenaTransformada = "0$cadena"
            }
        }

        val corteInicio = (cadenaTransformada.length - longitudSeed) / 2
        val corteFin = corteInicio + longitudSeed

        return cadenaTransformada.substring(corteInicio, corteFin)

    }

    //Comprobar si el usuario no ingresó puros ceros
    fun isOnlyZeros(text: String): Boolean {
        val sum = text.toLong() * text.toLong()

        return sum == 0.toLong()
    }
}