package com.example.gymtastic.ui.login
//Author Miguel Parra
//Class that model a routine
class Routine(pName: String,pObjetiveName :String, pObjetiveDescription: String) {

    //Atributes-------------------------------------------------------
    private var name: String
        get() {
            TODO()
        }
        set(value) {}


    private var objetive: Objetive
        get() {
            TODO()
        }
        set(value) {}
    //TODO Logic add days
    private lateinit var days:Array<Day>
    init{
        days = arrayOf(Day("DayX","Top Train"))
    }


    //@Author: Carlos Figueredo
    fun getDays(): Array<Day> {
        return days
    }

    fun getDay(index:Int): Day {
        return days.get(index)
    }

    fun setSet(index: Int, d: Day){
        days.set(index, d)
    }

    fun addDay(d: Day) {
        append(days, d)
        print("Day Added")
    }

    fun deleteDay(d: Day) {
        days.drop(days.indexOf(d))
    }

    fun <T> append(arr: Array<T>, element: T): Array<T?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }
    //Constructor--------------------------------------------------------
    init{
        name=pName
       objetive= Objetive(pObjetiveName,pObjetiveDescription)

    }

}