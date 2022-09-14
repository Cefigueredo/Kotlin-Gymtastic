package com.example.gymtastic.ui.login

//Author Miguel Parra
//Class that model an user
class User(pName:String, pMail:String,pPassword:String) {
    //Atributes-------------------------------------------------------
    private var name: String
        get() {
            TODO()
        }
        set(value) {}


    private var mail: String
        get() {
            TODO()
        }
        set(value) {}
    private var password: String
        get() {
            TODO()
        }
        set(value) {}
    //TODO Logic add routines
    private lateinit var routines:Array<Routine>



    //@Author: Carlos Figueredo
    fun getRoutines(): Array<Routine> {
        return routines
    }

    fun getRoutine(index:Int): Routine {
        return routines.get(index)
    }

    fun setRoutine(index: Int, r: Routine){
        routines.set(index, r)
    }

    fun addRoutine(r: Routine) {
        append(routines, r)
        print("Routine Added")
    }

    fun deleteRoutine(r: Routine) {
        routines.drop(routines.indexOf(r))
    }

    fun <T> append(arr: Array<T>, element: T): Array<T?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }
    //Constructor--------------------------------------------------------
    init{
        name=pName
        mail=pMail
        password=pPassword

    }
}