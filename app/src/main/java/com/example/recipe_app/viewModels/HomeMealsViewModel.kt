package com.example.recipe_app.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.Repository
import kotlinx.coroutines.launch

class HomeMealsViewModel (private val repository: Repository)  : ViewModel() {

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals


    private val _listOfFavMeals = MutableLiveData<List<MealX>>()
    val listOfFavMeals: LiveData<List<MealX>> = _listOfFavMeals


    private val _randomMeal = MutableLiveData<MealX>()
    val randomMeal: LiveData<MealX> = _randomMeal

    fun getRandomMeal(){
        if(randomMeal.value == null){
        viewModelScope.launch {
            val response =  repository.getRandomMeal()
            _randomMeal.value = response.meals[0]
        }
    }}

    val alphabets = ('a'..'z').map { it.toString() }.shuffled().get(0)


    fun getMeals(){
        viewModelScope.launch {
            val response = repository.getMealsResponse(alphabets)
            _listOfMeals.value = response.meals
        }
    }

    fun getFavMeals(userId :String) {
        viewModelScope.launch {
            _listOfFavMeals.value = repository.getFavMeals(userId)
        }
    }

    fun insertMeal (meal :MealX)
    {
        viewModelScope.launch {
            repository.insertFavMeal(meal)
        }
    }
    fun deleteFavMeal(id : String)
    {
        viewModelScope.launch {
            repository.deleteFavMeal(id)
        }
    }



}