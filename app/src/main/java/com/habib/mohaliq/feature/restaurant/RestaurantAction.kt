package com.habib.mohaliq.feature.restaurant

sealed interface RestaurantAction {

    data object BackClicked : RestaurantAction

    data object FavoriteClicked : RestaurantAction

    data object ShowMapClicked : RestaurantAction

    data object DateFieldClicked : RestaurantAction

    data class DateSelected(val date: String) : RestaurantAction

    data object DatePickerDismissed : RestaurantAction

    data object PartySizeIncremented : RestaurantAction

    data object PartySizeDecremented : RestaurantAction

    data object ReserveClicked : RestaurantAction

}
