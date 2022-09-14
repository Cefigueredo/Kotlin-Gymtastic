package com.example.gymtastic.ui.login

import kotlin.collections.Set

//Author Miguel Parra
//Class that model a day
class Day(pName: String, pMuselGroup: String) {
    //Atributes-------------------------------------------------------
    private var name: String
        get() {
            TODO()
        }
        set(value) {}


    private var muselGroup: String
        get() {
            TODO()
        }
        set(value) {}
    //TODO Logic add exercises
    private lateinit var exercisesAtHome:Array<ExerciseCalisthenics>
    init{
        exercisesAtHome = arrayOf(ExerciseCalisthenics("Name","Url",3))
    }
    private lateinit var exercisesAtGym:Array<ExerciseGym>
    init{
        exercisesAtGym = arrayOf(ExerciseGym("Press Chest","https://www.youtube.com/watch?v=vthMCtgVtFw", 20, 20))
    }

    //@Author: Carlos Figueredo

    fun getExercisesGym(): Array<ExerciseGym> {
        return exercisesAtGym
    }

    fun getExerciseGym(index:Int): ExerciseGym {
        return exercisesAtGym.get(index)
    }

    fun setExerciseGym(index: Int, e: ExerciseGym){
        exercisesAtGym.set(index, e)
    }

    fun addExerciseGym(ex: ExerciseGym) {
        append(exercisesAtGym, ex)
        print("ExerciseGym Added")
    }

    fun deleteExerciseGym(ex: ExerciseGym) {
        exercisesAtGym.drop(exercisesAtGym.indexOf(ex))
    }

    fun <T> append(arr: Array<T>, element: T): Array<T?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }
    //Constructor--------------------------------------------------------
    init{
        name=pName
        muselGroup=pMuselGroup

    }
}