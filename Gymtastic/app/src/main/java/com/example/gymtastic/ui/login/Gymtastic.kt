package com.example.gymtastic.ui.login

import android.util.Log

//Author Miguel Parra
//Class of then controller
class Gymtastic {

    private lateinit var users:Array<User>
    init {
        users = arrayOf(
            User(
                "a","b","c"
            )
        )
        //TODO logic that init the logic
    }


    //Feautre GPS @MIGUEL PARRA
    fun determineRecomendedRoutine(pLatitud:String, pLongitud:String): String {

        //Si la ubicacion GPS esta en Uniandes
        //4.601746, -74.0661119 la rutina seleccionada sera rutina de gymnasio, de lo contrario calistenia

        if(pLatitud.contains("4.60") && pLongitud.toString().contains("-74.06"))
        {
            Log.d("UBICACION","ESTOY EN LA U"+pLatitud+pLongitud)
            return "Rutina de pierna en el gymnasio"
        }
        else
        {
            Log.d("UBICACION","ESTOY EN LA CASA"+pLatitud+pLongitud)

            return "Rutina de pierna en casa"
        }

    }
    //Feature predic next set. Given the log of sets of a user predict the next set.
    //@Autor Miguel Parra

    fun predictNextSet( exercise:ExerciseGym): Set
    {
        var pset: Set = Set(8,10);

        if(exercise.getSets() != null)
        {

            pset = exercise.getSets().get(0);
            //Strategy Desing Pattern implemented by Miguel Parra
            //Depens of the case chec if it is possible to improve the set
            var condition = ConditionCheck(exercise.getSets());
            if(condition)
            {

                pset.setWeigth(pset.getWeigth()+10)

            }
            else
            {
                pset.setRep(pset.getRep()+10)
            }

        }

            return pset;


    }

    //Funtion that check if there is posible to increase the weight and repetitions of a set
    //@Autor Miguel Parra
    fun ConditionCheck(sets: Array<Set>): Boolean
    {
        var r = false;


        if(sets.size > 3)
        {
            var last= sets.size-1
            var ultimo = sets.get(last).getWeigth()
            var penultimo = sets.get(last-1).getWeigth()
            if(ultimo >= penultimo)
            {
                r = true
            }

        }
        return r;
    }

    //@Author: Carlos Figueredo
    fun getUsers(): Array<User> {
        return users
    }

    fun getUser(index:Int): User {
        return users.get(index)
    }

    fun setUser(index: Int, u: User){
        users.set(index, u)
    }

    fun addUser(u: User) {
        append(users, u)
        print("User Added")
    }

    fun deleteUser(u: User) {
        users.drop(users.indexOf(u))
    }

    fun <T> append(arr: Array<T>, element: T): Array<T?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }

}