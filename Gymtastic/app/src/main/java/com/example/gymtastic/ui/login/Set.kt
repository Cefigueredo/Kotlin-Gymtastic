package com.example.gymtastic.ui.login

/**
 * @Authors: Carlos Figueredo, Miguel Parra
 * Class that model a set with weight
 */
class Set(pRepetitions: Int, pWeight: Int) {

    /**
     *Attributes-------------------------------------------------------
     */
   var repetitions: Int

   var weight: Int

    /**
     * Constructor -------------------------------------------------------
     */
    init {
        repetitions = pRepetitions
        weight = pWeight
    }

    /**
     * Methods -------------------------------------------------------
     */
    fun getWeigth(): Int {
        return weight
    }

    fun setWeigth(pWeight: Int){
        weight = pWeight
    }
    fun getRep(): Int {
        return repetitions
    }

    fun setRep(pRepetitions: Int){
        repetitions = pRepetitions
    }
}