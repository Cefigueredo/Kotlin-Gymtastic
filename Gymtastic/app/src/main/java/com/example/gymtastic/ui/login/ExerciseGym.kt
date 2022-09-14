package com.example.gymtastic.ui.login

/**
 * @Authors: Carlos Figueredo, Miguel Parra
 * Class that model an exercise at the gym
 */
class ExerciseGym(pName: String, pUrl: String, pWeight: Int, pReps: Int) : Exercise(pName, pUrl) {
    /**
     * Atributes -------------------------------------------------------
     */
    private var sets: Array<Set> = arrayOf(Set(pReps, pWeight))
    private var name: String = pName
    private var url: String = pUrl

    /**
     * Methods ---------------------------------------------------------------
     */
    fun getUrl(): String {
        return url
    }
    fun getSets(): Array<Set> {
        return sets
    }

    fun getSet(index:Int): Set {
        return sets.get(index)
    }

    fun setSet(index: Int, set: Set){
        sets.set(index, set)
    }

    fun addSets(set: Set) {

        append(sets, set)
        print("Set Added")
    }

    fun deleteSet(set: Set) {
        sets.drop(sets.indexOf(set))
    }

    fun <T> append(arr: Array<T>, element: T): Array<T?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }
}