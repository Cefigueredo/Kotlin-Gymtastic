package com.example.gymtastic.ui.login
//Author Miguel Parra
//Class that model an exercise outside the gym
class ExerciseCalisthenics (pName: String, pUrl : String , pReps:Int): Exercise(pName,pUrl) {
    //Atributes-------------------------------------------------------
    private  var sets : Array<SetAtHome> = arrayOf(SetAtHome(pReps))

}