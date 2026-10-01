package com.habib.mohaliq.app.navigation

import android.net.Uri

sealed class Destinations(
    val route: String
) {

    data object Splash : Destinations("splash")

    data object Login : Destinations("login")

    data object Register : Destinations("register")

    data object Home : Destinations("home")

    data object Explore : Destinations("explore")

    data object Search : Destinations("search?category={category}") {
        fun createRoute(category: String? = null): String {
            return if (category != null) "search?category=$category" else "search"
        }
    }

    data object Destination : Destinations("destination/{destinationId}") {
        fun createRoute(destinationId: String) = "destination/$destinationId"
    }

    data object Hotel : Destinations("hotel/{hotelId}") {
        fun createRoute(hotelId: String) = "hotel/$hotelId"
    }

    data object HotelBooking : Destinations("hotel_booking/{hotelId}") {
        fun createRoute(hotelId: String) = "hotel_booking/$hotelId"
    }

    data object Restaurant : Destinations("restaurant/{restaurantId}") {
        fun createRoute(restaurantId: String) = "restaurant/$restaurantId"
    }

    data object Payment : Destinations(
        "payment/{placeType}/{placeId}/{secondaryId}/{dateRange}/{guestCount}"
    ) {
        fun createRoute(
            placeType: String,
            placeId: String,
            secondaryId: String,
            dateRange: String,
            guestCount: Int
        ): String {
            val encodedDateRange = Uri.encode(dateRange)

            return "payment/" +
                    "$placeType/" +
                    "$placeId/" +
                    "$secondaryId/" +
                    "$encodedDateRange/" +
                    "$guestCount"
        }
    }

    data object Favorites : Destinations("favorites")

    data object Map : Destinations("map/{latitude}/{longitude}/{title}") {
        fun createRoute(latitude: Double, longitude: Double, title: String): String {
            val encodedTitle = Uri.encode(title)
            return "map/$latitude/$longitude/$encodedTitle"
        }
    }

    data object History : Destinations("history")

    data object Profile : Destinations("profile")

    data object AiChat : Destinations("ai_chat")

}
